package org.comcom.config.security;

import org.comcom.dto.ApiError;
import org.comcom.dto.ApiResponse;
import org.comcom.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handle(MethodArgumentNotValidException argumentNotValidException) {
        String errorMessage = new ArrayList<>(argumentNotValidException.getAllErrors()).get(0).getDefaultMessage();
        return buildErrorResponse(new BaseException(errorMessage, errorMessage, BAD_REQUEST.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handle(HttpMessageNotReadableException exception) {
        return buildErrorResponse(new BaseException(exception.getMessage(), exception.getMessage(), BAD_REQUEST.value()));
    }

    @ExceptionHandler(BadInputException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadInputException(BadInputException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflictException(ConflictException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbiddenException(ForbiddenException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiResponse<Void>> handleInternalServerException(InternalServerException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(UnauthorizedException exception) {
        return buildErrorResponse(exception);
    }

    @ExceptionHandler(UnavailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnavailableException(UnavailableException exception) {
        return buildErrorResponse(exception);
    }

    private ResponseEntity<ApiResponse<Void>> buildErrorResponse(BaseException exception) {
        ApiResponse<Void> apiResponse = new ApiResponse<>(new ApiError(exception.getStatusCode(), exception.getMessage()));
        logger.error(exception.getDetail());
        return new ResponseEntity<>(apiResponse, null, OK);
    }
}
