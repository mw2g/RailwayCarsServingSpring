package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOfWagonDto {
    private Long deliveryId;
    private Instant created;
    private Date startDate;
    private Date endDate;
    private double cargoWeight;
    private boolean loadUnloadWork;
    private double shuntingWorks;
    private MemoOfDeliveryDto memoOfDelivery;
    private CargoOperationDto cargoOperation;
    private WagonDto wagon;
    private CustomerDto customer;
    private String author;
}
