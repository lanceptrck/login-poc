package com.devops3.dto;

import com.devops3.exception.ExceptionResponse;
import com.devops3.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityDTO<T> {

    private List<Data> data;
    private Status status;
    private ExceptionResponse error;
    private Integer responseCode;

    public EntityDTO() {

    }

    public List<Data> getData() {
        return data;
    }

    public void addData(Data d){
        if(data == null)
            data = new ArrayList<>();
        data.add(d);
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

}
