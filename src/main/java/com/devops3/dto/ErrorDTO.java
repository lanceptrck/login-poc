package com.devops3.dto;

import com.devops3.exception.ExceptionResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO extends GenericDTO {

    private ExceptionResponse error;

    public ExceptionResponse getError() {
        return error;
    }

    public void setError(ExceptionResponse error) {
        this.error = error;
    }
}
