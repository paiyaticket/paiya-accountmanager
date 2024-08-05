package events.paiya.accountmanager.errors;

import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.resources.ApiErrorResource;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.internal.error.message",
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return new ResponseEntity<>(apiErrorResource, apiErrorResource.getStatus());
    }


    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class, UserAlreadyExistException.class})
    public ResponseEntity<Object> handleBadParameter(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(apiErrorResource, apiErrorResource.getStatus());
    }
}
