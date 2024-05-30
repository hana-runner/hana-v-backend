package com.v.hana.common.exception;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.response.GlobalHttpCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@TypeInfo(name = "ExceptionControllerAdvice", description = "개발 환경의 예외 처리 컨트롤러 어드바이스 클래스")
@RestControllerAdvice(basePackages = "com.v.hana")
class ExceptionControllerAdvice {
    @MethodInfo(name = "handleException", description = "서버에서 발생한 전반적인 예외를 처리합니다.")
    @ExceptionHandler
    public ResponseEntity<BaseExceptionResponse> handleException(Exception e) {
        return ResponseEntity.status(GlobalHttpCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(
                        BaseExceptionResponse.of(
                                GlobalHttpCode.INTERNAL_SERVER_ERROR.getHttpStatus(),
                                GlobalHttpCode.INTERNAL_SERVER_ERROR.getCode(),
                                e.getMessage(),
                                e.getClass().getSimpleName()));
    }

    @MethodInfo(
            name = "handleMethodArgumentNotValidException",
            description = "서버에서 발생한 MethodArgumentNotValidException 예외를 처리합니다.")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        return ResponseEntity.status(GlobalHttpCode.BAD_REQUEST.getHttpStatus())
                .body(
                        BaseExceptionResponse.of(
                                GlobalHttpCode.BAD_REQUEST.getHttpStatus(),
                                GlobalHttpCode.BAD_REQUEST.getCode(),
                                methodArgumentNotValidException
                                        .getBindingResult()
                                        .getFieldError()
                                        .getDefaultMessage(),
                                methodArgumentNotValidException.getClass().getSimpleName()));
    }

    @MethodInfo(
            name = "handleBaseCodeException",
            description = "서버에서 발생한 예외 중에 사용자가 정의한 예외를 처리합니다.")
    @ExceptionHandler(BaseCodeException.class)
    public ResponseEntity<BaseExceptionResponse> handleBaseCodeException(BaseCodeException e) {
        return ResponseEntity.status(e.getBaseHttpCode().getHttpReason().getHttpStatus())
                .body(BaseExceptionResponse.of(e));
    }
}
