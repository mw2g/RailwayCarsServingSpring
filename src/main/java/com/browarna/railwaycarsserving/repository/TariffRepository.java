package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(String typeCode, Date date);

}
