package GLINS_BE.GLINS.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // TODO: FE랑 상태코드 맞추기
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Password is not matched"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid Request"),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT,"User email duplicated"),
    DUPLICATED_USER_NICKNAME(HttpStatus.CONFLICT,"User nickname duplicated"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Database error"),
    IMAGE_PROCESSING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR,"Image Processing failed"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"Category doesn't exist"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Client doesn't exist"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment doesn't exist"),
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "Follow doesn't exist"),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "Place doesn't exist"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post doesn't exist"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Review doesn't exist"),
    STATE_NOT_FOUND(HttpStatus.NOT_FOUND,"State doesn't exist"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"User doesn't exist"),
    WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Wishlist doesn't exist"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "User authorization failed"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    INVALID_TOKEN_LOGOUT(HttpStatus.UNAUTHORIZED, "Token is invalid because you logged out");

    private HttpStatus status;
    private String message;
}