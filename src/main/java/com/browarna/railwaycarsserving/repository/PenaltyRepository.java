package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    Optional<Penalty> findFirstByWagonType_TypeIdAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
            Long typeId, Date date);
}
