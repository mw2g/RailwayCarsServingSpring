package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.model.WagonType;
import com.browarna.railwaycarsserving.repository.WagonGroupRepository;
import com.browarna.railwaycarsserving.repository.WagonTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class WagonTypeService {
    private final WagonTypeRepository wagonTypeRepository;
    private final WagonGroupRepository wagonGroupRepository;

    public List<WagonType> getAll() {
        return wagonTypeRepository.findAll();
    }

    public WagonType getById(Long typeId) {
        WagonType wagonType = wagonTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon type by typeId"));
        return wagonType;
    }

    public WagonType create(WagonType wagonType) {
        WagonType type = new WagonType();
        type.setTypeName(wagonType.getTypeName());
        WagonGroup group = wagonGroupRepository.findByGroupName(wagonType.getWagonGroup().getGroupName()).get();
        type.setWagonGroup(group);
        return wagonTypeRepository.save(type);
    }

    public void update(WagonType wagonType) {
        WagonType type = wagonTypeRepository.findById(wagonType.getTypeId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon type by typeId to update"));
        type.setTypeName(wagonType.getTypeName());
        type.setWagonGroup(wagonGroupRepository.findByGroupName(wagonType.getWagonGroup().getGroupName()).get());
        wagonTypeRepository.save(type);
    }

    public void delete(Long typeId) {
        WagonType wagonType = wagonTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon type by typeId to delete"));
        wagonTypeRepository.delete(wagonType);
    }
}
