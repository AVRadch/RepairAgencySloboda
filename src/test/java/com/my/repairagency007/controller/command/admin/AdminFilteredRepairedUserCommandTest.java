package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.repairagency007.CommonEntity.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminFilteredRepairedUserCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @Test
    void testFilteredList() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        when(request.getParameter("reparier-id")).thenReturn("2");
        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("0");

        when(request.getMethod()).thenReturn("POST");
        when(appContext.getUserService()).thenReturn(userService);
        when(appContext.getRequestService()).thenReturn(requestService);
        when(userService.getAllRepairer()).thenReturn(getTestListUserDTO());
        when(requestService.getByReparierId(getQueryBuilder(request).getQuery(), 2)).thenReturn(getTestListRequestDTO());

        String forward = new AdminFilteredRepairedUserCommand(appContext).execute(testRequest, response);

        assertEquals("requestsForAdmin.jsp", forward);
        assertEquals(getTestListRequestDTO(), testRequest.getSession().getAttribute("requestDTOS"));
        assertEquals(getTestListUserDTO(), testRequest.getSession().getAttribute("repairers"));
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new RequestQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }

}