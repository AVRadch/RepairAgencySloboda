package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteUserCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @Test
    void testExecutePost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(request.getParameter("user-id")).thenReturn("1");
        doNothing().when(userService).delete(1);

        String path = new DeleteUserCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllUsers", path);
        assertEquals("message.successDelete", testRequest.getSession().getAttribute("message"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(request.getParameter("user-id")).thenReturn("1");
        doThrow(new ServiceException()).when(userService).delete(1);

        String path = new DeleteUserCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllUsers", path);
        assertEquals("error.errorDelete", testRequest.getSession().getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.errorDelete");

        String path = new DeleteUserCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllUsers", path);
        assertEquals("error.errorDelete", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }
}