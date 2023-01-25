package com.my.repairagency007.controller.command.admin;

import com.my.repairagency007.controller.context.AppContext;
import com.my.repairagency007.model.DAO.UserDAO;
import com.my.repairagency007.model.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.controller.Path;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User profile controller command.
 *
 * @author Aleksey Serdyukov
 */
public class ProfileCommand implements Command {
  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();

    if (session.getAttribute("newUser") != null) {
      User newUser = (User) session.getAttribute("newUser");
      request.setAttribute("fullUser", newUser);
    }

    if (request.getParameter("user_id") != null) {
      int id = Integer.parseInt(request.getParameter("user_id"));
      show(request, id);
    }

    return Path.PAGE_PROFILE;
  }

  private void show(HttpServletRequest request, int id) {
    UserDAO userDAO = new UserDAOImpl(AppContext.getDataSource());
    User user = null;
    try {
      user = userDAO.getEntityById(id).orElse(null);
    } catch (DAOException e) {
      throw new RuntimeException(e);
    }
    user.setRoleId(user.getRoleId());
    request.setAttribute("fullUser", user);
  }
}
