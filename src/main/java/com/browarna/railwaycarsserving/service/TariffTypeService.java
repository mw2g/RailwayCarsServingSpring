package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.TariffType;
import com.browarna.railwaycarsserving.repository.TariffTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TariffTypeService {
    private final TariffTypeRepository tariffTypeRepository;

    public List<TariffType> getAllTariffTypes() {
        return tariffTypeRepository.findAll();
    }

    public TariffType getTariffTypeById(Long typeId) {
        TariffType tariffType = tariffTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff type by typeId"));
        return tariffType;
    }

    public TariffType createTariffType(TariffType tariffType) {
        TariffType type = new TariffType();
        type.setType(tariffType.getType());
        return tariffTypeRepository.save(type);
    }

    public void updateTariffType(TariffType tariffType) {
        TariffType type = tariffTypeRepository.findById(tariffType.getTypeId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff type by typeId to update"));
        type.setType(tariffType.getType());
        tariffTypeRepository.save(type);
    }

    public void deleteTariffType(Long typeId) {
        TariffType tariffType = tariffTypeRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff type by typeId to delete"));
        tariffTypeRepository.delete(tariffType);
    }
}
