package com.my.repairagency007.controller.command.admin;


import com.my.repairagency007.DAO.UserDAO;
import com.my.repairagency007.DAO.implementations.UserDAOImpl;
import com.my.repairagency007.controller.Path;
import com.my.repairagency007.controller.RepairAgencyServlet;
import com.my.repairagency007.controller.command.Command;
import com.my.repairagency007.exception.DAOException;
import com.my.repairagency007.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * New user registration controller command.
 *
 * @author Aleksey Serdyukov
 */
public class RegistrationCommand implements Command {

  private static final Logger log = LoggerFactory.getLogger(RegistrationCommand.class);

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {

    log.debug("Start registration command");
    String email = request.getParameter("email").trim();
    String firstName = request.getParameter("firstname").trim();
    String lastName = request.getParameter("lastname").trim();
    String password = request.getParameter("password").trim();
    String phoneNumber = request.getParameter("phoneNumber").trim();

    int roleId = 3;
    log.debug("Read info from request");
    UserDAO userDAO = new UserDAOImpl();
    /*IContactDetailsService detailsService = AppContext.getInstance().getDetailsService();
    IAccountService accountService = AppContext.getInstance().getAccountService();
    ITariffService tariffService = AppContext.getInstance().getTariffService();

    ContactDetails details = new ContactDetails();
    details.setId(detailsService.getNextIdValue());
    details.setCity(city);
    details.setStreet(street);
    details.setHome(home);
    details.setApartment(apartment);
    details.setEmail(email);
    details.setPhone(phone);
    detailsService.save(details);

    Account account = new Account();
    account.setId(accountService.getNextIdValue());
    account.setNumber(accountService.getNumberContract());
    account.setBalance(BigDecimal.ZERO);
    accountService.save(account);

    Set<Tariff> tariffs;
    if (trafficsId != null) {
      tariffs = new HashSet<>();
      for (String item : trafficsId) {
        tariffs.add(tariffService.find(Long.parseLong(item)));
      }
    } else {
      tariffs = Collections.emptySet();
    }*/
    log.debug("Fill info into User");
    User newUser = new User();
    newUser.setEmail(email);
    newUser.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    newUser.setFirstName(firstName);
    newUser.setLastName(lastName);
    newUser.setRoleId(1);
    newUser.setAccount(0);
    newUser.setPhoneNumber(phoneNumber);
    try {
      log.debug("Try to create new user in Data Base");
      userDAO.create(newUser);
    } catch (DAOException e) {
      throw new RuntimeException(e);
    }


    HttpSession session = request.getSession();
    session.setAttribute("newUser", newUser);
    log.debug("Save new user in session attribute");
    String resp = Path.COMMAND_LOGIN;
    log.debug("Form path to profile command: " + resp);
    try {
      response.sendRedirect(resp);
      resp = Path.COMMAND_REDIRECT;
    } catch (IOException e) {
      resp = Path.PAGE_ERROR_PAGE;
    }
    return null;
  }
}
