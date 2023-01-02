package com.my.repairagency007.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse);
}

