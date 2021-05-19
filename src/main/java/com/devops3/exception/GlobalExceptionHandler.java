package com.devops3.exception;

import com.devops3.dto.EntityDTO;
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
    public ResponseEntity<EntityDTO> existingUserException(ExistingUserException ex) {
        EntityDTO dto = new EntityDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "EXISTING USER"));
        dto.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());

        return new ResponseEntity<>(dto, HttpStatus.NOT_ACCEPTABLE);         // 406
    }

    @ExceptionHandler({AccessException.class})
    public ResponseEntity<EntityDTO> loggedInUserException(AccessException ex) {
        EntityDTO dto = new EntityDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "CONFLICT"));
        dto.setResponseCode(HttpStatus.CONFLICT.value());

        // 409
        return new ResponseEntity<EntityDTO>(dto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<EntityDTO> userNotFoundException(UserNotFoundException ex) {
        EntityDTO dto = new EntityDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "NOT FOUND"));
        dto.setResponseCode(HttpStatus.NOT_FOUND.value());

        // 404
        return new ResponseEntity<EntityDTO>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EntityDTO> handleGenericException(RuntimeException ex) {
        EntityDTO dto = new EntityDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, ex.getMessage()));
        dto.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());

        // 406
        return new ResponseEntity<EntityDTO>(dto, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<EntityDTO> handleGenericException(EmptyFieldException ex) {
        EntityDTO dto = new EntityDTO();
        dto.setStatus(Status.FAILURE);
        dto.setError(buildExceptionResponse(ex, "EMPTY FIELD"));
        dto.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());

        // 406
        return new ResponseEntity<EntityDTO>(dto, HttpStatus.NOT_ACCEPTABLE);
    }

    private ExceptionResponse buildExceptionResponse(Exception ex, String errorCode){
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(errorCode);
        response.setTimestamp(LocalDateTime.now());
        return response;
    }


}
