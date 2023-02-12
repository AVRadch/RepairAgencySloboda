package com.my.repairagency007.controller.command.craftsman;

import com.my.repairagency007.TestRequest;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.FeedbackServiceImpl;
import com.my.repairagency007.util.query.FeedbackQueryBuilder;
import com.my.repairagency007.util.query.QueryBuilder;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.my.repairagency007.CommonEntity.getTestListFeedbackDTO;
import static com.my.repairagency007.CommonEntity.getTestListRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CraftsmanFeedmackCommandTest {


    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final FeedbackServiceImpl feedbackService = mock(FeedbackServiceImpl.class);

    @Test
    void testAllFeedbacksList() throws ServiceException {

        TestRequest testRequest = new TestRequest(request);

        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("0");
        when(appContext.getFeedbackService()).thenReturn(feedbackService);
        when(feedbackService.getAll(getQueryBuilder(request).getQuery())).thenReturn(getTestListFeedbackDTO());
        when(feedbackService.getNumberOfRecords(getQueryBuilder(request).getRecordQuery())).thenReturn(5);

        String forward = new CraftsmanFeedmackCommand(appContext).execute(testRequest, response);

        assertEquals("feedbacksForCraftsman.jsp", forward);
        assertEquals(getTestListFeedbackDTO(), testRequest.getAttribute("feedbackDTOS"));
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        return new FeedbackQueryBuilder()
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits(request.getParameter("offset"), request.getParameter("records"));
    }

}