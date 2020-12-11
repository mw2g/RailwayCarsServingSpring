package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeriodCustomerOperationDto {
    private Date afterDate;
    private Date beforeDate;
    private String operation;
    private String customer;
}
