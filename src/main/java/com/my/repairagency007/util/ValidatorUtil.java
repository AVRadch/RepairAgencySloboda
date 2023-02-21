package com.my.repairagency007.util;

import com.my.repairagency007.exception.IncorrectFormatException;

public final class ValidatorUtil {

    public static void validateDescription(String name) throws IncorrectFormatException {
        validateFormat(name, "^[\\wА-ЯҐІЇЄа-яёЁґіїє'.,;:+\\-~`!@#$^&*()={}| ]{1,200}", "error.description.format");
    }

    public static void validateEmail(String email) throws IncorrectFormatException {
        validateFormat(email, "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "error.emailFormat");
    }

    public static void validateName(String name, String message) throws IncorrectFormatException {
        validateFormat(name, "^[A-Za-zА-ЯҐІЇЄа-яЁёґіїє'\\- ]{1,30}", message);
    }

    public static void validatePhoneNumber(String phoneNumber) throws IncorrectFormatException {
        validateFormat(phoneNumber, "^(\\+\\d{1,3})?[- ]?\\d{2,3}[- ]?\\d{2,4}[- ]?\\d{2}[- ]?\\d{2}$",
                "error.phoneNumberFormat");
    }

    public static void validateAccount(String account) throws IncorrectFormatException {
        validateFormat(account, "^-?\\d+\\.?\\,?\\d*$", "error.accountFormat");
    }

    private static void validateFormat(String name, String regex,String message) throws IncorrectFormatException {
        if (name == null || !name.matches(regex))
            throw new IncorrectFormatException(message);
    }
}
