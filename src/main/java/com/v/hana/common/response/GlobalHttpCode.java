package com.v.hana.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalHttpCode implements BaseHttpCode {
    // HTTP 1XX
    CONTINUE(HttpStatus.CONTINUE, "HTTP-100", "계속 진행합니다."),
    SWITCHING_PROTOCOLS(HttpStatus.SWITCHING_PROTOCOLS, "HTTP-101", "프로토콜 변경을 요청합니다."),
    PROCESSING(HttpStatus.PROCESSING, "HTTP-102", "요청이 수신되었으며 처리 중입니다."),
    EARLY_HINTS(HttpStatus.EARLY_HINTS, "HTTP-103", "조기 힌트를 제공합니다."),

    // HTTP 2XX
    OK(HttpStatus.OK, "HTTP-200", "요청이 성공했습니다."),
    CREATED(HttpStatus.CREATED, "HTTP-201", "요청이 성공적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "HTTP-202", "요청이 수락되었습니다."),
    NON_AUTHORITATIVE_INFORMATION(
            HttpStatus.NON_AUTHORITATIVE_INFORMATION, "HTTP-203", "응답은 캐시에서 가져왔습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "HTTP-204", "응답이 없습니다."),
    RESET_CONTENT(HttpStatus.RESET_CONTENT, "HTTP-205", "응답이 리셋되었습니다."),
    PARTIAL_CONTENT(HttpStatus.PARTIAL_CONTENT, "HTTP-206", "부분적인 내용을 제공합니다."),
    MULTI_STATUS(HttpStatus.MULTI_STATUS, "HTTP-207", "다중 상태 응답입니다."),
    ALREADY_REPORTED(HttpStatus.ALREADY_REPORTED, "HTTP-208", "이미 보고된 상태입니다."),
    IM_USED(HttpStatus.IM_USED, "HTTP-226", "IM 사용됨"),

    // HTTP 3XX
    MULTIPLE_CHOICES(HttpStatus.MULTIPLE_CHOICES, "HTTP-300", "다중 선택이 가능합니다."),
    MOVED_PERMANENTLY(HttpStatus.MOVED_PERMANENTLY, "HTTP-301", "영구적으로 이동했습니다."),
    FOUND(HttpStatus.FOUND, "HTTP-302", "다른 주소로 이동했습니다."),
    SEE_OTHER(HttpStatus.SEE_OTHER, "HTTP-303", "다른 주소로 이동했습니다."),
    NOT_MODIFIED(HttpStatus.NOT_MODIFIED, "HTTP-304", "수정되지 않았습니다."),
    TEMPORARY_REDIRECT(HttpStatus.TEMPORARY_REDIRECT, "HTTP-307", "임시적으로 이동했습니다."),
    PERMANENT_REDIRECT(HttpStatus.PERMANENT_REDIRECT, "HTTP-308", "영구적으로 이동했습니다."),

    // HTTP 4XX
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "HTTP-400", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "HTTP-401", "인증이 필요합니다."),
    PAYMENT_REQUIRED(HttpStatus.PAYMENT_REQUIRED, "HTTP-402", "결제가 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "HTTP-403", "접근이 금지되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "HTTP-404", "찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "HTTP-405", "허용되지 않는 메소드입니다."),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "HTTP-406", "허용되지 않는 요청입니다."),
    PROXY_AUTHENTICATION_REQUIRED(
            HttpStatus.PROXY_AUTHENTICATION_REQUIRED, "HTTP-407", "프록시 인증이 필요합니다."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "HTTP-408", "요청 시간이 초과되었습니다."),
    CONFLICT(HttpStatus.CONFLICT, "HTTP-409", "충돌이 발생했습니다."),
    GONE(HttpStatus.GONE, "HTTP-410", "삭제된 리소스입니다."),
    LENGTH_REQUIRED(HttpStatus.LENGTH_REQUIRED, "HTTP-411", "길이가 필요합니다."),
    PRECONDITION_FAILED(HttpStatus.PRECONDITION_FAILED, "HTTP-412", "사전 조건이 실패했습니다."),
    PAYLOAD_TOO_LARGE(HttpStatus.PAYLOAD_TOO_LARGE, "HTTP-413", "페이로드가 너무 큽니다."),
    URI_TOO_LONG(HttpStatus.URI_TOO_LONG, "HTTP-414", "URI가 너무 깁니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "HTTP-415", "지원되지 않는 미디어 타입입니다."),
    EXPECTATION_FAILED(HttpStatus.EXPECTATION_FAILED, "HTTP-417", "기대치에 미치지 못했습니다."),
    I_AM_A_TEAPOT(HttpStatus.I_AM_A_TEAPOT, "HTTP-418", "나는 주전자입니다."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "HTTP-422", "처리할 수 없는 엔티티입니다."),
    LOCKED(HttpStatus.LOCKED, "HTTP-423", "잠겨 있습니다."),
    FAILED_DEPENDENCY(HttpStatus.FAILED_DEPENDENCY, "HTTP-424", "실패한 의존성입니다."),
    UPGRADE_REQUIRED(HttpStatus.UPGRADE_REQUIRED, "HTTP-426", "업그레이드가 필요합니다."),
    PRECONDITION_REQUIRED(HttpStatus.PRECONDITION_REQUIRED, "HTTP-428", "사전 조건이 필요합니다."),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "HTTP-429", "요청이 너무 많습니다."),
    REQUEST_HEADER_FIELDS_TOO_LARGE(
            HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE, "HTTP-431", "요청 헤더 필드가 너무 큽니다."),
    UNAVAILABLE_FOR_LEGAL_REASONS(
            HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "HTTP-451", "법적인 이유로 사용할 수 없습니다."),

    // HTTP 5XX
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "HTTP-500", "서버 내부 오류입니다."),
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "HTTP-501", "구현되지 않았습니다."),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "HTTP-502", "게이트웨이 오류입니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "HTTP-503", "서비스를 사용할 수 없습니다."),
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "HTTP-504", "게이트웨이 시간 초과입니다."),
    HTTP_VERSION_NOT_SUPPORTED(
            HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "HTTP-505", "지원되지 않는 HTTP 버전입니다."),
    VARIANT_ALSO_NEGOTIATES(HttpStatus.VARIANT_ALSO_NEGOTIATES, "HTTP-506", "변형도 협상합니다."),
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "HTTP-507", "저장 공간이 부족합니다."),
    LOOP_DETECTED(HttpStatus.LOOP_DETECTED, "HTTP-508", "루프가 감지되었습니다."),
    NOT_EXTENDED(HttpStatus.NOT_EXTENDED, "HTTP-510", "확장되지 않았습니다."),
    NETWORK_AUTHENTICATION_REQUIRED(
            HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "HTTP-511", "네트워크 인증이 필요합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public BaseHttpReason getHttpReason() {
        return BaseHttpReason.builder()
                .httpStatus(this.httpStatus)
                .code(this.code)
                .message(this.message)
                .build();
    }

    GlobalHttpCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
