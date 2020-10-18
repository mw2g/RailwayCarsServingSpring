package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControllerStatementDto {
    private Long statementId;
    private Instant created;
    private String comment;
    private String author;
    private List<MemoOfDispatchDto> memoOfDispatchList;
    private CargoOperationDto cargoOperation;
    private CustomerDto customer;
    private SignerDto signer;
}
