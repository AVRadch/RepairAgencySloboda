package com.my.repairagency007.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@EqualsAndHashCode
@Builder
public class Feedback implements Serializable {


    private static final long serialVersionUID = 1L;

    private int id;

    private int repairerId;

    private LocalDate date;

    private String feedback;

    private int rating;

    private int requestId;
}
