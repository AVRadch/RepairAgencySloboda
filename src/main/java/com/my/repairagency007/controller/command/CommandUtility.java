package com.my.repairagency007.controller.command;


import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.common.LoginCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class CommandUtility {

    private static final Logger log = LoggerFactory.getLogger(CommandUtility.class);

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
            log.info("Set attribute = " + attributeName + " set value = " +
             attributeValue);
            request.setAttribute(attributeName, attributeValue);
            request.getSession().removeAttribute(attributeName);
        }
    }
}
