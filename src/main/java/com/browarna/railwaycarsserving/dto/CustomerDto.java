package com.browarna.railwaycarsserving.dto;

import com.browarna.railwaycarsserving.model.Signer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long customerId;
    private Instant created;
    private String author;
    private String customerName;
    private String customerFullName;
    private List<SignerDto> signerList;
}
