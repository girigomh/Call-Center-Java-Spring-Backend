package org.comcom.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean isValid;
    private ApiError apiError;
    private Integer apiSuccess;
    private T data;

    public ApiResponse(ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiResponse(boolean isValid, Integer apiSuccess, T data) {
        this.isValid = isValid;
        this.apiSuccess = apiSuccess;
        this.data = data;
    }
}
