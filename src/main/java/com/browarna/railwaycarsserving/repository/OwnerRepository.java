package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByOwner(String owner);
}
