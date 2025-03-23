package com.example.demo.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

    @JsonFormat(pattern = "hh-mm-ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;


    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public  ApiResponse(ApiError apiError){
        this();
        this.error=apiError;
    }

    public ApiResponse(){
        this.timeStamp=LocalDateTime.now();
    }

}
