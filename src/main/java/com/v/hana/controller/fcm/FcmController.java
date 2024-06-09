package com.v.hana.controller.fcm;

import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.dto.alarm.FcmSendDto;
import com.v.hana.dto.alarm.PushMessageDto;
import com.v.hana.entity.user.User;
import com.v.hana.repository.user.UserRepository;
import com.v.hana.service.RedisService;
import com.v.hana.service.fcm.FcmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "FcmController", description = "fcm 컨트롤러 클래스")
@RestController
@RequestMapping("v1/api")
@Tag(name = "FCM", description = "FCM")
@RequiredArgsConstructor
@Slf4j
public class FcmController {
    private final FcmService fcmService;
    private final RedisService redisService;
    private final UserRepository userRepository;

    @Operation(
            summary = "푸시 메시지 전송",
            description = "푸시 메시지를 전송합니다.",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "푸시 메시지 전송 성공",
                        content =
                                @Content(schema = @Schema(implementation = PushMessageDto.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "푸시 메시지 전송 실패",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class))),
                @ApiResponse(
                        responseCode = "500",
                        description = "서버 에러",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                BaseExceptionResponse.class)))
            })
    @PostMapping("/fcm/send")
    public ResponseEntity pushMessage(@RequestBody PushMessageDto pushMessageDto)
            throws IOException {
        Optional<User> user = userRepository.findById(pushMessageDto.getUser_id());
        if (user.isEmpty()) throw new BadRequestException();
        Object device_token =
                redisService.get(
                        user.get().getUsername() + ":device-token"); // key는 해당 유저의 유효한 단말 토큰
        if (device_token == null) {
            throw new BadRequestException(user.get().getUsername() + "의 유효한 단말 토큰이 존재하지 않습니다.");
        } else {
            String device_token_str = (String) device_token;
            FcmSendDto fcmSendDto =
                    FcmSendDto.builder()
                            .token(device_token_str)
                            .title(pushMessageDto.getTitle())
                            .body(pushMessageDto.getMessage())
                            .build();
            fcmService.sendMessageTo(fcmSendDto);
            redisService.right_push(
                    "user:" + user.get().getId() + ":notifications",
                    "title:"
                            + pushMessageDto.getTitle()
                            + ", message:"
                            + pushMessageDto.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
