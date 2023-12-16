package org.comcom.utils;

import org.comcom.dto.ApiResponse;
import org.springframework.http.HttpStatus;

public class ApiResponseUtils {
    public static  <T> ApiResponse<T> buildResponse(T data) {
        return new ApiResponse<>(true, HttpStatus.OK.value(), data);
    }
}
