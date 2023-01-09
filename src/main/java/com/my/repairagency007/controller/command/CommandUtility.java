package com.my.repairagency007.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CommandUtility {

    static public Locale getSessionLocale(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("language");
        return locale != null ? Locale.forLanguageTag(locale) : Locale.getDefault();
    }
}
