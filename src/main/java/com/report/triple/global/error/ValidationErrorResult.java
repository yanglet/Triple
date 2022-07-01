package com.report.triple.global.error;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ValidationErrorResult extends ErrorResult{
    private Map<String, String> validation = new HashMap<>();

    public ValidationErrorResult(String statusCode, String message) {
        super(statusCode, message);
    }

    public void addValidationError(String field, String message){
        this.validation.put(field, message);
    }
}
