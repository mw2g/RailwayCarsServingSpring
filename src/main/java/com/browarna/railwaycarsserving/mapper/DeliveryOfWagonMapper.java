package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DeliveryOfWagonMapper {

    @Autowired
    protected MapperService mapperService;

//    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "memoOfDelivery", expression = "java(mapperService.idToMemoOfDelivery(deliveryOfWagonDto.getMemoOfDelivery()))")
    @Mapping(target = "memoOfDispatch", expression = "java(mapperService.idToMemoOfDispatch(deliveryOfWagonDto.getMemoOfDispatch()))")
    @Mapping(target = "cargoType", expression = "java(mapperService.typeNameToCargoType(deliveryOfWagonDto.getCargoType()))")
    @Mapping(target = "cargoOperation", expression = "java(mapperService.operationNameToCargoOperation(deliveryOfWagonDto.getCargoOperation()))")
    @Mapping(target = "wagonType", expression = "java(mapperService.typeNameToWagonType(deliveryOfWagonDto.getWagonType()))")
    @Mapping(target = "wagon", expression = "java(mapperService.wagonNumberToWagon(deliveryOfWagonDto.getWagon()))")
    @Mapping(target = "owner", expression = "java(mapperService.ownerNameToOwner(deliveryOfWagonDto.getOwner()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerNameToCustomer(deliveryOfWagonDto.getCustomer()))")
    public abstract DeliveryOfWagon map(DeliveryOfWagonDto deliveryOfWagonDto);

    @Mapping(target = "author", expression = "java(deliveryOfWagon.getAuthor().getInitials())")
    @Mapping(target = "memoOfDelivery", expression = "java(mapperService.getMemoOfDeliveryId(deliveryOfWagon.getMemoOfDelivery()))")
    @Mapping(target = "memoOfDispatch", expression = "java(mapperService.getMemoOfDispatchId(deliveryOfWagon.getMemoOfDispatch()))")
    @Mapping(target = "cargoType", expression = "java(deliveryOfWagon.getCargoType().getTypeName())")
    @Mapping(target = "cargoOperation", expression = "java(deliveryOfWagon.getCargoOperation().getOperationName())")
    @Mapping(target = "wagonType", expression = "java(deliveryOfWagon.getWagonType().getTypeName())")
    @Mapping(target = "wagon", expression = "java(deliveryOfWagon.getWagon().getWagonNumber())")
    @Mapping(target = "owner", expression = "java(deliveryOfWagon.getOwner().getOwnerName())")
    @Mapping(target = "customer", expression = "java(deliveryOfWagon.getCustomer().getCustomerName())")
    public abstract DeliveryOfWagonDto mapToDto(DeliveryOfWagon deliveryOfWagon);

}
