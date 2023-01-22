package com.my.repairagency007.util;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class MapperDTOUtil {

    private static final Logger log = LoggerFactory.getLogger(MapperDTOUtil.class);

    public static Request convertDTOToRequest(RequestDTO requestDTO) {
        int completionStatusNumber = CompletionStatus.valueOf(requestDTO.getCompletionStatus().toUpperCase()).ordinal();
        log.debug("Completion Status = " + completionStatusNumber++);
        int paymentStatusNumber = PaymentStatus.valueOf(requestDTO.getPaymentStatus().toUpperCase()).ordinal();
        log.debug("Payment Status = " + paymentStatusNumber++);
        int intTotalCost = (int) Math.round(Double.parseDouble(requestDTO.getTotalCost()) * 100);
        return Request.builder()
                .user_id(requestDTO.getUser_id())
                .description(requestDTO.getDescription())
                .date(LocalDate.parse(requestDTO.getDate()))
                .completionStatusId(completionStatusNumber)
                .repairer_id(requestDTO.getRepairer_id())
                .paymentStatusId(paymentStatusNumber)
                .totalCost(intTotalCost)
                .build();
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
        int intAccount = (int) Math.round(Double.parseDouble(userDTO.getAccount()) * 100);
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

    public static void fillUserDTO(HttpServletRequest request, UserDTO userDTO) {
        userDTO.setEmail(request.getParameter("email").trim());
        userDTO.setFirstName(request.getParameter("firstname").trim());
        userDTO.setLastName(request.getParameter("lastname").trim());
        userDTO.setPhoneNumber(request.getParameter("phoneNumber").trim());
    }

    public static void fillRequestDTO(HttpServletRequest request, RequestDTO requestDTO){

        requestDTO.setCompletionStatus(request.getParameter("completionStatus").trim());
        requestDTO.setPaymentStatus(request.getParameter("paymentStatus").trim());
        requestDTO.setRepairer_id(Integer.parseInt(request.getParameter("repairer-id")));
        requestDTO.setTotalCost(request.getParameter("totalCost").replace(",", ".").trim());
    }

    //public static void fillRequestDTO
}
