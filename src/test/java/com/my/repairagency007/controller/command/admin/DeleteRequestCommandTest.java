package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DeleteRequestCommandTest {


    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);

}