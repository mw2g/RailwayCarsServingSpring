package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DeliveryOfWagonRepository extends JpaRepository<DeliveryOfWagon, Long> {
    Optional<DeliveryOfWagon> findAllByCustomer(Customer customer);
    List<DeliveryOfWagon> findAllByMemoOfDelivery_MemoOfDeliveryId(Long memoId);
    List<DeliveryOfWagon> findAllByMemoOfDispatch_MemoOfDispatchId(Long memoId);
    List<DeliveryOfWagon> findAllByCustomerAndCargoOperationAndStartDateAndMemoOfDelivery(Customer customer,
                                                                            CargoOperation cargoOperation,
                                                                            Date startDate,
                                                                            MemoOfDelivery memoOfDelivery);
    List<DeliveryOfWagon> findAllByCustomerAndCargoOperationAndEndDateAndMemoOfDispatch(Customer customer,
                                                                            CargoOperation cargoOperation,
                                                                            Date startDate,
                                                                            MemoOfDispatch memoOfDispatch);

    List<DeliveryOfWagon> findAllByCustomerAndCargoOperationAndMemoOfDispatch(Customer customer,
                                                                            CargoOperation cargoOperation,
                                                                            MemoOfDispatch memoOfDispatch);

    Optional<DeliveryOfWagon> findByWagon_WagonNumberAndStartDate(String wagonNumber, Date startDate);

    Optional<DeliveryOfWagon> findFirstByWagon_WagonNumberOrderByCreatedDesc(String wagonNumber);

}
