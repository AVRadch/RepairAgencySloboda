package com.my.repairagency007.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

@SuppressWarnings("serial")
public class RoleTag extends TagSupport {

    private String role;

    private String account;
    public void setRole(String role) {
        this.role = role;
    }
    public void setAccount(String account) {this.account = account;}
    @Override
    public int doStartTag() throws JspException {
        try {
            String to = null;
            if ("manager".equalsIgnoreCase(role)||"craftsman".equalsIgnoreCase(role)) {
                to = "Hello, " + role;
                pageContext.getOut().write(" " + to + "      ");
            } else {
                to = "Welcome, " + role;
                pageContext.getOut().write(" " + to + "     <br> ");
                pageContext.getOut().write("Your account " + account + " грн.");
            }

        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
