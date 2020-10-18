package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.repository.WagonGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class WagonGroupService {
    private final WagonGroupRepository wagonGroupRepository;

    public List<WagonGroup> getAll() {
        return wagonGroupRepository.findAll();
    }

    public WagonGroup getById(Long typeId) {
        WagonGroup wagonGroup = wagonGroupRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon group by groupId"));
        return wagonGroup;
    }

    public WagonGroup create(WagonGroup wagonGroup) {
        WagonGroup type = new WagonGroup();
        type.setGroupName(wagonGroup.getGroupName());
        return wagonGroupRepository.save(type);
    }

    public void update(WagonGroup wagonGroup) {
        WagonGroup type = wagonGroupRepository.findById(wagonGroup.getGroupId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon group by groupId to update"));
        type.setGroupName(wagonGroup.getGroupName());
        wagonGroupRepository.save(type);
    }

    public void delete(Long typeId) {
        WagonGroup wagonGroup = wagonGroupRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find wagon group by groupId to delete"));
        wagonGroupRepository.delete(wagonGroup);
    }
}
