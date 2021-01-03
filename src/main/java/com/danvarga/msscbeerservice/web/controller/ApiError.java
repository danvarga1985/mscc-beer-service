package com.danvarga.msscbeerservice.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    private int code;
    private String status;
//    private String error;
}
