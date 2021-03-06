package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoOfDispatchDto {
    private Long memoOfDispatchId;
    private Date created;
    private Date endDate;
    private String comment;
    private String author;
    private List<DeliveryOfWagonDto> deliveryOfWagonList;
    private String cargoOperation;
    private CustomerDto customer;
    private String signer;
    private Long statement;
}
