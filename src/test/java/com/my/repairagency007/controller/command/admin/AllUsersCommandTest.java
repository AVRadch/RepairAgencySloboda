package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.UserQueryBuilder;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.repairagency007.CommonEntity.getTestListUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AllUsersCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @Test
    void testAllUsersList() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("0");
        when(appContext.getUserService()).thenReturn(userService);
        when(userService.getAll(getQueryBuilder(request).getQuery())).thenReturn(getTestListUserDTO());
        when(userService.getNumberOfRecords(getQueryBuilder(request).getRecordQuery())).thenReturn(5);

        String forward = new AllUsersCommand(appContext).execute(testRequest, response);

        assertEquals("usersForAdmin.jsp", forward);
        assertEquals(getTestListUserDTO(), testRequest.getSession().getAttribute("userDTOS"));
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new UserQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }
}