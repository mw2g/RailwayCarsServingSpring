package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.Tariff;
import com.browarna.railwaycarsserving.repository.TariffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TariffService {
    private final TariffRepository tariffRepository;

    public List<Tariff> getAllTariff() {
        return tariffRepository.findAll();
    }

    public Tariff getTariffById(Long tariffId) {
        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff by tariffId"));
        return tariff;
    }

    public Tariff createTariff(Tariff tariff) {
        Tariff newTariff = new Tariff();
        newTariff.setTariff(tariff.getTariff());
        return tariffRepository.save(newTariff);
    }

    public void updateTariff(Tariff tariff) {
        Tariff tariffById = tariffRepository.findById(tariff.getTariffId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff by tariffId to update"));
        tariffById.setTariff(tariff.getTariff());
        tariffRepository.save(tariffById);
    }

    public void deleteTariff(Long tariffId) {
        Tariff tariff = tariffRepository.findById(tariffId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find tariff by tariffId to delete"));
        tariffRepository.delete(tariff);
    }
}
