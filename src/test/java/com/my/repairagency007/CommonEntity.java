package com.my.repairagency007;

import com.my.repairagency007.DTO.FeedbackDTO;
import com.my.repairagency007.DTO.RequestDTO;
import com.my.repairagency007.DTO.UserDTO;
import com.my.repairagency007.model.entity.CompletionStatus;
import com.my.repairagency007.model.entity.PaymentStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommonEntity {


    public static List<RequestDTO> getTestListRequestDTO() {
        List<RequestDTO> requestDTOS = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            requestDTOS.add(getTestRequestDTO());
        }
        return requestDTOS;
    }

    public static List<UserDTO> getTestListUserDTO() {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            userDTOS.add(getTestUserDTO());
        }
        return userDTOS;
    }

    public static List<FeedbackDTO> getTestListFeedbackDTO() {
        List<FeedbackDTO> feedbackDTOS = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            feedbackDTOS.add(getTestFeedbackDTO());
        }
        return feedbackDTOS;
    }
    public static RequestDTO getTestRequestDTO() {
        return RequestDTO.builder()
                .id(1)
                .user_id(3)
                .userFirstName("Alex")
                .userLastName("Petrov")
                .description("description")
                .date(LocalDate.now().toString())
                .completionStatus(CompletionStatus.NOT_STARTED.getName())
                .repairer_id(2)
                .repairerFirstName("John")
                .repairerLastName("Polansky")
                .paymentStatus(PaymentStatus.PAID.getName())
                .totalCost("1000.00")
                .build();
    }

    public static UserDTO getTestUserDTO() {
        return UserDTO.builder()
                .id(1)
                .notification("notification")
                .phoneNumber("+380972866635")
                .account("1000.00")
                .status("registred")
                .password("Aa111111")
                .firstName("Alex")
                .lastName("Petrov")
                .email("asw1@aa.aaa")
                .role("user")
                .build();
    }

    public static FeedbackDTO getTestFeedbackDTO() {
        return FeedbackDTO.builder()
                .id(1)
                .repairerId(1)
                .repairerFirstName("Nikolas")
                .repairerLastName("Paleus")
                .date(LocalDate.now().toString())
                .feedback("All very good")
                .rating(9)
                .requestId(1)
                .userFirstName("Ivan")
                .userLastName("Bogun")
                .requestDescription("Repair back roller")
                .build();
    }
}
