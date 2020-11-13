package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.BaseRate;
import com.browarna.railwaycarsserving.model.TimeNorm;
import com.browarna.railwaycarsserving.model.WagonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BaseRateRepository extends JpaRepository<BaseRate, Long> {
    Optional<BaseRate> findFirstByWagonGroup_GroupIdAndHoursAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
            Long groupId, Long hours,  Date date);
}
