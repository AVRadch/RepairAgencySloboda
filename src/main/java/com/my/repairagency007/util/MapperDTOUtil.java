package com.my.repairagency007.util;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.exception.IncorrectFormatException;
import com.my.repairagency007.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static com.my.repairagency007.util.ValidatorUtil.*;

public class MapperDTOUtil {

    private static final Logger log = LoggerFactory.getLogger(MapperDTOUtil.class);

    public static Request convertDTOToRequest(RequestDTO requestDTO) {

        Request request = Request.builder().build();
        int requestId = requestDTO.getId();
        if (requestId != 0){
            log.debug("request id = " + requestDTO.getId());
            request.setId(requestId);
        }

        log.debug("user_ id" + requestDTO.getUser_id());
        request.setUser_id(requestDTO.getUser_id());
        log.debug("description = " + requestDTO.getDescription());
        request.setDescription(requestDTO.getDescription());
        log.debug("date = " + LocalDate.parse(requestDTO.getDate()));
        request.setDate(LocalDate.parse(requestDTO.getDate()));
        int completionStatusNumber = CompletionStatus.valueOf(requestDTO.getCompletionStatus().toUpperCase()).ordinal();
        log.debug("Completion Status = " + completionStatusNumber++);
        request.setCompletionStatusId(completionStatusNumber);
        log.debug("repairer_id = " + requestDTO.getRepairer_id());
        request.setRepairer_id(requestDTO.getRepairer_id());
        int paymentStatusNumber = PaymentStatus.valueOf(requestDTO.getPaymentStatus().toUpperCase()).ordinal();
        log.debug("Payment Status = " + paymentStatusNumber++);
        request.setPaymentStatusId(paymentStatusNumber);
        if (requestDTO.getTotalCost() != null) {
            log.debug("text field TotalCost = " + requestDTO.getTotalCost().replace(",", ".").trim());
            int intTotalCost = (int) Math.round(Double.parseDouble(requestDTO.getTotalCost().replace(",", ".").trim()) * 100);
            log.debug("total Cost = " + intTotalCost);
            request.setTotalCost(intTotalCost);
        }
        return request;
    }

    /**
     * Converts Request and 2 User into RequestDTO
     * @param request, user, repairer to convert
     * @return UserDTO
     */

    public static RequestDTO convertRequestToDTO(Request request, User user){

        String formattedTotalCost = new DecimalFormat("#0.00").format(request.getTotalCost()/100.0);
        RequestDTO requestDTO = RequestDTO.builder().build();
        log.debug("req id = " + request.getId());
        requestDTO.setId(request.getId());
        log.debug("req id = " + request.getUser_id());
        requestDTO.setUser_id(request.getUser_id());
        log.debug("req id = " + user.getFirstName());
        requestDTO.setUserFirstName(user.getFirstName());
        log.debug("req id = " + user.getLastName());
        requestDTO.setUserLastName(user.getLastName());
        log.debug("req id = " + request.getDescription());
        requestDTO.setDescription(request.getDescription());
        log.debug("req id = " + request.getDate().toString());
        requestDTO.setDate(request.getDate().toString());
        log.debug("Completion status = " + CompletionStatus.getCompletionStatusId(request).getName());
        requestDTO.setCompletionStatus(CompletionStatus.getCompletionStatusId(request).getName());
        log.debug("Payment status = " + PaymentStatus.getPaymentStatusId(request).getName());
        requestDTO.setPaymentStatus(PaymentStatus.getPaymentStatusId(request).getName());
        requestDTO.setTotalCost(formattedTotalCost);

        return requestDTO;
    }

    /**
     * Converts User into UserDTO
     * @param user to convert
     * @return UserDTO
     */
    public static UserDTO convertUserToDTO(User user){
 //       log.debug("Role = " + Role.getRole(user));
        String formattedAccount = new DecimalFormat("#0.00").format(user.getAccount()/100.0);
        return UserDTO.builder()
                .id(user.getId())
                .notification(user.getNotification())
                .phoneNumber(user.getPhoneNumber())
                .account(formattedAccount)
                .status(user.getStatus())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(Role.getRole(user).getName())
                .build();
    }

    /**
     * Converts UserDTO into User
     * @param userDTO to convert
     * @return User
     */
    public static User convertDTOToUser(UserDTO userDTO) {

        int intAccount;
        if (userDTO.getAccount() != null) {
        intAccount = (int) Math.round(Double.parseDouble(userDTO.getAccount().replace(",", ".").trim()) * 100);
        } else {
            intAccount = 0;
        }
        log.debug("intAccount = " + intAccount);
        log.debug("Role userDTO = " + userDTO.getRole().toUpperCase());
        log.debug("Role = " + Role.valueOf(userDTO.getRole().toUpperCase()));
        log.debug("Role number = " + Role.valueOf(userDTO.getRole().toUpperCase()).ordinal());
        int roleNumber = Role.valueOf(userDTO.getRole().toUpperCase()).ordinal();
        log.debug("Role userDTO = " + userDTO.getRole());
        log.debug("Number of role userDTO = " + roleNumber++);
        return User.builder()
                .id(userDTO.getId())
                .notification(userDTO.getNotification())
                .phoneNumber(userDTO.getPhoneNumber())
                .account(intAccount)
                .status(userDTO.getStatus())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .roleId(roleNumber)
                .build();
    }

