package com.browarna.railwaycarsserving.controller;

import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.SignerDto;
import com.browarna.railwaycarsserving.service.CustomerService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.status(OK)
                .body(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.status(OK)
                .body(customerService.createCustomer(customerDto));
    }

    @PutMapping
    public String updateCustomer(@RequestBody CustomerDto CustomerDto) {
        customerService.updateCustomer(CustomerDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new JSONObject().put("message", "Общая подача удалена").toString();
    }

    @DeleteMapping("/signer/{id}")
    public String deleteSigner(@PathVariable Long id) {
        customerService.deleteSigner(id);
        return new JSONObject().put("message", "Подписант удален").toString();
    }

    @PostMapping("/signer")
    public ResponseEntity<SignerDto> createSigner(@RequestBody SignerDto signerDto) {
        return ResponseEntity.status(OK)
                .body(customerService.createSigner(signerDto));
    }

    @PutMapping("/signer")
    public ResponseEntity<SignerDto> updateSigner(@RequestBody SignerDto signerDto) {
        return ResponseEntity.status(OK)
                .body(customerService.updateSigner(signerDto));
    }
}
