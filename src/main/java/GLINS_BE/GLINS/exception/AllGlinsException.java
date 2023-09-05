package GLINS_BE.GLINS.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllGlinsException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;
}
