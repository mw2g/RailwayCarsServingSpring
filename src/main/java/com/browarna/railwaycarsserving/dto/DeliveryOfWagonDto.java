package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOfWagonDto {
    private Long deliveryId;
    private Date created;
    private Date startDate;
    private Date endDate;
    private double cargoWeight;
    private boolean loadUnloadWork;
    private double shuntingWorks;
    private Long memoOfDelivery;
    private Long memoOfDispatch;
    private String cargoOperation;
    private String cargoType;
    private String wagon;
    private String wagonType;
    private String owner;
    private String customer;
    private String author;
}
