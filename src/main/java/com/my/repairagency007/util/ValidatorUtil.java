package com.my.repairagency007.util;

import com.my.repairagency007.exception.IncorrectFormatException;

public final class ValidatorUtil {

    public static void validateDescription(String name) throws IncorrectFormatException {
        validateFormat(name, "^[\\wА-ЩЬЮЯҐІЇЄа-щьюяґіїє'.,;:+\\-~`!@#$^&*()={}| ]{1,200}", "error.description.format");
    }

    private static void validateFormat(String name, String regex,String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }
}
