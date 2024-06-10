package com.v.hana.controller.interest;

import com.v.hana.auth.annotation.CurrentUser;
import com.v.hana.auth.util.user.SecurityUtil;
import com.v.hana.command.card.GetUserInterestCardsCommand;
import com.v.hana.command.interest.AddUserInterestCommand;
import com.v.hana.command.interest.GetUserInterestReportsCommand;
import com.v.hana.command.interest.GetUserInterestTransactionsCommand;
import com.v.hana.command.interest.GetUserInterestsCommand;
import com.v.hana.command.interest.ModifyUserInterestCommand;
import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.common.response.DeleteSuccessResponse;
import com.v.hana.common.response.PostSuccessResponse;
import com.v.hana.common.response.PutSuccessResponse;
import com.v.hana.dto.card.CardInterestResponse;
import com.v.hana.dto.interest.UserCompareResponse;
import com.v.hana.dto.interest.UserInterestReportsResponse;
import com.v.hana.dto.interest.UserInterestResponse;
import com.v.hana.dto.interest.UserInterestTransactionsResponse;
import com.v.hana.entity.interest.Interest;
import com.v.hana.entity.interest.UserInterest;
import com.v.hana.entity.user.User;
import com.v.hana.exception.interest.UserInterestDuplicatedException;
import com.v.hana.exception.user.InvalidUserAccessException;
import com.v.hana.repository.interest.InterestRepository;
import com.v.hana.repository.interest.UserInterestRepository;
import com.v.hana.service.interest.ImageService;
import com.v.hana.service.interest.UserInterestService;
import com.v.hana.usecase.card.CardInterestUseCase;
import com.v.hana.usecase.interest.UserInterestUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@TypeInfo(name = "UserInterestController", description = "사용자 관심사 컨트롤러")
@RestController
@Tag(name = "UserInterest", description = "사용자 관심사")
@RequestMapping("v1/api")
public class UserInterestController {
    private final SecurityUtil securityUtil;
    private final ImageService imageUploadService;
    private final UserInterestService userInterestService;
    private final InterestRepository interestRepository;
    private final UserInterestRepository userInterestRepository;
    private final UserInterestUseCase userInterestUseCase;
    private final CardInterestUseCase cardInterestUseCase;

