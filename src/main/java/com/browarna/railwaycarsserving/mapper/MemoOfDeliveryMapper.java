package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class MemoOfDeliveryMapper {

    @Autowired
    protected MapperService mapperService;

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "cargoOperation", expression = "java(mapperService.operationNameToCargoOperation(memoOfDeliveryDto.getCargoOperation()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerNameToCustomer(memoOfDeliveryDto.getCustomer().getCustomerName()))")
    @Mapping(target = "signer", expression = "java(mapperService.signerInitialsToSigner(memoOfDeliveryDto.getSigner()))")
    @Mapping(target = "deliveryOfWagonList", expression = "java(mapperService.mapDeliveryOfWagonList(memoOfDeliveryDto.getDeliveryOfWagonList()))")
    public abstract MemoOfDelivery map(MemoOfDeliveryDto memoOfDeliveryDto);

    @Mapping(target = "signer", expression = "java(mapperService.signerToSignerInitials(memoOfDelivery.getSigner()))")
    @Mapping(target = "cargoOperation", expression = "java(memoOfDelivery.getCargoOperation().getOperationName())")
    @Mapping(target = "author", expression = "java(memoOfDelivery.getAuthor().getInitials())")
    @Mapping(target = "customer", expression = "java(mapperService.mapToDtoCustomer(memoOfDelivery.getCustomer()))")
    @Mapping(target = "deliveryOfWagonList", expression = "java(mapperService.mapToDtoDeliveryOfWagonList(memoOfDelivery.getDeliveryOfWagonList()))")
    public abstract MemoOfDeliveryDto mapToDto(MemoOfDelivery memoOfDelivery);
}
