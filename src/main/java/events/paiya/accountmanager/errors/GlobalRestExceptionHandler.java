package events.paiya.accountmanager.errors;

import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.resources.ApiErrorResource;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    
    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResource> handleNotFound(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.internal.error.message",
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class, UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResource> handleBadParameter(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }
}
