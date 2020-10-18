package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoOfDeliveryDto {
    private Long memoOfDeliveryId;
    private Instant created;
    private Date startDate;
    private CargoOperationDto cargoOperation;
    private CustomerDto customer;
    private SignerDto signer;
    private String comment;
    private String author;
    private List<DeliveryOfWagonDto> deliveryOfWagonList;
}
