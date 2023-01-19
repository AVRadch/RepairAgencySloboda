package com.my.repairagency007.controller.command;


import com.my.repairagency007.DTO.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CommandUtility {

    static public Locale getSessionLocale(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("language");
        return locale != null ? Locale.forLanguageTag(locale) : Locale.getDefault();
    }

    /**
     * Move userDTO from session to request and delete user DTO from session
     * @param request passed by action
     */

 /*   public static void moveUserDTOFromSessionToRequest(HttpServletRequest request){

        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user != null) {
            request.setAttribute("user", user);
            request.getSession().removeAttribute("user");
        }
    }  */


    /**
     * Move sessions attributes to request and delete session attributes
     * @param request passed by action
     */
    public static void moveAttributeFromSessionToRequest(HttpServletRequest request, String attributeName) {

        String attributeValue = (String) request.getSession().getAttribute(attributeName);
        if (attributeValue != null) {
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }
}
