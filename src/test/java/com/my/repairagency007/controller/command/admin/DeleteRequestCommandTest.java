package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.command.common.LoginCommand;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteRequestCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testExecutePost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(request.getParameter("request-id")).thenReturn("1");
        doNothing().when(requestService).delete(1);

        String path = new DeleteRequestCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllRequest", path);
        assertEquals("message.successDelete", testRequest.getSession().getAttribute("message"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecutePostWithExeption() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(request.getParameter("request-id")).thenReturn("1");
        doThrow(new ServiceException()).when(requestService).delete(1);

        String path = new DeleteRequestCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllRequest", path);
        assertEquals("error.errorDelete", testRequest.getSession().getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.errorDelete");

        String path = new DeleteRequestCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllRequest", path);
        assertEquals("error.errorDelete", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

}