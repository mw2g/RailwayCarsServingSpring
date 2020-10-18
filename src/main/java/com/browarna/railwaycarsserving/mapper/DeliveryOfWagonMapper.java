package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
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
    @Autowired
    private CargoOperationMapper cargoOperationMapper;
    @Autowired
    private MemoOfDispatchMapper memoOfDispatchMapper;

    @Mapping(target = "deliveryId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "wagon", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "memoOfDelivery", ignore = true)
    @Mapping(target = "memoOfDispatch", ignore = true)
//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    public abstract DeliveryOfWagon map(DeliveryOfWagonDto deliveryOfWagonDto);

//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    @Mapping(target = "author", expression = "java(getName(deliveryOfWagon))")
    @Mapping(target = "memoOfDelivery", expression = "java(mapMemoOfDelivery(deliveryOfWagon))")
    @Mapping(target = "memoOfDispatch", expression = "java(mapMemoOfDispatch(deliveryOfWagon))")
    @Mapping(target = "customer", expression = "java(mapToDtoCustomer(deliveryOfWagon))")
    public abstract DeliveryOfWagonDto mapToDto(DeliveryOfWagon deliveryOfWagon);

    String getName(DeliveryOfWagon deliveryOfWagon) {

        return deliveryOfWagon.getAuthor().getInitials();
    }

    MemoOfDispatchDto mapMemoOfDispatch(DeliveryOfWagon deliveryOfWagon) {

        MemoOfDispatch memoOfDispatch = deliveryOfWagon.getMemoOfDispatch();
        if (memoOfDispatch != null) {
            MemoOfDispatchDto memoOfDispatchDto =
                    new MemoOfDispatchDto(  memoOfDispatch.getMemoOfDispatchId(),
                            memoOfDispatch.getCreated(),
                            memoOfDispatch.getEndDate(),
                            memoOfDispatch.getComment(),
                            memoOfDispatch.getAuthor().getInitials(),
                            null,
                            cargoOperationMapper.mapToDto(memoOfDispatch.getCargoOperation()),
                            customerMapper.mapToDto(memoOfDispatch.getCustomer()),
                            signerMapper.mapToDto(memoOfDispatch.getSigner())
                    );
            return memoOfDispatchDto;
        } else {
            return new MemoOfDispatchDto();
        }
    }

//        if (deliveryOfWagon.getMemoOfDispatch() != null) {
//            MemoOfDispatchDto memoOfDispatchDto = memoOfDispatchMapper.mapToDto(deliveryOfWagon.getMemoOfDispatch());
//            return memoOfDispatchDto;
//        } else {
//            return null;
//        }
//    }

    CustomerDto mapToDtoCustomer(DeliveryOfWagon deliveryOfWagon) {
        if (deliveryOfWagon.getCustomer() != null) {
            CustomerDto customerDto = customerMapper.mapToDto(deliveryOfWagon.getCustomer());
            return customerDto;
        } else {
            return null;
        }
    }

    MemoOfDeliveryDto mapMemoOfDelivery(DeliveryOfWagon deliveryOfWagon) {
        MemoOfDelivery memoOfDelivery = deliveryOfWagon.getMemoOfDelivery();
        if (memoOfDelivery != null) {
            MemoOfDeliveryDto memoOfDeliveryDto =
                    new MemoOfDeliveryDto(  memoOfDelivery.getMemoOfDeliveryId(),
                                            memoOfDelivery.getCreated(),
                                            memoOfDelivery.getStartDate(),
                                            cargoOperationMapper.mapToDto(memoOfDelivery.getCargoOperation()),
                                            customerMapper.mapToDto(memoOfDelivery.getCustomer()),
                                            signerMapper.mapToDto(memoOfDelivery.getSigner()),
                                            memoOfDelivery.getComment(),
                                            memoOfDelivery.getAuthor().getInitials(),
                                            null
                            );
            return memoOfDeliveryDto;
        } else {
            return new MemoOfDeliveryDto();
        }
    }
}
