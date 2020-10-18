package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.*;
import com.browarna.railwaycarsserving.model.ControllerStatement;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ControllerStatementMapper {
    @Autowired
    private DeliveryOfWagonMapper deliveryOfWagonMapper;
    @Autowired
    private MemoOfDispatchMapper memoOfDispatchMapper;
    @Autowired
    private SignerMapper signerMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Mapping(target = "statementId", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "memoOfDispatchList", ignore = true)
    @Mapping(target = "signer", ignore = true)
//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    public abstract ControllerStatement map(ControllerStatementDto controllerStatementDto);

//    @Mapping(target = "cargoOperation", source = "cargoOperation")
    @Mapping(target = "memoOfDispatchList", expression = "java(mapToDtoMemoOfDispatchList(controllerStatement))")
    @Mapping(target = "signer", expression = "java(mapToDtoSigner(controllerStatement))")
    @Mapping(target = "customer", expression = "java(mapToDtoCustomer(controllerStatement))")
    @Mapping(target = "author", expression = "java(getName(controllerStatement))")
    public abstract ControllerStatementDto mapToDto(ControllerStatement controllerStatement);

    String getName(ControllerStatement controllerStatement) {

        return controllerStatement.getAuthor().getInitials();
    }

    List<MemoOfDispatchDto> mapToDtoMemoOfDispatchList(ControllerStatement controllerStatement) {
        if (controllerStatement.getMemoOfDispatchList() != null) {
            List<MemoOfDispatchDto> memoOfDispatchDtoList = controllerStatement.getMemoOfDispatchList().stream()
                    .map(memoOfDispatch -> memoOfDispatchMapper.mapToDto(memoOfDispatch))
                    .collect(Collectors.toList());
            return memoOfDispatchDtoList;
        } else {
            return null;
        }
    }

    SignerDto mapToDtoSigner(ControllerStatement controllerStatement) {
        if (controllerStatement.getSigner() != null) {
            SignerDto signerDto = signerMapper.mapToDto(controllerStatement.getSigner());
            return signerDto;
        } else {
            return new SignerDto();
        }
    }

    CustomerDto mapToDtoCustomer(ControllerStatement controllerStatement) {
        if (controllerStatement.getCustomer() != null) {
            CustomerDto customerDto = customerMapper.mapToDto(controllerStatement.getCustomer());
            return customerDto;
        } else {
            return null;
        }
    }
}
