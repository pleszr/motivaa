package com.motivaa.control.errorHandling;

import com.motivaa.control.errorHandling.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {
    private static final String ERROR_MESSAGE_KEY = "errorMessage";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> NotFoundException(NotFoundException ex) {
        UUID exceptionUuid = UUID.randomUUID();
        log.error("NotFoundException with uuid: {}, message: {}",exceptionUuid.toString(),ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                exceptionUuid,
                createNewErrorList(ERROR_MESSAGE_KEY, ex.getMessage()));
        System.out.println(errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        UUID exceptionUuid = UUID.randomUUID();
        List<ErrorMessageField> errorMessageFieldList = new ArrayList<>();
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        fieldErrors.forEach(fieldError ->
            addErrorToList(
                    errorMessageFieldList,
                    fieldError.getField(),
                    fieldError.getDefaultMessage())
        );
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                exceptionUuid,
                errorMessageFieldList);
        log.error("MethodArgumentNotValidException with uuid {}: {}",exceptionUuid,errorMessage);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex) {
        UUID exceptionUuid = UUID.randomUUID();
        log.error("GlobalException: {}",ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exceptionUuid,
                createNewErrorList(ERROR_MESSAGE_KEY, "Some error occurred on server, please try later."));
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ErrorMessageField> createNewErrorList(String key, String msg) {
        ErrorMessageField message = new ErrorMessageField(key, msg);
        List<ErrorMessageField> errorMessageFields = new ArrayList<>();
        errorMessageFields.add(message);
        return errorMessageFields;
    }

    private void addErrorToList(List<ErrorMessageField> errorMessageFields, String key, String msg) {
        ErrorMessageField message = new ErrorMessageField(key, msg);
        errorMessageFields.add(message);
    }

}
