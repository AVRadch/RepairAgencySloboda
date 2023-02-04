package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.CommonEntity.getTestUserDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AddRequestCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testExecutePost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        testRequest.getSession().setAttribute("logged_user",  getTestUserDTO());

        when(request.getParameter("description")).thenReturn("description text");
        when(request.getMethod()).thenReturn("POST");
        when(appContext.getRequestService()).thenReturn(requestService);
        doNothing().when(requestService).create(null);

        String forward = new AddRequestCommand(appContext).execute(testRequest, response);
        assertEquals("controller?action=userRequest", forward);
    }

    @Test
    void testNotAddRequest() throws ServiceException {
        TestRequest testRequest = new TestRequest(request);

        testRequest.getSession().setAttribute("logged_user",  getTestUserDTO());
        when(request.getParameter("description")).thenReturn("description text");
        when(request.getMethod()).thenReturn("POST");
        when(appContext.getRequestService()).thenReturn(requestService);
        doThrow(new ServiceException()).when(requestService).create(isA(RequestDTO.class));

        String forward = new AddRequestCommand(appContext).execute(testRequest, response);
        assertEquals("controller?action=addRequest", forward);
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.createRequest");

        String path = new AddRequestCommand(appContext).execute(testRequest, response);

        assertEquals("requestForUser.jsp", path);
        assertEquals("error.createRequest", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }
}