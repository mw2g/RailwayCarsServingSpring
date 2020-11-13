package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseRateAndPenaltyResponse {
    private Double baseRate;
    private Double penalty;
}
