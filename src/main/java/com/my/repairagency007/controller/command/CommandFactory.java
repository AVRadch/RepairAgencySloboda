package com.my.repairagency007.controller.command;

import com.my.repairagency007.controller.command.admin.*;
import com.my.repairagency007.controller.command.common.*;
import com.my.repairagency007.controller.command.craftsman.CraftsmanFeedmackCommand;
import com.my.repairagency007.controller.command.craftsman.CraftsmanRequestCommand;
import com.my.repairagency007.controller.command.craftsman.SetCompletedRepairCommand;
import com.my.repairagency007.controller.command.craftsman.SetStartRepairCommand;
import com.my.repairagency007.controller.command.user.*;
import com.my.repairagency007.controller.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static final Logger log = LoggerFactory.getLogger(CommandFactory.class);

    private static CommandFactory factory;
    private static final Map<String, Command> commands = new HashMap<>();

    private CommandFactory() {}

    /**
     * Singleton.
     */

    public static CommandFactory commandFactory() {
        if (factory == null) {
            factory = new CommandFactory();
            log.debug("Create command factory");
        }
        return factory;
    }

    static {

        //craftsman commands
        commands.put("craftsmanRequest", new CraftsmanRequestCommand(AppContext.getAppContext()));
        commands.put("craftsmanFeedbacks", new CraftsmanFeedmackCommand(AppContext.getAppContext()));
        commands.put("setStartRepair", new SetStartRepairCommand(AppContext.getAppContext()));
        commands.put("setCompletedRepair", new SetCompletedRepairCommand(AppContext.getAppContext()));
        //user commands
        commands.put("userRequest", new UserRequestCommand(AppContext.getAppContext()));
        commands.put("updateProfile", new UserProfileCommand(AppContext.getAppContext()));
        commands.put("userEditRequest", new UserEditRequestCommand());
        commands.put("userUpdateRequest", new UserUpdateRequestCommand());
        commands.put("userFeedbacks", new UserFeedbacksCommand(AppContext.getAppContext()));
        commands.put("addFeedback", new AddFeedbackCommand(AppContext.getAppContext()));
        commands.put("createFeedback", new CreateFeedbackCommand(AppContext.getAppContext()));
        commands.put("changePassword", new ChangePasswordCommand(AppContext.getAppContext()));
        // common commands
        commands.put("login", new LoginCommand(AppContext.getAppContext()));
        commands.put("logout", new LogoutCommand());
        commands.put("redirect", null);
        commands.put("addRequest", new AddRequestCommand(AppContext.getAppContext()));
        commands.put("error", new ErrorCommand());
        //admin commands
        commands.put("registration", new RegistrationCommand());
        commands.put("adminAllRequest", new AllRequestCommand(AppContext.getAppContext()));
        commands.put("adminAllUsers", new AllUsersCommand(AppContext.getAppContext()));
        commands.put("adminAllFeedbacks", new AllFeedbacksCommand(AppContext.getAppContext()));
        commands.put("deleteRequest", new DeleteRequestCommand(AppContext.getAppContext()));
        commands.put("deleteFeedback", new DeleteFeedbackCommand(AppContext.getAppContext()));
        commands.put("deleteUser", new DeleteUserCommand(AppContext.getAppContext()));
        commands.put("editUser", new EditUserCommand(AppContext.getAppContext()));
        commands.put("updateUser", new UpdateUserCommand(AppContext.getAppContext()));
        commands.put("updateRequest", new UpdateRequestAdminCommand(AppContext.getAppContext()));
        commands.put("addUser", new AddUserCommand());
        commands.put("editRequest", new EditRequestCommand(AppContext.getAppContext()));
        commands.put("registrationAdmin", new RegistrationAdminCommand(AppContext.getAppContext()));
        commands.put("requestPdfReport", new RequestPdfReportCommand(AppContext.getAppContext()));
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        log.info("action = " + action + " session attribute error = " + request.getSession().getAttribute("error") +
                " request parametr error = " + request.getAttribute("error"));
        return commands.get(action);
    }
}
