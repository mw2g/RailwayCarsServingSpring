package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRefreshTokenDto {
    private Long refreshTokenId;
    private Date createdDate;
}
