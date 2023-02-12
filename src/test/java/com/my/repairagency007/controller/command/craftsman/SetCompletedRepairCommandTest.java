package com.my.repairagency007.controller.command.craftsman;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.command.admin.UpdateRequestAdminCommand;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.CommonEntity.getTestRequestDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SetCompletedRepairCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testExecutePost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("request-id")).thenReturn("1");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        doNothing().when(requestService).setCompletedRepair(getTestRequestDTO());

        String path = new SetCompletedRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=craftsmanRequest", path);
        assertEquals("label.successSetStatus", testRequest.getSession().getAttribute("message"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("request-id")).thenReturn("1");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        doThrow(new ServiceException()).when(requestService).setCompletedRepair(getTestRequestDTO());

        String path = new SetCompletedRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=setCompletedRepair", path);
        assertEquals("error.errorSetStatus", testRequest.getSession().getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }

    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.errorUpdateRequest");

        String path = new SetCompletedRepairCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=craftsmanRequest", path);
        assertEquals("error.errorUpdateRequest", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }
}