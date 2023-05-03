package GLINS_BE.GLINS.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // TODO: FE랑 상태코드 맞추기
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT,"user email duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user doesn't exist"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"category doesn't exist"),
    STATE_NOT_FOUND(HttpStatus.NOT_FOUND,"state doesn't exist"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT,"User nickname duplicated"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "client doesn't exist"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "password is not matched"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post doesn't exist"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "comment doesn't exist"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is not valid."),
    INVALID_TOKEN_LOGOUT(HttpStatus.UNAUTHORIZED, "token is not valid because you logged out."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "user authorization failed"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "database error")
    ;
    private HttpStatus status;
    private String message;
}