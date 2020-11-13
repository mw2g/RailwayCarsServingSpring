package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.TimeNorm;
import com.browarna.railwaycarsserving.model.TimeNormType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TimeNormRepository extends JpaRepository<TimeNorm, Long> {

    Optional<TimeNorm> findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(String typeCode, Date date);
}