    /**
     * Converts FeedbackDTO into Feedback
     * @param feedbackDTO to convert
     * @return Feedback
     */
    public static Feedback convertDTOToFeedback(FeedbackDTO feedbackDTO) {
        return Feedback.builder()
                .id(feedbackDTO.getId())
                .repairerId(feedbackDTO.getRepairerId())
                .date(LocalDate.parse(feedbackDTO.getDate()))
                .feedback(feedbackDTO.getFeedback())
                .rating(feedbackDTO.getRating())
                .requestId(feedbackDTO.getRequestId())
                .build();
    }

    /**
     * Converts Feedback, User, Request DTO into FeedbackDTO
     * @param feedback, repairer, requestDTO to convert
     * @return UserDTO
     */
    public static FeedbackDTO convertFeedbackToDTO(Feedback feedback, RequestDTO requestDTO){
        return FeedbackDTO.builder()
                .id(feedback.getId())
                .repairerId(requestDTO != null ? requestDTO.getRepairer_id() : 0)
                .repairerFirstName(requestDTO != null ? requestDTO.getRepairerFirstName(): null)
                .repairerLastName(requestDTO != null ? requestDTO.getRepairerLastName(): null)
                .date(feedback.getDate().toString())
                .feedback(feedback.getFeedback())
                .rating(feedback.getRating())
                .requestId(feedback.getRequestId())
                .userFirstName(requestDTO != null ? requestDTO.getUserFirstName(): null)
                .userLastName(requestDTO != null ? requestDTO.getUserLastName() : null)
                .requestDescription(requestDTO != null ? requestDTO.getDescription() : null)
                .build();
    }

    public static void fillUserDTO(HttpServletRequest request, UserDTO userDTO, ValidatorUtil validatorUtil) {

        userDTO.setEmail(request.getParameter("email").trim());
        validatorUtil.validateEmail(userDTO.getEmail());
        userDTO.setFirstName(request.getParameter("firstname").trim());
        validatorUtil.validateName(userDTO.getFirstName(), "error.firstNameFormat");
        log.info("Name => " + userDTO.getFirstName() + " " + request.getParameter("firstname"));
        userDTO.setLastName(request.getParameter("lastname").trim());
        validatorUtil.validateName(userDTO.getLastName(), "error.lastNameFormat");
        log.info("Surname => " + userDTO.getLastName() + " " + request.getParameter("lastname"));
        userDTO.setPhoneNumber(request.getParameter("phoneNumber").trim());
        validatorUtil.validatePhoneNumber(userDTO.getPhoneNumber());
    }

    public static void fillRequestDTO(HttpServletRequest request, RequestDTO requestDTO){

        requestDTO.setCompletionStatus(request.getParameter("completionStatus").trim());
        requestDTO.setPaymentStatus(request.getParameter("paymentStatus").trim());
        requestDTO.setRepairer_id(Integer.parseInt(request.getParameter("repairer-id")));
        requestDTO.setTotalCost(request.getParameter("totalCost").replace(",", ".").trim());
    }

    public static void fillFeedbackDTO(HttpServletRequest request, RequestDTO requestDTO, FeedbackDTO feedbackDTO){
        log.debug("Repairer id = " + requestDTO.getRepairer_id());
        feedbackDTO.setRepairerId(requestDTO.getRepairer_id());
        log.debug("Repairer First Name = " + requestDTO.getRepairerFirstName());
        feedbackDTO.setRepairerFirstName(requestDTO.getRepairerFirstName());
        log.debug("Repairer Last Name = " + requestDTO.getRepairerLastName());
        feedbackDTO.setRepairerLastName(requestDTO.getRepairerLastName());
        log.debug("Feedback = " + request.getParameter("feedback"));
        feedbackDTO.setFeedback(request.getParameter("feedback"));
        log.debug("Rating = " + request.getParameter("rating"));
        feedbackDTO.setRating(Integer.parseInt(request.getParameter("rating")));
        feedbackDTO.setDate(LocalDate.now().toString());
        log.debug("request id = " + requestDTO.getId());
        feedbackDTO.setRequestId(requestDTO.getId());
        feedbackDTO.setUserFirstName(requestDTO.getUserFirstName());
        feedbackDTO.setUserLastName(requestDTO.getUserLastName());
        feedbackDTO.setRequestDescription(requestDTO.getDescription());
    }

    //public static void fillRequestDTO
}
