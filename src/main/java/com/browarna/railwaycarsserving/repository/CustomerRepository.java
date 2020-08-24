package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerName(String customerName);
}
