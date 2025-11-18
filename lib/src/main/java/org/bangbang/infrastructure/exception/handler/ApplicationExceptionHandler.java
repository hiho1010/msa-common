package org.bangbang.infrastructure.exception.handler;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.bangbang.infrastructure.exception.ApplicationException;
import org.bangbang.infrastructure.exception.ErrorCode;
import org.bangbang.infrastructure.exception.ErrorResponse;
import org.bangbang.infrastructure.exception.FieldError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ErrorResponse<Void>> handleApplicationException(ApplicationException e) {
        log.error("ApplicationException", e);

        return ResponseEntity
            .status(e.getStatusCode())
            .body(
                new ErrorResponse<>(e.getCode(), e.getMessage(), null)
            );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<FieldError>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors()
            .stream().map(
                it -> new FieldError(it.getField(), it.getRejectedValue(), it.getDefaultMessage())
            ).toList();

        List<FieldError> globalErrors = e.getBindingResult().getGlobalErrors()
            .stream().map(
                it -> new FieldError("global", null, it.getDefaultMessage())
            ).toList();

        List<FieldError> allErrors = new ArrayList<>();
        allErrors.addAll(fieldErrors);
        allErrors.addAll(globalErrors);

        ErrorCode errorCode = ErrorCode.REQUEST_VALIDATION_ERROR;
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(new ErrorResponse<>(errorCode.getCode(), errorCode.getDefaultMessage(), allErrors));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse<Void>> handleAllUncaughtException(Exception e) {
        log.error("Exception", e);

        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity
            .status(internalServerError.getHttpStatus())
            .body(
                new ErrorResponse<>(internalServerError.getCode(), internalServerError.getDefaultMessage(), null));
    }
}
