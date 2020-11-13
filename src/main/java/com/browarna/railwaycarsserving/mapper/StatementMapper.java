package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.dto.StatementDto;
import com.browarna.railwaycarsserving.model.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StatementMapper {
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
    @Mapping(target = "memoOfDispatchList", ignore = true)
    @Mapping(target = "signer", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "cargoOperation", ignore = true)
    public abstract Statement map(StatementDto statementDto);

    @Mapping(target = "memoOfDispatchList", expression = "java(mapToDtoMemoOfDispatchList(statement))")
    @Mapping(target = "author", expression = "java(statement.getAuthor().getInitials())")
    @Mapping(target = "cargoOperation", expression = "java(statement.getCargoOperation().getOperationName())")
    @Mapping(target = "customer", expression = "java(statement.getCustomer().getCustomerName())")
    @Mapping(target = "signer", expression = "java(statement.getSigner().getInitials())")
    public abstract StatementDto mapToDto(Statement statement);

    List<MemoOfDispatchDto> mapToDtoMemoOfDispatchList(Statement statement) {
        if (statement.getMemoOfDispatchList() != null) {
            List<MemoOfDispatchDto> memoOfDispatchDtoList = statement.getMemoOfDispatchList().stream()
                    .map(memoOfDispatch -> memoOfDispatchMapper.mapToDto(memoOfDispatch))
                    .collect(Collectors.toList());
            return memoOfDispatchDtoList;
        } else {
            return null;
        }
    }

//    SignerDto mapToDtoSigner(Statement statement) {
//        if (statement.getSigner() != null) {
//            SignerDto signerDto = signerMapper.mapToDto(statement.getSigner());
//            return signerDto;
//        } else {
//            return new SignerDto();
//        }
//    }

//    CustomerDto mapToDtoCustomer(Statement statement) {
//        if (statement.getCustomer() != null) {
//            CustomerDto customerDto = customerMapper.mapToDto(statement.getCustomer());
//            return customerDto;
//        } else {
//            return null;
//        }
//    }
}
