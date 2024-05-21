package treehouse.server.api.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import treehouse.server.api.notification.business.NotificationService;
import treehouse.server.api.notification.presentation.dto.NotificationResponseDTO;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.security.handler.annotation.AuthMember;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "⚠ Notification API", description = "알림 API 입니다. 알림 조회 API가 포함됩니다.")
public class NotificationApi {

    private final NotificationService notificationService;

    @GetMapping("/users/notifications")
    @Operation(summary = "알림 조회 \uD83D\uDD11✅", description = "사용자의 알림을 조회합니다.")
    public CommonResponse<NotificationResponseDTO.getNotifications> getNotifications(
            @AuthMember @Parameter(hidden = true) User user
    ){
        return CommonResponse.onSuccess(notificationService.getNotifications(user));
    }
}
