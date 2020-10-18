package com.browarna.railwaycarsserving.dto;

import com.browarna.railwaycarsserving.model.CargoType;
import com.browarna.railwaycarsserving.model.Owner;
import com.browarna.railwaycarsserving.model.WagonType;
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
    private MemoOfDispatchDto memoOfDispatch;
    private CargoOperationDto cargoOperation;
    private CargoType cargoType;
    private WagonDto wagon;
    private WagonType wagonType;
    private Owner owner;
    private CustomerDto customer;
    private String author;
}
