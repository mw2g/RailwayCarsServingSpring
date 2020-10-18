package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.BaseRate;
import com.browarna.railwaycarsserving.model.WagonGroup;
import com.browarna.railwaycarsserving.repository.BaseRateRepository;
import com.browarna.railwaycarsserving.repository.WagonGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BaseRateService {
    private final BaseRateRepository baseRateRepository;
    private final WagonGroupRepository wagonGroupRepository;

    public List<BaseRate> getAll() {
        return baseRateRepository.findAll();
    }

    public BaseRate getBaseRateById(Long typeId) {
        BaseRate baseRate = baseRateRepository.findById(typeId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by rateId"));
        return baseRate;
    }

    public BaseRate create(BaseRate baseRate) {
        BaseRate rate = new BaseRate();
        rate.setRelevanceDate(baseRate.getRelevanceDate());
        rate.setHours(baseRate.getHours());
        WagonGroup group = wagonGroupRepository.findByGroupName(baseRate.getWagonGroup().getGroupName()).get();
        rate.setWagonGroup(group);
        rate.setRate(baseRate.getRate());
        return baseRateRepository.save(rate);
    }

    public void update(BaseRate baseRate) {
        BaseRate rate = baseRateRepository.findById(baseRate.getRateId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by rateId to update"));
        rate.setRelevanceDate(baseRate.getRelevanceDate());
        rate.setHours(baseRate.getHours());
        rate.setWagonGroup(baseRate.getWagonGroup());
        rate.setRate(baseRate.getRate());
        baseRateRepository.save(rate);
    }

    public void delete(Long rateId) {
        BaseRate baseRate = baseRateRepository.findById(rateId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find base rate by rateId to delete"));
        baseRateRepository.delete(baseRate);
    }
}
