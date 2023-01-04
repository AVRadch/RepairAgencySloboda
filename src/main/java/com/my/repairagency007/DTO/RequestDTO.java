package com.my.repairagency007.DTO;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

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

    private int totalCost;
}
