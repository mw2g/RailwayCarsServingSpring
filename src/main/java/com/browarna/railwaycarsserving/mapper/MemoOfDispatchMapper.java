package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MemoOfDispatchMapper {
    @Autowired
    protected MapperService mapperService;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "cargoOperation", expression = "java(mapperService.operationNameToCargoOperation(memoOfDispatchDto.getCargoOperation()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerNameToCustomer(memoOfDispatchDto.getCustomer().getCustomerName()))")
    @Mapping(target = "signer", expression = "java(mapperService.signerInitialsToSigner(memoOfDispatchDto.getSigner()))")
    @Mapping(target = "deliveryOfWagonList", expression = "java(mapperService.mapDeliveryOfWagonList(memoOfDispatchDto.getDeliveryOfWagonList()))")
    @Mapping(target = "statement", expression = "java(mapperService.statementIdToStatement(memoOfDispatchDto.getStatement()))")
    public abstract MemoOfDispatch map(MemoOfDispatchDto memoOfDispatchDto);

    @Mapping(target = "author", expression = "java(memoOfDispatch.getAuthor().getInitials())")
    @Mapping(target = "cargoOperation", expression = "java(memoOfDispatch.getCargoOperation().getOperationName())")
    @Mapping(target = "customer", expression = "java(mapperService.mapToDtoCustomer(memoOfDispatch.getCustomer()))")
    @Mapping(target = "signer", expression = "java(mapperService.signerToSignerInitials(memoOfDispatch.getSigner()))")
    @Mapping(target = "deliveryOfWagonList", expression = "java(mapperService.mapToDtoDeliveryOfWagonList(memoOfDispatch.getDeliveryOfWagonList()))")
    @Mapping(target = "statement", expression = "java(mapperService.statementToStatementId(memoOfDispatch.getStatement()))")
    public abstract MemoOfDispatchDto mapToDto(MemoOfDispatch memoOfDispatch);
}
