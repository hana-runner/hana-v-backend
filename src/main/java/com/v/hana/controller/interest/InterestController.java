package com.v.hana.controller.interest;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.dto.interest.InterestsResponse;
import com.v.hana.usecase.interest.InterestUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TypeInfo(name = "InterestController", description = "관심사 컨트롤러")
@RestController
@Tag(name = "Interest", description = "관심사")
@RequestMapping("v1/api")
public class InterestController {

    private final InterestUseCase interestUseCase;

    @Operation(
            summary = "관심사 목록 가져오기",
            description = "관심사 목록을 가져옵니다.",
            method = "GET",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "관심사 목록 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(implementation = InterestsResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "잘못된 요청",
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
    @MethodInfo(name = "getInterests", description = "관심사 목록 가져오기")
    @GetMapping("/interests")
    public ResponseEntity<InterestsResponse> getInterests() {
        InterestsResponse interests = interestUseCase.getInterests();
        return ResponseEntity.ok(interests);
    }

    public InterestController(InterestUseCase interestUseCase) {
        this.interestUseCase = interestUseCase;
    }
}
