package com.devops3.exception;

import com.devops3.dto.EntityDTO;
import com.devops3.dto.ErrorDTO;
import com.devops3.model.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ExistingUserException.class})
    public ResponseEntity<ErrorDTO> existingUserException(ExistingUserException ex) {
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "EXISTING USER"));
        dto.setResponseCode(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);         // 409
    }

    @ExceptionHandler({AccessException.class})
    public ResponseEntity<ErrorDTO> loggedInUserException(AccessException ex) {
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "Not acceptable"));
        dto.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());

        // 406
        return new ResponseEntity<>(dto, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> userNotFoundException(UserNotFoundException ex) {
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "NOT FOUND"));
        dto.setResponseCode(HttpStatus.NOT_FOUND.value());

        // 404
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ErrorDTO> handleGenericException(EmptyFieldException ex) {
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "Empty field"));
        dto.setResponseCode(HttpStatus.NO_CONTENT.value());

        // 204
        return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
    }

    private ExceptionResponse buildExceptionResponse(Exception ex, String errorCode){
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(errorCode);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }


}
