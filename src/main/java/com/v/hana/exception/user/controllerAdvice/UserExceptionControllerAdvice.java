package com.v.hana.exception.user.controllerAdvice;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import com.v.hana.common.exception.BaseExceptionResponse;
import com.v.hana.common.exception.UserHttpCode;
import com.v.hana.common.response.GlobalHttpCode;
import com.v.hana.entity.user.User;
import com.v.hana.exception.user.UserEmailDuplicateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@TypeInfo(name = "UserExceptionControllerAdvice", description = "유저관리 예외 처리 컨트롤러 어드바이스 클래스")
@RestControllerAdvice(basePackageClasses = User.class)
public class UserExceptionControllerAdvice {
    @MethodInfo(name = "handleUserEmailDuplicateException", description = "회원가입 아이디 중복 예외를 처리합니다.")
    @ExceptionHandler(UserEmailDuplicateException.class)
    public ResponseEntity<BaseExceptionResponse> handleMethodArgumentNotValidException(
            UserEmailDuplicateException exception) {
        return ResponseEntity.status(GlobalHttpCode.BAD_REQUEST.getHttpStatus())
                .body(
                        BaseExceptionResponse.of(
                                UserHttpCode.USER_EMAIL_DUPLICATE.getHttpStatus(),
                                UserHttpCode.USER_EMAIL_DUPLICATE.getCode(),
                                exception.getMessage(),
                                exception.getClass().getSimpleName()));
    }
}
