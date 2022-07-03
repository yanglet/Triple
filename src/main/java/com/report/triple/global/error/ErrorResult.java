package com.report.triple.global.error;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResult {
    private String statusCode;
    private String message;

    public static ErrorResult of(String statusCode, String message){
        ErrorResult errorResult = new ErrorResult();
        errorResult.setStatusCode(statusCode);
        errorResult.setMessage(message);

        return errorResult;
    }
}
