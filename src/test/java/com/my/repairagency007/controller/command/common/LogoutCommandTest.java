package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.TestRequest;
import com.my.repairagency007.exception.ServiceException;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class LogoutCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void testExecute() throws ServiceException {
        TestRequest testRequest = new TestRequest(request);
        testRequest.getSession().setAttribute("logged_user", UserDTO.builder().build());
        testRequest.getSession().setAttribute("language", "en");
        assertEquals("login.jsp", new LogoutCommand().execute(testRequest, response));
        assertEquals("en", testRequest.getSession().getAttribute("language"));
    }

}