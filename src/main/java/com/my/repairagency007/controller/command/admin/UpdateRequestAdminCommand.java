package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import com.my.repairagency007.model.services.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import static com.my.repairagency007.controller.command.CommandUtility.moveAttributeFromSessionToRequest;
import static com.my.repairagency007.util.MapperDTOUtil.fillRequestDTO;

public class UpdateRequestAdminCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(UpdateRequestAdminCommand.class);

    private final RequestServiceImpl requestService;

    private final UserServiceImpl userService;

    public UpdateRequestAdminCommand() {
        requestService = AppContext.getAppContext().getRequestService();
        userService=AppContext.getAppContext().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        log.debug("get request method" + request.getMethod());
        return request.getMethod().equals("POST") ? executePost(request, response) : executeGet(request);
    }

    private String executeGet(HttpServletRequest request) {
        moveAttributeFromSessionToRequest(request, "message");
        moveAttributeFromSessionToRequest(request, "error");
        return "editUser.jsp";
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        //       RequestDispatcher dispatcher;
        log.debug("Start decode))");
        log.debug("id = " + request.getParameter("request-id"));
        log.debug("description" + request.getParameter("description"));
        log.debug("user-id" + request.getParameter("user-id"));
        log.debug("date" + request.getParameter("date"));
        log.debug("completionStatus" + request.getParameter("completionStatus"));
        log.debug("paymentStatus" + request.getParameter("paymentStatus"));
        log.debug("repairer-id" + request.getParameter("repairer-id"));
        log.debug("totalCost" + request.getParameter("totalCost"));

        RequestDTO requestDTO = requestService.getById(Integer.parseInt(request.getParameter("request-id")));

        fillRequestDTO(request, requestDTO);
        int id = requestDTO.getUser_id();
        log.debug("Get id user for update = " + id);
        UserDTO userDTO = userService.getById(id);
        log.debug("Get user for update " + userDTO.getLastName());
 //       requestDTO.setUserFirstName(userDTO.getFirstName());
 //       requestDTO.setUserLastName(userDTO.getLastName());
        id = requestDTO.getRepairer_id();
        log.debug("Get id repairer for update = " + id);
        if(id != 0) {
            UserDTO repairerDTO = userService.getById(id);
        }
        log.debug("Get repairer for update " + userDTO.getLastName());
        log.debug("Request DTO = " + requestDTO);


 //       fillRequestDTO(request, userDTO);
//        userDTO.setRole(request.getParameter("role"));
 //       userDTO.setAccount(request.getParameter("account").replace(",", "."));

        String forward = "controller?action=adminAllRequest";

        requestService.update(requestDTO);
        if (requestDTO.getRepairer_id() !=0 ){
            requestService.updateRepairForRequest(requestDTO);
        }

        request.getSession().setAttribute("message", "label.succesUpdate");
        request.setAttribute("requestDTO", requestDTO);

        //      try {
//            response.sendRedirect(forward);
//            forward = "controller?action=redirect";
        //           return forward;
        //     } catch (IOException e) {
        //         log.error("Error user update command", e);
        //         forward = "error_page.jsp";
        //     }

        //       dispatcher = request.getRequestDispatcher("editUser.jsp");


        return forward;
    }
}
