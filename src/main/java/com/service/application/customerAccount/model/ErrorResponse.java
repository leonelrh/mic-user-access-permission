package com.service.application.customerAccount.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ErrorResponse {

    @JsonIgnore
    private int statusCode;
    private String message;
    @JsonIgnore
    private String description;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


    public ErrorResponse(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }

}
