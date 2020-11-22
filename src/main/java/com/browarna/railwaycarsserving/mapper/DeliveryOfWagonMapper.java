package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DeliveryOfWagonMapper {

    @Autowired
    protected MapperService mapperService;

//    @Mapping(target = "memoOfDelivery", expression = "java(mapperService.idToMemoOfDelivery(deliveryOfWagonDto.getMemoOfDelivery()))")
//    @Mapping(target = "memoOfDispatch", expression = "java(mapperService.idToMemoOfDispatch(deliveryOfWagonDto.getMemoOfDispatch()))")
//    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "memoOfDelivery", ignore = true)
    @Mapping(target = "memoOfDispatch", ignore = true)
    @Mapping(target = "cargoType", expression = "java(mapperService.typeNameToCargoType(deliveryOfWagonDto.getCargoType()))")
    @Mapping(target = "cargoOperation", expression = "java(mapperService.operationNameToCargoOperation(deliveryOfWagonDto.getCargoOperation()))")
    @Mapping(target = "wagonType", expression = "java(mapperService.typeNameToWagonType(deliveryOfWagonDto.getWagonType()))")
    @Mapping(target = "wagon", expression = "java(mapperService.wagonNumberToWagon(deliveryOfWagonDto.getWagon()))")
    @Mapping(target = "owner", expression = "java(mapperService.ownerNameToOwner(deliveryOfWagonDto.getOwner()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerNameToCustomer(deliveryOfWagonDto.getCustomer()))")
    public abstract DeliveryOfWagon map(DeliveryOfWagonDto deliveryOfWagonDto);

    @Mapping(target = "author", expression = "java(mapperService.authorToAuthorInitials(deliveryOfWagon.getAuthor()))")
    @Mapping(target = "memoOfDelivery", expression = "java(mapperService.getMemoOfDeliveryId(deliveryOfWagon.getMemoOfDelivery()))")
    @Mapping(target = "memoOfDispatch", expression = "java(mapperService.getMemoOfDispatchId(deliveryOfWagon.getMemoOfDispatch()))")
    @Mapping(target = "cargoType", expression = "java(mapperService.cargoTypeToCargoTypeName(deliveryOfWagon.getCargoType()))")
    @Mapping(target = "cargoOperation", expression = "java(mapperService.cargoOperationToCargoOperationName(deliveryOfWagon.getCargoOperation()))")
    @Mapping(target = "wagonType", expression = "java(mapperService.wagonTypeToWagonTypeName(deliveryOfWagon.getWagonType()))")
    @Mapping(target = "wagon", expression = "java(mapperService.wagonToWagonNumber(deliveryOfWagon.getWagon()))")
    @Mapping(target = "owner", expression = "java(mapperService.ownerToOwnerName(deliveryOfWagon.getOwner()))")
    @Mapping(target = "customer", expression = "java(mapperService.customerToCustomerName(deliveryOfWagon.getCustomer()))")
    public abstract DeliveryOfWagonDto mapToDto(DeliveryOfWagon deliveryOfWagon);

}
