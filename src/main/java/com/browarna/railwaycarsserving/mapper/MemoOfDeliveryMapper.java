package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.dto.SignerDto;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MemoOfDeliveryMapper {
    @Autowired
    private DeliveryOfWagonMapper deliveryOfWagonMapper;
    @Autowired
    private SignerMapper signerMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "memoOfDeliveryId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "deliveryOfWagonList", ignore = true)
    @Mapping(target = "signer", ignore = true)
//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    public abstract MemoOfDelivery map(MemoOfDeliveryDto memoOfDeliveryDto);

//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    @Mapping(target = "deliveryOfWagonList", expression = "java(mapToDtoDeliveryOfWagonList(memoOfDelivery))")
    @Mapping(target = "signer", expression = "java(mapToDtoSigner(memoOfDelivery))")
    @Mapping(target = "customer", expression = "java(mapToDtoCustomer(memoOfDelivery))")
    @Mapping(target = "author", expression = "java(getName(memoOfDelivery))")
    public abstract MemoOfDeliveryDto mapToDto(MemoOfDelivery memoOfDelivery);

    String getName(MemoOfDelivery memoOfDelivery) {

        return memoOfDelivery.getAuthor().getInitials();
    }

    List<DeliveryOfWagonDto> mapToDtoDeliveryOfWagonList(MemoOfDelivery memoOfDelivery) {
        if (memoOfDelivery.getDeliveryOfWagonList() != null) {
            List<DeliveryOfWagonDto> deliveryOfWagonDtoList = memoOfDelivery.getDeliveryOfWagonList().stream()
                    .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon))
                    .collect(Collectors.toList());
            return deliveryOfWagonDtoList;
        } else {
            return null;
        }
    }

    SignerDto mapToDtoSigner(MemoOfDelivery memoOfDelivery) {
        if (memoOfDelivery.getSigner() != null) {
            SignerDto signerDto = signerMapper.mapToDto(memoOfDelivery.getSigner());
            return signerDto;
        } else {
            return new SignerDto();
        }
    }

    CustomerDto mapToDtoCustomer(MemoOfDelivery memoOfDelivery) {
        if (memoOfDelivery.getCustomer() != null) {
            CustomerDto customerDto = customerMapper.mapToDto(memoOfDelivery.getCustomer());
            return customerDto;
        } else {
            return null;
        }
    }
}
