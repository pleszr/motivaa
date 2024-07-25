package com.motivaa.control.utility;

public class Constants {
    public static final String UUID_REGEX = "^[a-f0-9]{8}-[a-f0-9]{4}-[1-5][a-f0-9]{3}-[89ab][a-f0-9]{3}-[a-f0-9]{12}$";
    public static final String NAME_REGEX = "^[a-zA-Z']{1,30}$";

    private Constants() {
        throw new UnsupportedOperationException("Constants is a utility class and cannot be instantiated");
    }
}
