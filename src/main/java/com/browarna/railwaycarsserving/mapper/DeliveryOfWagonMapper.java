package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DeliveryOfWagonMapper {

    @Autowired
    private MemoOfDeliveryMapper memoOfDeliveryMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SignerMapper signerMapper;

    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "wagon", ignore = true)
    @Mapping(target = "cargoOperation", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "memoOfDelivery", ignore = true)
    public abstract DeliveryOfWagon map(DeliveryOfWagonDto deliveryOfWagonDto);

    @Mapping(target = "author", expression = "java(getName(deliveryOfWagon))")
    @Mapping(target = "memoOfDelivery", expression = "java(mapMemo(deliveryOfWagon))")
    @Mapping(target = "customer", expression = "java(mapToDtoCustomer(deliveryOfWagon))")
    public abstract DeliveryOfWagonDto mapToDto(DeliveryOfWagon deliveryOfWagon);

    String getName(DeliveryOfWagon deliveryOfWagon) {

        return deliveryOfWagon.getAuthor().getLastName();
    }

    CustomerDto mapToDtoCustomer(DeliveryOfWagon deliveryOfWagon) {
        if (deliveryOfWagon.getCustomer() != null) {
            CustomerDto customerDto = customerMapper.mapToDto(deliveryOfWagon.getCustomer());
            return customerDto;
        } else {
            return null;
        }
    }

    MemoOfDeliveryDto mapMemo(DeliveryOfWagon deliveryOfWagon) {
        MemoOfDelivery memoOfDelivery = deliveryOfWagon.getMemoOfDelivery();
        if (memoOfDelivery != null) {
            MemoOfDeliveryDto memoOfDeliveryDto =
                    new MemoOfDeliveryDto(  memoOfDelivery.getMemoId(),
                                            memoOfDelivery.getCreated(),
                                            memoOfDelivery.getStartDate(),
                                            memoOfDelivery.getCargoOperation(),
                                            customerMapper.mapToDto(memoOfDelivery.getCustomer()),
                                            signerMapper.mapToDto(memoOfDelivery.getSigner()),
                                            memoOfDelivery.getComment(),
                            memoOfDelivery.getAuthor().getLastName(),
                                            null
                            );
            return memoOfDeliveryDto;
        } else {
            return new MemoOfDeliveryDto();
        }
    }
}
