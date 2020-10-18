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
public class MemoOfDispatchDto {
    private Long memoOfDispatchId;
    private Instant created;
    private Date endDate;
    private String comment;
    private String author;
    private List<DeliveryOfWagonDto> deliveryOfWagonList;
    private CargoOperationDto cargoOperation;
    private CustomerDto customer;
    private SignerDto signer;
}
