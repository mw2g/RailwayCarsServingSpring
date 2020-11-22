package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {
    private Long statementId;
    private Date created;
    private String comment;
    private String author;
    private List<MemoOfDispatchDto> memoOfDispatchList;
    private String cargoOperation;
    private CustomerDto customer;
    private String signer;
}
