package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.User;
import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.model.WagonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WagonGroupRepository extends JpaRepository<WagonGroup, Long> {
    Optional<WagonGroup> findByGroupName(String groupName);
}
