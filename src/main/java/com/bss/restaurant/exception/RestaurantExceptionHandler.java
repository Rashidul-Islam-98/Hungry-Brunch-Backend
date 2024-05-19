package com.bss.restaurant.exception;

import com.bss.restaurant.dto.response.RestaurantBadRequestErrorResponse;
import com.bss.restaurant.dto.response.RestaurantErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;
@Slf4j
@RestControllerAdvice
public class RestaurantExceptionHandler {
    public static final String BAD_REQUEST_CODE = "BAD_REQUEST";
    public static final String NOT_FOUND_CODE = "NOT_FOUND";
    public static final String NOT_AUTHORIZED_CODE = "NOT_AUTHENTICATED";
    public static final String FORBIDDEN_CODE = "NOT_AUTHORIZED";
    public static final String UNEXPECTED_ERROR_OCCURRED_MESSAGE = "Unexpected error occurred.";

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RestaurantErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        log.warn("Functional error occurred (not authenticated).", exception);
        String code = NOT_AUTHORIZED_CODE;
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new RestaurantErrorResponse(code, "Not authenticated"));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<RestaurantErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Functional error occurred (not authorized).", exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new RestaurantErrorResponse(FORBIDDEN_CODE, "Not authorized"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RestaurantErrorResponse> handleBadCredentialException(BadCredentialsException exception) {
        log.warn("Functional error occurred; invalid password.", exception);
        String code = BAD_REQUEST_CODE;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(new RestaurantErrorResponse(code, exception.getMessage()));
    }

    @ExceptionHandler(value = RestaurantNotFoundException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException exception) {
        log.warn("Functional error occurred; object cannot be found.", exception);
        String code = NOT_FOUND_CODE;
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity
                .status(status)
                .body(new RestaurantErrorResponse(code, exception.getMessage()));
    }

    @ExceptionHandler(value = RestaurantBadRequestException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantApplicationException(RestaurantBadRequestException exception) {
        log.warn("Functional error occurred; invalid Operation.", exception);
        String code = BAD_REQUEST_CODE;
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(new RestaurantErrorResponse(code, exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<RestaurantBadRequestErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var errors = getValidationErrors(exception.getBindingResult().getAllErrors());
        var errorResponse = new RestaurantBadRequestErrorResponse();
        errorResponse.setErrors(errors);
        log.warn("Functional error occurred; failed to validate the request body.", exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ResponseEntity<RestaurantErrorResponse> handleException(Exception exception) {
        log.error(UNEXPECTED_ERROR_OCCURRED_MESSAGE, exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RestaurantErrorResponse(null, "Unexpected error has occurred."));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<RestaurantErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        log.error(UNEXPECTED_ERROR_OCCURRED_MESSAGE, exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestaurantErrorResponse(BAD_REQUEST_CODE, "Maximum upload size exceeded."));
    }

    @ExceptionHandler(RestaurantImageUploadException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantImageUploadException(RestaurantImageUploadException exception) {
        log.error(UNEXPECTED_ERROR_OCCURRED_MESSAGE, exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestaurantErrorResponse(BAD_REQUEST_CODE, exception.getMessage()));
    }

    @ExceptionHandler(RestaurantImageDeleteException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantImageDeleteException(RestaurantImageDeleteException exception) {
        log.error(UNEXPECTED_ERROR_OCCURRED_MESSAGE, exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestaurantErrorResponse(BAD_REQUEST_CODE, exception.getMessage()));
    }

    private List<String> getValidationErrors(List<ObjectError> errors) {
        return errors
                .stream()
                .map(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    return fieldName + " " + errorMessage;
                }).toList();
    }
}
