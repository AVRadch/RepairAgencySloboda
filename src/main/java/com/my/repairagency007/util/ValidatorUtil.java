package com.my.repairagency007.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

public final class ValidatorUtil {

    public Optional<ArrayList<String>> list;

    public ValidatorUtil() {
        list = Optional.empty();
    }
    private static final Logger log = LoggerFactory.getLogger(ValidatorUtil.class);

    public void validateDescription(String name){
        validateFormat(name, "^[\\wА-ЯҐІЇЄа-яёЁґіїє'.,;:+\\-~`!@#$^&*()={}| ]{1,200}", "error.description.format");
    }

    public void validateEmail(String email){
        validateFormat(email, "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", "error.emailFormat");
    }

    public void validateName(String name, String message){
        validateFormat(name, "^[A-Za-zА-ЯҐІЇЄа-яЁёґіїє'\\- ]{1,30}", message);
    }

    public void validatePhoneNumber(String phoneNumber){
        validateFormat(phoneNumber, "^(\\+\\d{1,3})?[- ]?\\d{2,3}[- ]?\\d{2,4}[- ]?\\d{2}[- ]?\\d{2}$",
                "error.phoneNumberFormat");
    }

    public void validateAccount(String account){
        validateFormat(account, "^-?\\d+\\.?\\,?\\d*$", "error.accountFormat");
    }

    private void validateFormat(String name, String regex,String message){
        if (name == null || !name.matches(regex)) {
            list.ifPresentOrElse(l -> l.add(message),
                    () -> {
                        ArrayList<String> newList = new ArrayList<>();
                        newList.add(message);
                        list = Optional.of(newList);
                    });
        }
    }
}
