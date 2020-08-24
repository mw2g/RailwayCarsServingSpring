package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoOfDispatchRepository extends JpaRepository<MemoOfDispatch, Long> {
    Optional<MemoOfDispatch> findAllByCustomer(Customer customer);
}
