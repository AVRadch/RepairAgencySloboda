package com.my.repairagency007.controller.command.craftsman;

import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.exception.ServiceException;
import com.my.repairagency007.model.services.impl.RequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SetCompletedRepairCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(SetCompletedRepairCommand.class);

    private final RequestServiceImpl requestService;

    public SetCompletedRepairCommand() {
        requestService = AppContext.getAppContext().getRequestService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();

        String forward = "controller?action=craftsmanRequest";
        RequestDTO requestDTO = requestService.getById(Integer.parseInt(request.getParameter("request-id")));

        requestService.setCompletedRepair(requestDTO);
        return forward;
    }

}
