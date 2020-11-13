package com.browarna.railwaycarsserving.dto;

import com.browarna.railwaycarsserving.model.IndexToBaseRate;
import com.browarna.railwaycarsserving.model.Tariff;
import com.browarna.railwaycarsserving.model.TimeNorm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementRateResponse {
    private TimeNorm deliveryDispatchTimeNorm;
    private TimeNorm turnoverTimeNorm;
    private Tariff deliveryDispatchTariff;
    private Tariff shuntingTariff;
    private IndexToBaseRate indexToBaseRate;
}
