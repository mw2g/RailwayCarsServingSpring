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
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto CustomerDto) {
        return ResponseEntity.status(OK)
                .body(customerService.createCustomer(CustomerDto));
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

    @PostMapping("/signer")
    public String createSigner(@RequestBody SignerDto signerDto) {
        customerService.createSigner(signerDto);
        return new JSONObject().put("message", "Подписант добавлен").toString();
    }

    @PutMapping("/signer")
    public String updateSigner(@RequestBody SignerDto signerDto) {
        customerService.updateSigner(signerDto);
        return new JSONObject().put("message", "Изменения сохранены").toString();
    }
}
