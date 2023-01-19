package com.my.repairagency007.controller.command;

import com.my.repairagency007.controller.command.admin.*;
import com.my.repairagency007.controller.command.common.LocaleHandlerCommand;
import com.my.repairagency007.controller.command.common.LoginCommand;
import com.my.repairagency007.controller.command.common.LogoutCommand;
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
        }
        return factory;
    }

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("redirect", null);
        commands.put("locale-handler", new LocaleHandlerCommand());
        //admin commands
        commands.put("registration", new RegistrationCommand());
        commands.put("adminAllRequest", new AllRequestCommand());
        commands.put("adminAllUsers", new AllUsersCommand());
        commands.put("adminAllFeedbacks", new AllFeedbacksCommand());
        commands.put("deleteUser", new DeleteUserCommand());
        commands.put("editUser", new EditUserCommand());
        commands.put("updateUser", new UpdateUserCommand());
        commands.put("addUser", new AddUserCommand());
        commands.put("registrationAdmin", new RegistrationAdminCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("action");
        log.debug(action);
        return commands.get(action);
    }
}
