package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignerDto {
    private Long signerId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String initials;
    private Long customerId;
}
