package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementWithRateRequest {
    private Long deliveryId;
    private Long payTime;
    private Date date;
}
