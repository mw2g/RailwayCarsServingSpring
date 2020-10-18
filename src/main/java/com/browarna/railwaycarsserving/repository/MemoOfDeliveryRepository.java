package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import com.browarna.railwaycarsserving.model.Signer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoOfDeliveryRepository extends JpaRepository<MemoOfDelivery, Long> {
    Optional<MemoOfDelivery> findAllByCustomer(Customer customer);
    List<MemoOfDelivery> findAllBySigner(Signer signer);
}
