package com.devops3.dto;

import com.devops3.exception.ExceptionResponse;
import com.devops3.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityDTO<T> {

    private T data;
    private Status status;
    private ExceptionResponse error;
    private Integer responseCode;

    public EntityDTO() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExceptionResponse getError() {
        return error;
    }

    public void setError(ExceptionResponse error) {
        this.error = error;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public static final class EntityDTOBuilder {
        private EntityDTO entityDTO;

        private EntityDTOBuilder() {
            entityDTO = new EntityDTO<>();
        }

        public static EntityDTOBuilder anEntityDTO() {
            return new EntityDTOBuilder();
        }

        public EntityDTOBuilder withData(Object data) {
            entityDTO.setData(data);
            return this;
        }

        public EntityDTOBuilder withStatus(Status status) {
            entityDTO.setStatus(status);
            return this;
        }

        public EntityDTOBuilder withExceptionResponse(ExceptionResponse exceptionResponse) {
            entityDTO.setError(exceptionResponse);
            return this;
        }

        public EntityDTO build() {
            return entityDTO;
        }
    }
}
