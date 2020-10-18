package com.browarna.railwaycarsserving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemoIdListAndStatementIdDto {
    private List<Long> memoIds;
    private Long statementId;
}
