package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.model.Setting;
import com.browarna.railwaycarsserving.model.WagonGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findBySettingType(String settingType);
}
