package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
}
