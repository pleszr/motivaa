package com.motivaa.control.error_handling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
    private int httpCode;
    private UUID exceptionUuid;
    private List<ErrorMessageField> errors;
}