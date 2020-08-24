package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.model.Customer;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeliveryOfWagonRepository extends JpaRepository<DeliveryOfWagon, Long> {
    Optional<DeliveryOfWagon> findAllByCustomer(Customer customer);
    List<DeliveryOfWagon> findAllByMemoOfDelivery_MemoId(Long memoId);
    List<DeliveryOfWagon> findAllByCustomerAndCargoOperationAndStartDateAndMemoOfDelivery(Customer customer,
                                                                            CargoOperation cargoOperation,
                                                                            Date startDate,
                                                                            MemoOfDelivery memoOfDelivery);
}
