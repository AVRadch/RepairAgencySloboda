package com.my.repairagency007.controller.command.common;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateFeedbackCommandTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final RequestServiceImpl requestService = mock(RequestServiceImpl.class);
    private final FeedbackServiceImpl feedbackService = mock(FeedbackServiceImpl.class);

    @Test
    void testExecute() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);
        FeedbackDTO feedbackDTO = null;
        testRequest.getSession().setAttribute("request-id", "1");

        mockRequestParameter();

        when(appContext.getRequestService()).thenReturn(requestService);
        when(appContext.getFeedbackService()).thenReturn(feedbackService);
        when(requestService.getById(1)).thenReturn(getTestRequestDTO());

        doNothing().when(feedbackService).create(feedbackDTO);

        String forward = new CreateFeedbackCommand(appContext).execute(testRequest, response);

        assertEquals("controller?action=userFeedbacks", forward);
    }

    private void mockRequestParameter() {
        when(request.getParameter("feedback")).thenReturn("feedback");
        when(request.getParameter("rating")).thenReturn("10");
        when(request.getParameter("date")).thenReturn(LocalDate.now().toString());
        when(request.getMethod()).thenReturn("POST");
    }

    private RequestDTO getTestRequestDTO() {
        return RequestDTO.builder()
                .id(1)
                .user_id(3)
                .userLastName("LastName")
                .userFirstName("FirstName")
                .description("Description")
                .repairer_id(2)
                .repairerFirstName("RepairFirstName")
                .repairerLastName("RepairLastName")
                .build();
    }

}