package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.model.TimeNorm;
import com.browarna.railwaycarsserving.model.TimeNormType;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.repository.TimeNormRepository;
import com.browarna.railwaycarsserving.repository.TimeNormTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TimeNormService {
    private final TimeNormRepository timeNormRepository;
    private final TimeNormTypeRepository timeNormTypeRepository;

    public List<TimeNorm> getAllTimeNorm() {
        return timeNormRepository.findAll();
    }

    public TimeNorm getTimeNormById(Long normId) {
        TimeNorm timeNorm = timeNormRepository.findById(normId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm by normId"));
        return timeNorm;
    }

    public TimeNorm createTimeNorm(TimeNorm timeNorm) {
        TimeNorm newTimeNorm = new TimeNorm();
        newTimeNorm.setNorm(timeNorm.getNorm());
        newTimeNorm.setRelevanceDate(timeNorm.getRelevanceDate());
        TimeNormType timeNormType = timeNormTypeRepository.findByTypeName(timeNorm.getNormType().getTypeName()).get();
        newTimeNorm.setNormType(timeNormType);
        return timeNormRepository.save(newTimeNorm);
    }

    public void updateTimeNorm(TimeNorm timeNorm) {
        TimeNorm timeNormById = timeNormRepository.findById(timeNorm.getNormId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm by normId to update"));
        timeNormById.setNorm(timeNorm.getNorm());
        timeNormById.setRelevanceDate(timeNorm.getRelevanceDate());
        TimeNormType timeNormType = timeNormTypeRepository.findByTypeName(timeNorm.getNormType().getTypeName()).get();
        timeNormById.setNormType(timeNormType);
        timeNormRepository.save(timeNormById);
    }

    public void deleteTimeNorm(Long timeNormId) {
        TimeNorm timeNorm = timeNormRepository.findById(timeNormId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find timeNorm by normId to delete"));
        timeNormRepository.delete(timeNorm);
    }
}
