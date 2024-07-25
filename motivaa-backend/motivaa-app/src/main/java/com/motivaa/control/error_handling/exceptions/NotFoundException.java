package com.motivaa.control.error_handling.exceptions;

public class NotFoundException extends RuntimeException{

        public NotFoundException(String message) {
            super(message);
        }
}
