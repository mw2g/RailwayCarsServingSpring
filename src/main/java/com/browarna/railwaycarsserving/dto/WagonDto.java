package com.browarna.railwaycarsserving.dto;

import com.browarna.railwaycarsserving.model.Owner;
import com.browarna.railwaycarsserving.model.WagonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WagonDto {
    private Long wagonId;
    private String wagonNumber;
    private WagonType wagonType;
    private Owner owner;
}
