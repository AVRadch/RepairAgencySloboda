package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static com.my.repairagency007.CommonEntity.*;
import static org.mockito.Mockito.*;

class UpdateRequestAdminCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testExecuteGoodPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        setRequestParametr();

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        when(userService.getById(3)).thenReturn(getTestUserDTO());
        when(userService.getById(2)).thenReturn(getTestUserDTO());
        doNothing().when(requestService).update(getTestRequestDTO());

        String path = new UpdateRequestAdminCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=adminAllRequest", path);
        assertEquals("label.succesUpdate", testRequest.getSession().getAttribute("message"));
        assertEquals(getTestRequestDTO(), testRequest.getAttribute("requestDTO"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    @Test
    void testExecuteBadPost() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        setRequestParametr();


        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());
        doThrow(new ServiceException()).when(requestService).getById(1);

        String path = new UpdateRequestAdminCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=updateRequest", path);
        assertEquals("error.errorUpdateRequest", testRequest.getSession().getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("message"));
    }


    @Test
    void testExecuteGet() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getMethod()).thenReturn("GET");
        HttpSession session = testRequest.getSession();
        session.setAttribute("error", "error.errorUpdateRequest");

        String path = new UpdateRequestAdminCommand(appContext).execute(testRequest, response);

        assertEquals("editUser.jsp", path);
        assertEquals("error.errorUpdateRequest", testRequest.getAttribute("error"));
        assertNull(testRequest.getSession().getAttribute("error"));
    }

    private void setRequestParametr() {
        when(request.getParameter("request-id")).thenReturn("1");
        when(request.getParameter("completionStatus")).thenReturn(CompletionStatus.NOT_STARTED.getName());
        when(request.getParameter("paymentStatus")).thenReturn(PaymentStatus.PAID.getName());
        when(request.getParameter("repairer-id")).thenReturn("2");
        when(request.getParameter("totalCost")).thenReturn("1000.00");
    }
}