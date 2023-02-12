package com.my.repairagency007.controller.command.craftsman;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.command.admin.AllRequestCommand;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.my.repairagency007.CommonEntity.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CraftsmanRequestCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

    @Test
    void testAllRequestsList() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        HttpSession session = testRequest.getSession();
        session.setAttribute("logged_user", getTestUserDTO());

        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("0");
        when(appContext.getRequestService()).thenReturn(requestService);
        when(requestService.getAllForCraftsman(getQueryBuilder(request).getQuery(), 1)).thenReturn(getTestListRequestDTO());
        when(requestService.getNumberOfUserRecords(getQueryBuilder(request).getRecordQuery(), 1)).thenReturn(5);

        String forward = new CraftsmanRequestCommand(appContext).execute(testRequest, response);

        assertEquals("requestsForCraftsman.jsp", forward);
        assertEquals(getTestListRequestDTO(), testRequest.getSession().getAttribute("requestDTOS"));
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new RequestQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}