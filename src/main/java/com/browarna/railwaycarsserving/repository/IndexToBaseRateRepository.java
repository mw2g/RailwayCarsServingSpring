package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.IndexToBaseRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface IndexToBaseRateRepository extends JpaRepository<IndexToBaseRate, Long> {
    Optional<IndexToBaseRate> findFirstByRelevanceDateLessThanEqualOrderByRelevanceDateDesc(Date date);
}
