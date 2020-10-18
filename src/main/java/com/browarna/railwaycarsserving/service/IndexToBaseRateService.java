package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.IndexToBaseRate;
import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.repository.IndexToBaseRateRepository;
import com.browarna.railwaycarsserving.repository.WagonGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class IndexToBaseRateService {
    private final IndexToBaseRateRepository indexToBaseRateRepository;

    public List<IndexToBaseRate> getAll() {
        return indexToBaseRateRepository.findAll();
    }

    public IndexToBaseRate getById(Long indexId) {
        IndexToBaseRate baseRate = indexToBaseRateRepository.findById(indexId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by indexId"));
        return baseRate;
    }

    public IndexToBaseRate create(IndexToBaseRate indexToBaseRate) {
        IndexToBaseRate index = new IndexToBaseRate();
        index.setRelevanceDate(indexToBaseRate.getRelevanceDate());
        index.setIndexToRate(indexToBaseRate.getIndexToRate());
        return indexToBaseRateRepository.save(index);
    }

    public void update(IndexToBaseRate indexToBaseRate) {
        IndexToBaseRate rate = indexToBaseRateRepository.findById(indexToBaseRate.getIndexId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by indexId to update"));
        rate.setRelevanceDate(indexToBaseRate.getRelevanceDate());
        rate.setIndexToRate(indexToBaseRate.getIndexToRate());
        indexToBaseRateRepository.save(rate);
    }

    public void delete(Long indexId) {
        IndexToBaseRate indexToBaseRate = indexToBaseRateRepository.findById(indexId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by indexId to delete"));
        indexToBaseRateRepository.delete(indexToBaseRate);
    }
}
