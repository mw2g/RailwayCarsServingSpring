package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.SignerDto;
import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.Signer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    @Autowired
    private  SignerMapper signerMapper;

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "signerList", ignore = true)
    public abstract Customer map(CustomerDto customerDto);

    @Mapping(target = "signerList", expression = "java(mapSignerListToDto(customer.getSignerList()))")
    @Mapping(target = "author", expression = "java(getName(customer))")
    public abstract CustomerDto mapToDto(Customer customer);

    String getName(Customer customer) {
        if (customer.getAuthor() != null) {
        return customer.getAuthor().getLastName();
        }
        return "";
    }

    List<SignerDto> mapSignerListToDto(List<Signer> signerList) {
        List<SignerDto> signerDtoList = new ArrayList<>();
        if (signerList == null) {
            return signerDtoList;
        }
        for (Signer signer : signerList) {
            signerDtoList.add(signerMapper.mapToDto(signer));
        }

        return signerDtoList;
    }
}
