package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.model.ControllerStatement;
import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoOfDispatchRepository extends JpaRepository<MemoOfDispatch, Long> {
    Optional<MemoOfDispatch> findAllByCustomer(Customer customer);

    List<MemoOfDispatch> findAllByCustomerAndCargoOperationAndControllerStatement(
            Customer customer,
            CargoOperation cargoOperation,
            ControllerStatement controllerStatement);
}
