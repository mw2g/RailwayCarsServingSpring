package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.Setting;
import com.browarna.railwaycarsserving.repository.SettingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SettingService {
    private final SettingRepository settingRepository;

    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    public List<String> getByType(List<String> settingType) {
        List<String> settings = new ArrayList<>();
        for (String type : settingType) {
            settings.add(settingRepository.findBySettingType(type)
                    .orElseThrow(() -> new RailwayCarsServingException("Can`t find setting by settingId")).getSettingValue());
        }
        return settings;
    }

    public Setting create(Setting set) {
        Setting setting = new Setting();
        setting.setSettingType(set.getSettingType());
        setting.setSettingValue(set.getSettingValue());
        return settingRepository.save(setting);
    }

    public void update(Setting set) {
        Setting setting = settingRepository.findById(set.getSettingId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find setting by settingId"));
        setting.setSettingType(set.getSettingType());
        setting.setSettingValue(set.getSettingValue());
        settingRepository.save(setting);
    }

    public void delete(Long settingId) {
        Setting setting = settingRepository.findById(settingId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find setting by settingId to delete"));
        settingRepository.delete(setting);
    }
}
