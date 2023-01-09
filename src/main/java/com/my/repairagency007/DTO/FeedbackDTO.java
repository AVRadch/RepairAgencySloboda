package com.my.repairagency007.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Builder
public class FeedbackDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private int repairerId;

    @EqualsAndHashCode.Exclude private String repairerFirstName;

    @EqualsAndHashCode.Exclude private String repairerLastname;

    private String date;

    private String feedback;

    private int rating;

    private int requestId;

    @EqualsAndHashCode.Exclude private String userFirstName;

    @EqualsAndHashCode.Exclude private String userLastName;

    @EqualsAndHashCode.Exclude private String requestDescription;
}
