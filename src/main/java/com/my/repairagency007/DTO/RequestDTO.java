package com.my.repairagency007.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"user_id", "description", "date", "repairer_id", "totalCost"})
@Builder
public class RequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int user_id;

    private String userLastName;

    private String userFirstName;

    private String description;

    private String date;

    private String completionStatus;

    private int repairer_id;

    private String repairerLastName;

    private String repairerFirstName;

    private String paymentStatus;

    private String totalCost;
}
