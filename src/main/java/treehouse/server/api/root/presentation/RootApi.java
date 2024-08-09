package treehouse.server.api.root.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import treehouse.server.global.common.CommonResponse;
import treehouse.server.global.fcm.dto.FCMDto;
import treehouse.server.global.fcm.service.FcmService;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "2000",description = "OK 성공"),
        @ApiResponse(responseCode = "5000",description = "서버 에러, 백엔드 개발자에게 알려주세요."),
})
@Tag(name = "테스트, 기타 API", description = "테스트, 기타 API 목록입니다.")
public class RootApi {

    private final FcmService fcmService;


    @GetMapping("/health")
    public String healthCheck(){
        return "I'm healthy!";
    }

    @Operation(summary = "FCM 테스트 API", description = "테스트용")
    @PostMapping("/test/fcm")
    public CommonResponse<Object> testFCM(@RequestBody FCMDto.FCMTestDto fcmToken) throws IOException
    {
        fcmService.testFCMService(fcmToken.getFcmToken());
        return CommonResponse.onSuccess("FCM 테스트 성공!");
    }
}