    @MethodInfo(name = "getUserInterests", description = "사용자 관심사 목록 가져오기")
    @Operation(
            summary = "사용자 관심사 목록 가져오기",
            description = "사용자의 관심사 목록을 가져옵니다.",
            parameters = {@Parameter(name = "userId", description = "사용자 ID", required = true)},
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사 목록 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사 목록 가져오기 실패",
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
    @GetMapping("/user-interests")
    @CurrentUser
    public ResponseEntity<UserInterestResponse> getUserInterests() {
        User user = securityUtil.getCurrentUser();

        UserInterestResponse interests =
                userInterestUseCase.getUserInterests(
                        GetUserInterestsCommand.builder().userId(user.getId()).build());
        return ResponseEntity.ok(interests);
    }

    @MethodInfo(name = "getUserInterests", description = "사용자 관심사별 거래 내역 가져오기")
    @Operation(
            summary = "사용자 관심사별 거래 내역 가져오기",
            description = "사용자의 관심사별 거래 내역을 가져옵니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "userId", description = "사용자 ID", required = true),
                @Parameter(name = "year", description = "년도", required = true),
                @Parameter(name = "month", description = "월", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사별 거래 내역 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestTransactionsResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사별 거래 내역 가져오기 실패",
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
    @GetMapping("/user-interests/transaction/{interestId}")
    @CurrentUser
    public ResponseEntity<UserInterestTransactionsResponse> getUserInterestTransaction(
            @PathVariable Long interestId, @RequestParam int year, @RequestParam int month) {
        User user = securityUtil.getCurrentUser();

        UserInterestTransactionsResponse interests =
                userInterestUseCase.getUserInterestTransactions(
                        GetUserInterestTransactionsCommand.builder()
                                .interestId(interestId)
                                .userId(user.getId())
                                .year(year)
                                .month(month)
                                .build());
        return ResponseEntity.ok(interests);
    }

    @MethodInfo(name = "getUserInterestReports", description = "사용자 관심사별 거래 내역 리포트를 가져옵니다.")
    @Operation(
            summary = "사용자 관심사별 거래 내역 리포트 가져오기",
            description = "사용자의 관심사별 거래 내역 리포트를 가져옵니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "year", description = "년도", required = true),
                @Parameter(name = "month", description = "월", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사별 거래 내역 리포트 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                UserInterestReportsResponse
                                                                        .class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사별 거래 내역 리포트 가져오기 실패",
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
    @GetMapping("/user-interests/report/{interestId}")
    @CurrentUser
    public ResponseEntity<UserInterestReportsResponse> getUserInterestReports(
            @PathVariable Long interestId, @RequestParam int year, @RequestParam int month) {
        User user = securityUtil.getCurrentUser();

        UserInterestReportsResponse reports =
                userInterestUseCase.getUserInterestReports(
                        GetUserInterestReportsCommand.builder()
                                .interestId(interestId)
                                .userId(user.getId())
                                .year(year)
                                .month(month)
                                .build());
        return ResponseEntity.ok(reports);
    }

    @MethodInfo(name = "getUserInterestCards", description = "사용자 관심사별 카드 목록을 가져옵니다.")
    @Operation(
            summary = "사용자 관심사별 카드 목록 가져오기",
            description = "사용자의 관심사별 카드 목록을 가져옵니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사별 카드 목록 가져오기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                CardInterestResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사별 카드 목록 가져오기 실패",
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
    @GetMapping("/user-interests/cards/{interestId}")
    @CurrentUser
    public ResponseEntity<CardInterestResponse> getUserInterestCards(
            @PathVariable Long interestId) {
        User user = securityUtil.getCurrentUser();

        return ResponseEntity.ok(
                cardInterestUseCase.getCardInterest(
                        GetUserInterestCardsCommand.builder().interestId(interestId).build()));
    }

    @MethodInfo(name = "addUserInterests", description = "사용자 관심사 추가하기")
    @Operation(
            summary = "사용자 관심사 추가하기",
            description = "사용자의 관심사를 추가합니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "subtitle", description = "부제목", required = true),
                @Parameter(name = "image", description = "이미지", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사 추가하기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                PostSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사 추가하기 실패",
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
    @PostMapping("/user-interests")
    @CurrentUser
    public ResponseEntity<PostSuccessResponse> addUserInterests(
            @RequestParam("interestId") Long interestId,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("image") MultipartFile image) {

        // 토큰 검사
        User user = securityUtil.getCurrentUser();

        // 등록된 사용자 관심사가 있는지 확인
        ArrayList<UserInterest> userInterest =
                userInterestRepository.findByInterestIdAndUserId(interestId, user.getId());
        if (!userInterest.isEmpty()) throw new UserInterestDuplicatedException();

        String fileUrl;
        if (image.isEmpty()) {
            fileUrl = imageUploadService.getDefaultImage();
        } else {
            fileUrl = imageUploadService.getImageUrl(image);
        }

        Optional<Interest> interest = interestRepository.findById(interestId);
        if (interest.isEmpty()) throw new InvalidUserAccessException();

        userInterestUseCase.addUserInterest(
                AddUserInterestCommand.builder()
                        .user(user)
                        .interest(interest.get())
                        .subtitle(subtitle)
                        .image(fileUrl)
                        .build());

        return ResponseEntity.ok(PostSuccessResponse.builder().build());
    }

    @MethodInfo(name = "modifyUserInterests", description = "사용자 관심사 수정하기")
    @Operation(
            summary = "사용자 관심사 수정하기",
            description = "사용자의 관심사를 수정합니다.",
            parameters = {
                @Parameter(name = "interestId", description = "관심사 ID", required = true),
                @Parameter(name = "subtitle", description = "부제목", required = true),
                @Parameter(name = "image", description = "이미지", required = true)
            },
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "사용자 관심사 수정하기 성공",
                        content =
                                @Content(
                                        schema =
                                                @Schema(
                                                        implementation =
                                                                PutSuccessResponse.class))),
                @ApiResponse(
                        responseCode = "400",
                        description = "사용자 관심사 수정하기 실패",
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
    @PutMapping("/user-interests")
    @CurrentUser
    public ResponseEntity<PutSuccessResponse> modifyUserInterests(
            @RequestParam("interestId") Long interestId,
            @RequestParam("subtitle") String subtitle,
            @RequestParam("image") MultipartFile image) {

        // jwt 토큰 복호화
        User user = securityUtil.getCurrentUser();

        Interest interest =
                interestRepository
                        .findById(interestId)
                        .orElseThrow(InvalidUserAccessException::new);

        String fileUrl =
                image.isEmpty()
                        ? userInterestRepository.findImageUrlByUserIdAndInterestId(
                                user.getId(), interest.getId())
                        : imageUploadService.getImageUrl(image);

        userInterestUseCase.modifyUserInterest(
                ModifyUserInterestCommand.builder()
                        .user(user)
                        .interest(interest)
                        .subtitle(subtitle)
                        .image(fileUrl)
                        .build());

        return ResponseEntity.ok(PutSuccessResponse.builder().build());
    }

    @MethodInfo(name = "getComparison", description = "관심사별 카테고리 지출 비교 정보를 조회합니다.")
    @GetMapping("/user-interests/compare/{interestId}")
    public ResponseEntity<UserCompareResponse> getComparison(
            @PathVariable Long interestId,
            @RequestParam(name = "start", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate start,
            @RequestParam(name = "end", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate end) {
        if (start == null) {
            start = LocalDate.now().minusMonths(1);
        }

        if (end == null) {
            end = LocalDate.now().plusDays(1);
        }
        User currentUser = securityUtil.getCurrentUser();

        int age = Period.between(currentUser.getBirthday(), LocalDate.now()).getYears();

        UserCompareResponse comparison =
                userInterestUseCase.getComparison(currentUser.getId(), interestId, age, start, end);

        return ResponseEntity.ok(comparison);
    }

    @MethodInfo(name = "deleteUserInterests", description = "사용자 관심사 삭제하기")
    @DeleteMapping("/user-interests/{interestId}")
    @CurrentUser
    public ResponseEntity<DeleteSuccessResponse> deleteUserInterests(@PathVariable Long interestId) {
        // 거래 내역 상세 삭제, 사용자 관심사 삭제
        User user = securityUtil.getCurrentUser();
        userInterestService.deleteUserInterestAndTransactionHistoryDetail(user.getId(), interestId);

        return ResponseEntity.ok(DeleteSuccessResponse.builder().build());
    }

    public UserInterestController(SecurityUtil securityUtil, ImageService imageUploadService, UserInterestService userInterestService, InterestRepository interestRepository, UserInterestRepository userInterestRepository, UserInterestUseCase userInterestUseCase, CardInterestUseCase cardInterestUseCase) {
        this.securityUtil = securityUtil;
        this.imageUploadService = imageUploadService;
        this.userInterestService = userInterestService;
        this.interestRepository = interestRepository;
        this.userInterestRepository = userInterestRepository;
        this.userInterestUseCase = userInterestUseCase;
        this.cardInterestUseCase = cardInterestUseCase;
    }
}
