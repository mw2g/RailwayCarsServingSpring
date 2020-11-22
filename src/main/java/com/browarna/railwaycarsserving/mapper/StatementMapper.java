package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.dto.StatementDto;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import com.browarna.railwaycarsserving.model.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StatementMapper {

    @Autowired
    private MemoOfDispatchMapper memoOfDispatchMapper;
    @Autowired
    protected MapperService mapperService;

    @Mapping(target = "statementId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "memoOfDispatchList", ignore = true)
    @Mapping(target = "signer", ignore = true)
    @Mapping(target = "cargoOperation", expression = "java(mapperService.operationNameToCargoOperation(statementDto.getCargoOperation()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerNameToCustomer(statementDto.getCustomer().getCustomerName()))")
    public abstract Statement map(StatementDto statementDto);

    @Mapping(target = "author", expression = "java(mapperService.authorToAuthorInitials(statement.getAuthor()))")
    @Mapping(target = "cargoOperation", expression = "java(statement.getCargoOperation().getOperationName())")
    @Mapping(target = "customer", expression = "java(mapperService.mapToDtoCustomer(statement.getCustomer()))")
    @Mapping(target = "signer", expression = "java(mapperService.signerToSignerInitials(statement.getSigner()))")
    @Mapping(target = "memoOfDispatchList", expression = "java(mapToDtoMemoOfDispatchList(statement.getMemoOfDispatchList()))")
    public abstract StatementDto mapToDto(Statement statement);

    List<MemoOfDispatchDto> mapToDtoMemoOfDispatchList(List<MemoOfDispatch> memoOfDispatchList) {
        if (memoOfDispatchList != null) {
            List<MemoOfDispatchDto> memoOfDispatchDtoList = memoOfDispatchList.stream()
                    .map(memoOfDispatch -> memoOfDispatchMapper.mapToDto(memoOfDispatch))
                    .collect(Collectors.toList());
            return memoOfDispatchDtoList;
        } else {
            return null;
        }
    }
}
