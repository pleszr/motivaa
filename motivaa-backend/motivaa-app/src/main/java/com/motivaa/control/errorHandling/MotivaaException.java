package com.motivaa.control.errorHandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MotivaaException extends RuntimeException {
    ErrorCode errorCode;
    HttpStatus httpStatus;
    public MotivaaException(
            ErrorCode errorCode,
            String message
    ) {
        super(message);
        this.errorCode = errorCode;
        setHttpStatus();
    }
    public String getErrorCode() {
        return errorCode.toString();
    }

    private void setHttpStatus() {
        switch (errorCode.toString()) {
            case "NOT_FOUND":
                this.httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "Missing_Field":
            case "BAD_REQUEST":
                this.httpStatus = HttpStatus.BAD_REQUEST;
                break;
            default:
                this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

//    if (errorCode.toString().equals("NOT_FOUND")) {
//        this.httpStatus = HttpStatus.NOT_FOUND;
//    } else {
//        this.httpStatus = HttpStatus.BAD_REQUEST;
//    }

    @Override
    public String toString() {
        return String.format("MotivaaException{errorCode=%s, message=%s}", errorCode, getMessage());
    }
}
