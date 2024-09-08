package treehouse.server.global.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.user.business.UserMapper;
import treehouse.server.api.user.persistence.FcmTokenRepository;
import treehouse.server.api.user.presentation.dto.UserResponseDTO;
import treehouse.server.global.entity.User.FcmToken;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.FcmException;
import treehouse.server.global.fcm.dto.FCMDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class FcmService {
    Logger logger = LoggerFactory.getLogger(FcmService.class);

    private final FcmTokenRepository fcmTokenRepository;

    public void testFCMService(String fcmToken)
    {
        logger.info("받은 FCM 토큰 값 : " + fcmToken);
        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(
                        Notification.builder()
                                .setTitle("TreeHouse FCM 테스트")
                                .setBody("TreeHouse 메시지를 성공적으로 수신하였습니당")
                                .build())
                .build();
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("the response of request FCM : {}",response);
        }catch (FirebaseMessagingException e){
            throw new FcmException(GlobalErrorCode.FCM_SEND_MESSAGE_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public UserResponseDTO.saveFcmToken saveFcmToken(User user, FCMDto.saveFcmTokenDto request) {
        boolean isSuccess = true;
        logger.info("토큰 값 : {}", request.getFcmToken());


        // 한 user 당 가장 최신의 fcm token 값 하나만 db 에 저장하도록 수정
        if (fcmTokenRepository.existsByUser(user)) {
            FcmToken fcmToken = fcmTokenRepository.findByUser(user).orElseThrow(() -> new FcmException(GlobalErrorCode.FCM_TOKEN_NOT_EXISTS));
            fcmToken.update(request.getFcmToken());
        } else {
            fcmTokenRepository.save(FcmToken.builder()
                    .user(user)
                    .token(request.getFcmToken())
                    .build()
            );
        }
        return UserMapper.toSaveFcmToken(user, isSuccess);
    }


    public void sendFcmMessage(User receiver, String title, String body) {

        FcmToken fcmToken = fcmTokenRepository.findByUser(receiver).orElseThrow(() -> new FcmException(GlobalErrorCode.FCM_TOKEN_NOT_EXISTS));

        String token = fcmToken.getToken();
        logger.info("전송하고자 하는 FCM 토큰 값 : " + token);

        Message message = Message.builder()
                .setToken(token)
                .setNotification(
                        Notification.builder()
                                .setTitle(title)
                                .setBody(body)
                                .build())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("the response of request FCM : {}",response);
        } catch (FirebaseMessagingException e) {
            logger.error("FCM 푸시 알림 전송 실패, 이유 : {}", e.getMessage());
            throw new FcmException(GlobalErrorCode.FCM_SEND_MESSAGE_ERROR);
        }

    }

    @Transactional
    public void deleteAllFcmToken(User user) {
        fcmTokenRepository.deleteAllByUser(user);
    }

}
