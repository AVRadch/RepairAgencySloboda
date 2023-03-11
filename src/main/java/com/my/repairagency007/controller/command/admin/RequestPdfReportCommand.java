package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import com.my.repairagency007.util.PDFUtil;
import com.my.repairagency007.util.query.QueryBuilder;
import com.my.repairagency007.util.query.RequestQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class RequestPdfReportCommand  implements Command {

    private static final Logger log = LoggerFactory.getLogger(RequestPdfReportCommand.class);

    private final RequestServiceImpl requestService;

    private final PDFUtil pdfUtil;
    /**
     * @param appContext using for get the value of FeedbackServiceImpl and RequestServiceImpl
     * instance to use in command
     */
    public RequestPdfReportCommand(AppContext appContext) {
        requestService = appContext.getRequestService();
        pdfUtil = appContext.getPdfUtil();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {

        List<RequestDTO> requests;

        log.info("создание списка реквестов");
        QueryBuilder queryBuilder = getQueryBuilder(request);
        String language = (String) request.getSession().getAttribute("language");
        requests = requestService.getAll(queryBuilder.getQuery());
        ByteArrayOutputStream adminReportPdf = pdfUtil.createPdfRequests(requests, language);
        setResponse(response, adminReportPdf);
        return "controller?action=adminAllRequest";
    }

    private QueryBuilder getQueryBuilder(HttpServletRequest request) {
        String statusString = "";
        String repairerString = "";
        HttpSession session = request.getSession();
        if (request.getParameter("status") != "" && request.getParameter("status") != null) {
            int status = Integer.parseInt(request.getParameter("status"));
            session.setAttribute("status", status);
            if (status > -1){
                statusString = String.valueOf(status+1);
            }
        }
        if (request.getParameter("repairer") != "" && request.getParameter("repairer") != null){
            int repairer = Integer.parseInt(request.getParameter("repairer"));
            session.setAttribute("repairer", repairer);
            if (repairer > 0) {
                repairerString = String.valueOf(repairer);
            }
        }
        log.info("status => " + statusString + " repairer => " + repairerString);
        return new RequestQueryBuilder()
                .setReparierFilter(repairerString)
                .setCompletionStatusFilter(statusString)
                .setDateFilter(request.getParameter("date"))
                .setSortField(request.getParameter("sort"))
                .setOrder(request.getParameter("order"))
                .setLimits("0", String.valueOf(Integer.MAX_VALUE));
    }

    private void setResponse(HttpServletResponse response, ByteArrayOutputStream output) {
        response.setContentType("application/pdf");
        response.setContentLength(output.size());
        response.setHeader("Content-Disposition", "attachment; filename=\"requests.pdf\"");
        try (OutputStream outputStream = response.getOutputStream()) {
            output.writeTo(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
