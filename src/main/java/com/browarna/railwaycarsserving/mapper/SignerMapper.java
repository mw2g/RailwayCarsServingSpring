package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.repository.CustomerRepository;
import com.browarna.railwaycarsserving.dto.SignerDto;
import com.browarna.railwaycarsserving.model.Signer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SignerMapper {
    @Autowired
    private CustomerRepository customerRepository;

    @Mapping(target = "initials", ignore = true)
    @Mapping(target = "customer", expression = "java(customerMap(signerDto.getCustomerId()))")
//    @Mapping(target = "memoOfDeliveryList", ignore = true)
    public abstract Signer map(SignerDto signerDto);

    @Mapping(target = "customerId", expression = "java(signer.getCustomer().getCustomerId())")
    public abstract SignerDto mapToDto(Signer signer);

    public Customer customerMap(Long customerId) {
        if (customerId != null) {
            return customerRepository.findById(customerId).get();
        }
        return null;
    }
}
