package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.SignerDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.CargoOperationMapper;
import com.browarna.railwaycarsserving.mapper.CustomerMapper;
import com.browarna.railwaycarsserving.mapper.SignerMapper;
import com.browarna.railwaycarsserving.mapper.UserMapper;
import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.Signer;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CustomerService {
    private final AuthService authService;
    private final CustomerRepository customerRepository;
    private final SignerRepository signerRepository;
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final CustomerMapper customerMapper;
    private final SignerMapper signerMapper;

    public List<CustomerDto> getAllCustomers() {

        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = customerList.stream().map(customer -> customerMapper.mapToDto(customer)).collect(Collectors.toList());
        return customerDtoList;
    }

    public CustomerDto getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        CustomerDto customerDto = customerMapper.mapToDto(customer);
        return customerDto;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.map(customerDto);
        customer.setCreated(Instant.now());
        customer.setAuthor(authService.getCurrentUser());
        return customerMapper.mapToDto(customerRepository.save(customer));
    }

    public void updateCustomer(CustomerDto customerDto) {
        Customer customer = customerRepository.findById(customerDto.getCustomerId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find Customer by id to update"));
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setCustomerFullName(customerDto.getCustomerFullName());
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long deliveryId) {
        Customer customer = customerRepository.findById(deliveryId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find Customer by id to delete"));
        customerRepository.delete(customer);
    }

    public SignerDto createSigner(SignerDto signerDto) {
        Signer signer = signerMapper.map(signerDto);
        signer.setInitials(getInitials(signerDto));
        return signerMapper.mapToDto(signerRepository.save(signer));
    }

    public SignerDto updateSigner(SignerDto signerDto) {
        Signer signer = signerRepository.findById(signerDto.getSignerId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find Signer by id to update"));
        signer.setLastName(signerDto.getLastName());
        signer.setFirstName(signerDto.getFirstName());
        signer.setMiddleName(signerDto.getMiddleName());
        signer.setInitials(getInitials(signerDto));
        return signerMapper.mapToDto(signerRepository.save(signer));
    }

    private String getInitials(SignerDto signerDto) {
        return signerDto.getLastName() + " "
                + signerDto.getFirstName().substring(0, 1) + "."
                + signerDto.getMiddleName().substring(0, 1) + ".";
    }

    public void deleteSigner(Long signerId) {
        Signer signer = signerRepository.findById(signerId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find Signer by id to delete"));
        List<MemoOfDelivery> memoOfDeliveryList = memoOfDeliveryRepository.findAllBySigner(signer);
        for (MemoOfDelivery memo : memoOfDeliveryList) {
            memo.setSigner(null);
            memoOfDeliveryRepository.save(memo);
        }
        signerRepository.delete(signer);
    }
}
