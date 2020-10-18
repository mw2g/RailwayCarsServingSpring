package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.BaseRate;
import com.browarna.railwaycarsserving.model.WagonType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRateRepository extends JpaRepository<BaseRate, Long> {
}
