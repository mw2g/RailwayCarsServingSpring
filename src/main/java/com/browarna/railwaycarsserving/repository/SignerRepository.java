package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Signer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignerRepository extends JpaRepository<Signer, Long> {
    Signer findByInitials(String initials);
}
