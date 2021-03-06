package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.model.TimeNormType;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.repository.TimeNormTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TimeNormTypeService {
    private final TimeNormTypeRepository timeNormTypeRepository;

    public List<TimeNormType> getAllTimeNormTypes() {
        return timeNormTypeRepository.findAll();
    }

    public TimeNormType getTimeNormTypeById(Long typeId) {
        TimeNormType timeNormType = timeNormTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm type by typeId"));
        return timeNormType;
    }

    public TimeNormType createTimeNormType(TimeNormType timeNormType) {
        TimeNormType type = new TimeNormType();
        type.setTypeName(timeNormType.getTypeName());
        type.setTypeCode(timeNormType.getTypeCode());
        return timeNormTypeRepository.save(type);
    }

    public void updateTimeNormType(TimeNormType timeNormType) {
        TimeNormType type = timeNormTypeRepository.findById(timeNormType.getTypeId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm type by typeId to update"));
        type.setTypeName(timeNormType.getTypeName());
        type.setTypeCode(timeNormType.getTypeCode());
        timeNormTypeRepository.save(type);
    }

    public void deleteTimeNormType(Long typeId) {
        TimeNormType timeNormType = timeNormTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm type by typeId to delete"));
        timeNormTypeRepository.delete(timeNormType);
    }
}
