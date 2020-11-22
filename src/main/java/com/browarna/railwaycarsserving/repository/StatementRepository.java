package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    Optional<Statement> findAllByCustomer(Customer customer);
    List<Statement> findAllByCreatedBetween(Date afterDate, Date beforeDate);
}
