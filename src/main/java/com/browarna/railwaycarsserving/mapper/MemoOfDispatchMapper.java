package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.*;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class MemoOfDispatchMapper {
    @Autowired
    private DeliveryOfWagonMapper deliveryOfWagonMapper;
    @Autowired
    private SignerMapper signerMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "memoOfDispatchId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
//    @Mapping(target = "cargoOperation", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "deliveryOfWagonList", ignore = true)
    @Mapping(target = "signer", ignore = true)
    public abstract MemoOfDispatch map(MemoOfDispatchDto memoOfDispatchDto);

    @Mapping(target = "deliveryOfWagonList", expression = "java(mapToDtoDeliveryOfWagonList(memoOfDispatch))")
    @Mapping(target = "signer", expression = "java(mapToDtoSigner(memoOfDispatch))")
    @Mapping(target = "customer", expression = "java(mapToDtoCustomer(memoOfDispatch))")
    @Mapping(target = "author", expression = "java(getName(memoOfDispatch))")
    public abstract MemoOfDispatchDto mapToDto(MemoOfDispatch memoOfDispatch);

    String getName(MemoOfDispatch memoOfDispatch) {

        return memoOfDispatch.getAuthor().getInitials();
    }

    List<DeliveryOfWagonDto> mapToDtoDeliveryOfWagonList(MemoOfDispatch memoOfDispatch) {
        if (memoOfDispatch.getDeliveryOfWagonList() != null) {
            List<DeliveryOfWagonDto> deliveryOfWagonDtoList = memoOfDispatch.getDeliveryOfWagonList().stream()
                    .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon))
                    .collect(Collectors.toList());
            return deliveryOfWagonDtoList;
        } else {
            return null;
        }
    }

    SignerDto mapToDtoSigner(MemoOfDispatch memoOfDispatch) {
        if (memoOfDispatch.getSigner() != null) {
            SignerDto signerDto = signerMapper.mapToDto(memoOfDispatch.getSigner());
            return signerDto;
        } else {
            return new SignerDto();
        }
    }

    CustomerDto mapToDtoCustomer(MemoOfDispatch memoOfDispatch) {
        if (memoOfDispatch.getCustomer() != null) {
            CustomerDto customerDto = customerMapper.mapToDto(memoOfDispatch.getCustomer());
            return customerDto;
        } else {
            return null;
        }
    }
}
