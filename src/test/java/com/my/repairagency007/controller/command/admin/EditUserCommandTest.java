package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.repairagency007.CommonEntity.getTestUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditUserCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    @Test
    void testExecute() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getMethod()).thenReturn("GET");
        when(appContext.getUserService()).thenReturn(userService);
        when(request.getParameter("user-id")).thenReturn("1");
        when(userService.getById(1)).thenReturn(getTestUserDTO());

        String forward = new EditUserCommand(appContext).execute(testRequest, response);

        assertEquals("editUser.jsp", forward);
        assertEquals(getTestUserDTO(), testRequest.getSession().getAttribute("userDTO"));
    }
}