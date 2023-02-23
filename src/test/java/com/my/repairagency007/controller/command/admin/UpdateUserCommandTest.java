package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

import static com.my.repairagency007.CommonEntity.getTestRequestDTO;
import static com.my.repairagency007.CommonEntity.getTestUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateUserCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);
    @Test
    void testExecuteGoodPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        setUserParameter();

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById(1)).thenReturn(getTestUserDTO());
        doNothing().when(userService).update(getTestUserDTO());

        String path = new UpdateUserCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllUsers", path);
        assertEquals("label.succesUpdate", testRequest.getSession().getAttribute("message"));
        assertEquals(getTestUserDTO(), testRequest.getAttribute("userDTO"));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        setUserParameter();
        when(request.getParameter("phoneNumber")).thenReturn("+380(67)85-255-85");

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getById(1)).thenReturn(getTestUserDTO());
        doNothing().when(userService).update(getTestUserDTO());

        String path = new UpdateUserCommand(appContext).execute(testRequest, response);

        assertEquals("modalErrorList.jsp", path);
        ArrayList<String> list = (ArrayList<String>) testRequest.getSession().getAttribute("errorList");
        assertEquals("error.phoneNumberFormat", list.get(0));
        assertNull(testRequest.getSession().getAttribute("message"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("message", "label.succesUpdate");

        String path = new UpdateUserCommand(appContext).execute(testRequest, response);

        assertEquals("editUser.jsp", path);
        assertEquals("label.succesUpdate", testRequest.getAttribute("message"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }
    private void setUserParameter() {
        when(request.getParameter("user-id")).thenReturn("1");
        when(request.getParameter("email")).thenReturn("asw1@aa.aaa");
        when(request.getParameter("firstname")).thenReturn("Alex");
        when(request.getParameter("lastname")).thenReturn("Petrov");
        when(request.getParameter("phoneNumber")).thenReturn("+380972866635");
        when(request.getParameter("role")).thenReturn("user");
        when(request.getParameter("account")).thenReturn("1000.00");
    }
}