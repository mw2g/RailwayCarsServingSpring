package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.Penalty;
import com.browarna.railwaycarsserving.model.WagonType;
import com.browarna.railwaycarsserving.repository.PenaltyRepository;
import com.browarna.railwaycarsserving.repository.WagonTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class PenaltyService {
    private final PenaltyRepository penaltyRepository;
    private final WagonTypeRepository wagonTypeRepository;

    public List<Penalty> getAll() {
        return penaltyRepository.findAll();
    }

    public Penalty getById(Long penaltyId) {
        Penalty tariff = penaltyRepository.findById(penaltyId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find penalty by penaltyId"));
        return tariff;
    }

    public Penalty create(Penalty penalty) {
        Penalty newPenalty = new Penalty();
        newPenalty.setRelevanceDate(penalty.getRelevanceDate());
        newPenalty.setPenalty(penalty.getPenalty());
        WagonType wagonType = wagonTypeRepository.findById(penalty.getWagonType().getTypeId()).get();
        newPenalty.setWagonType(wagonType);
        return penaltyRepository.save(newPenalty);
    }

    public void update(Penalty penalty) {
        Penalty penaltyById = penaltyRepository.findById(penalty.getPenaltyId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find penalty by penaltyId to update"));
        penaltyById.setRelevanceDate(penalty.getRelevanceDate());
        penaltyById.setPenalty(penalty.getPenalty());
        WagonType wagonType = wagonTypeRepository.findByTypeName(penalty.getWagonType().getTypeName()).get();
        penaltyById.setWagonType(wagonType);
        penaltyRepository.save(penaltyById);
    }

    public void delete(Long penaltyId) {
        Penalty tariff = penaltyRepository.findById(penaltyId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find penalty by penaltyId to delete"));
        penaltyRepository.delete(tariff);
    }
}
