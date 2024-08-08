package treehouse.server.global.fcm.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.user.persistence.FcmTokenRepository;
import treehouse.server.global.entity.User.FcmToken;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.FcmException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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


    public void sendFcmMessage(User receiver, String title, String body) {
        if (receiver.isPushAgree() == false) {
            return;
        }

        List<FcmToken> fcmTokenList = fcmTokenRepository.findAllByUser(receiver);
        if (fcmTokenList.isEmpty()) {
            return;
        }

        for (FcmToken fcmToken : fcmTokenList) {
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
                throw new FcmException(GlobalErrorCode.FCM_SEND_MESSAGE_ERROR);
            }
        }


    }
}
