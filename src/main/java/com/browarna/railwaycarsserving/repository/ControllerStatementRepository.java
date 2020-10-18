package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.ControllerStatement;
import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.Signer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ControllerStatementRepository extends JpaRepository<ControllerStatement, Long> {
    Optional<ControllerStatement> findAllByCustomer(Customer customer);
}
