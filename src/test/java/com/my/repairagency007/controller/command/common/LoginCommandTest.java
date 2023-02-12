package com.my.repairagency007.controller.command.common;


import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.IncorrectPasswordException;
import com.my.repairagency007.exception.NoSuchUserException;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.CommonEntity.getTestUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @Test
    void testExecutePost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getParameter("email")).thenReturn("asw1@aa.aaa");
        when(request.getParameter("password")).thenReturn("Aa111111");

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.login("asw1@aa.aaa", "Aa111111")).thenReturn(getTestUserDTO());
 //       when(bCrypt.checkpw(isA(String.class), isA(String.class))).thenReturn(true);

        String path = new LoginCommand(appContext).execute(testRequest, response);

        assertEquals(getTestUserDTO(), testRequest.getSession().getAttribute("logged_user"));
        assertEquals(getTestUserDTO().getRole(), testRequest.getSession().getAttribute("logged_user_role"));
        assertEquals("controller?action=userRequest", path);
    }

    @Test
    void testWrongEmail() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getParameter("email")).thenReturn("asw1@aa.aaa");
        when(request.getParameter("password")).thenReturn("Aa111111");

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.login("asw1@aa.aaa", "Aa111111")).thenThrow(new NoSuchUserException());

        String path = new LoginCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=login", path);
        assertEquals("error.emailNoUser", testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testWrongPassword() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getParameter("email")).thenReturn("asw1@aa.aaa");
        when(request.getParameter("password")).thenReturn("Aa111111");

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.login("asw1@aa.aaa", "Aa111111")).thenThrow(new IncorrectPasswordException());

        String path = new LoginCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=login", path);
        assertEquals("error.passwordWrong", testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.emailNoUser");

        String path = new LoginCommand(appContext).execute(testRequest, response);

        assertEquals("login.jsp", path);
        assertEquals("error.emailNoUser", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }
}