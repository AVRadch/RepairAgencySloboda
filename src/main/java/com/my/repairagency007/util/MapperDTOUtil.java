package com.my.repairagency007.util;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.controller.command.admin.AllUsersCommand;
import com.my.repairagency007.model.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class MapperDTOUtil {

    private static final Logger log = LoggerFactory.getLogger(MapperDTOUtil.class);

    public static Request convertDTOToRequest(RequestDTO requestDTO) {
        return Request.builder()
                .user_id(requestDTO.getUser_id())
                .description(requestDTO.getDescription())
                .date(LocalDate.parse(requestDTO.getDate()))
                .build();
    }

    public static RequestDTO convertRequestToDTO(Request request, User user, User repairer){
        return RequestDTO.builder()
                .id(request.getId())
                .user_id(request.getUser_id())
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .description(request.getDescription())
                .date(request.getDate().toString())
                .completionStatus(CompletionStatus.getCompletionStatusId(request).getName())
                .repairer_id(request.getRepairer_id())
                .repairerFirstName(repairer.getFirstName())
                .repairerLastName(repairer.getLastName())
                .paymentStatus(PaymentStatus.getPaymentStatusId(request).getName())
                .totalCost(request.getTotalCost())
                .build();
    }

    /**
     * Converts User into UserDTO
     * @param user to convert
     * @return UserDTO
     */
    public static UserDTO convertUserToDTO(User user){
        log.debug("Role = " + Role.getRole(user));
        return UserDTO.builder()
                .id(user.getId())
                .notification(user.getNotification())
                .phoneNumber(user.getPhoneNumber())
                .account(user.getAccount())
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
        return User.builder()
                .id(userDTO.getId())
                .notification(userDTO.getNotification())
                .phoneNumber(userDTO.getPhoneNumber())
                .account(userDTO.getAccount())
                .status(userDTO.getStatus())
                .password(userDTO.getPassword())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .roleId(Role.valueOf(userDTO.getRole()).ordinal())
                .build();
    }

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
}
