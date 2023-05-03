package GLINS_BE.GLINS.exception;

import GLINS_BE.GLINS.response.ErrorResponse;
import GLINS_BE.GLINS.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(AllGlinsException.class)
    public ResponseEntity<?> GlinsExceptionHandler(AllGlinsException e){
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(new ErrorResponse(e.getErrorCode(), e.getErrorCode().getMessage())));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlExceptionHandler(SQLException e){
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DATABASE_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(errorResponse));
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.error(e));
    }
}
