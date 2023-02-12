package com.my.repairagency007.controller.command.craftsman;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.TestSession;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.CommonEntity.getTestRequestDTO;
import static com.my.repairagency007.CommonEntity.getTestUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SetStartRepairCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testExecuteGoodPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        HttpSession session = testRequest.getSession();
        session.setAttribute("logged_user", getTestUserDTO());

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("request-id")).thenReturn("1");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        doNothing().when(requestService).setStartRepair(getTestRequestDTO(), getTestUserDTO());

        String path = new SetStartRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=craftsmanRequest", path);
        assertEquals("label.successSetStartRepair", testRequest.getSession().getAttribute("message"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        HttpSession session = testRequest.getSession();
        session.setAttribute("logged_user", getTestUserDTO());

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("request-id")).thenReturn("1");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        doThrow(new ServiceException()).when(requestService).setStartRepair(getTestRequestDTO(), getTestUserDTO());

        String path = new SetStartRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=setStartRepair", path);
        assertEquals("error.errorSetStartRepair", testRequest.getSession().getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        HttpSession session = testRequest.getSession();
        session.setAttribute("logged_user", getTestUserDTO());
        when(request.getMethod()).thenReturn("GET");
        session.setAttribute("error", "error.errorSetStartRepair");

        String path = new SetStartRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=craftsmanRequest", path);
        assertEquals("error.errorSetStartRepair", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

}