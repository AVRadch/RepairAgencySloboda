package com.my.repairagency007.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Data
@EqualsAndHashCode(of = {"user_id", "description", "date", "repairer_id", "totalCost"})
@Builder
public class Request {

    private static final long serialVersionUID = 1L;

    private int id;

    private int user_id;

    private String description;

    private LocalDate date;

    private int completionStatusId;

    private int repairer_id;

    private int paymentStatusId;

    private int totalCost;
}
