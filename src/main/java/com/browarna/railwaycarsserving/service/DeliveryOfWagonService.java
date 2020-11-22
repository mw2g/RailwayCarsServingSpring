package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.BaseRateAndPenaltyResponse;
import com.browarna.railwaycarsserving.dto.StatementWithRateRequest;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.DeliveryOfWagonMapper;
import com.browarna.railwaycarsserving.model.*;
import com.browarna.railwaycarsserving.repository.*;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.model.*;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class DeliveryOfWagonService {
    private final DeliveryOfWagonRepository deliveryOfWagonRepository;
    private final DeliveryOfWagonMapper deliveryOfWagonMapper;
    private final AuthService authService;
    private final WagonRepository wagonRepository;
    private final WagonTypeRepository wagonTypeRepository;
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final CargoOperationRepository cargoOperationRepository;
    private final OwnerRepository ownerRepository;
    private final CargoTypeRepository cargoTypeRepository;
    private final CustomerRepository customerRepository;
    private final BaseRateRepository baseRateRepository;
    private final PenaltyRepository penaltyRepository;

    public List<DeliveryOfWagonDto> getAllDeliveryOfWagons(Date afterDate, Date beforeDate) {

        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository.findAllByStartDateBetween(afterDate, beforeDate);
        return deliveryOfWagonList.stream().map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon))
                .collect(Collectors.toList());
    }

    public DeliveryOfWagonDto getDeliveryOfWagonById(Long deliveryId) {
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.findById(deliveryId).get());
    }

    public DeliveryOfWagonDto createDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        Optional<DeliveryOfWagon> deliveryFromBase = deliveryOfWagonRepository.findByWagon_WagonNumberAndStartDate(
                deliveryOfWagonDto.getWagon(),
                deliveryOfWagonDto.getStartDate());
        if (deliveryFromBase.isPresent()) {
            return new DeliveryOfWagonDto();
        }
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonMapper.map(deliveryOfWagonDto);
        deliveryOfWagon.setCreated(new Date());
        deliveryOfWagon.setAuthor(authService.getCurrentUser());
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.save(deliveryOfWagon));
    }

    public DeliveryOfWagonDto updateDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        Optional<DeliveryOfWagon> deliveryFromBase = deliveryOfWagonRepository.findByWagon_WagonNumberAndStartDate(
                deliveryOfWagonDto.getWagon(),
                deliveryOfWagonDto.getStartDate());
        if (deliveryFromBase.isPresent() && deliveryFromBase.filter(deliveryOfWagon ->
                deliveryOfWagon.getDeliveryId() != deliveryOfWagonDto.getDeliveryId()).isPresent()) {
            return null;
        }

        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryOfWagonDto.getDeliveryId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to update"));
        deliveryOfWagon.setWagon(getOrCreateWagon(deliveryOfWagonDto.getWagon()));
        deliveryOfWagon.setCargoWeight(deliveryOfWagonDto.getCargoWeight());
        deliveryOfWagon.setLoadUnloadWork(deliveryOfWagonDto.isLoadUnloadWork());
        deliveryOfWagon.setShuntingWorks(deliveryOfWagonDto.getShuntingWorks());
        deliveryOfWagon.setStartDate(deliveryOfWagonDto.getStartDate());
        deliveryOfWagon.setEndDate(deliveryOfWagonDto.getEndDate());
        deliveryOfWagon.setCargoOperation(cargoOperationRepository.findByOperationName(deliveryOfWagonDto.getCargoOperation()));
        deliveryOfWagon.setWagonType(wagonTypeRepository.findByTypeName(deliveryOfWagonDto.getWagonType()).get());
        deliveryOfWagon.setOwner(ownerRepository.findByOwnerName(deliveryOfWagonDto.getOwner()));
        deliveryOfWagon.setCustomer(customerRepository.findByCustomerName(deliveryOfWagonDto.getCustomer()));
        deliveryOfWagon.setCargoType(cargoTypeRepository.findByTypeName(deliveryOfWagonDto.getCargoType()));
        deliveryOfWagon.setMemoOfDelivery(deliveryOfWagonDto.getMemoOfDelivery() != null ?
                memoOfDeliveryRepository.findById(deliveryOfWagonDto.getMemoOfDelivery()).get() : null);
        deliveryOfWagon.setMemoOfDispatch(deliveryOfWagonDto.getMemoOfDispatch() != null ?
                memoOfDispatchRepository.findById(deliveryOfWagonDto.getMemoOfDispatch()).get() : null);
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.save(deliveryOfWagon));
    }

    public Wagon getOrCreateWagon(String wagonNumber) {
        Wagon wagon;
        Optional<Wagon> byWagonNumber = wagonRepository.findByWagonNumber(wagonNumber);
        if (byWagonNumber.isPresent()) {
            wagon = byWagonNumber.get();
        } else {
            wagon = new Wagon();
            wagon.setWagonNumber(wagonNumber);
        }
        return wagonRepository.save(wagon);
    }

    public void deleteDeliveryOfWagon(Long deliveryId) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to delete"));
        deliveryOfWagonRepository.delete(deliveryOfWagon);
    }

    public DeliveryOfWagonDto getDeliveryForAutocomplete(String wagonNumber) {
        Optional<DeliveryOfWagon> deliveryOfWagonOptional = deliveryOfWagonRepository
                .findFirstByWagon_WagonNumberOrderByCreatedDesc(wagonNumber);
        return deliveryOfWagonOptional.isPresent() ?
                deliveryOfWagonMapper.mapToDto(deliveryOfWagonOptional.get()) : new DeliveryOfWagonDto();
    }

    public List<DeliveryOfWagonDto> getSuitableDeliveryForMemoOfDelivery(Long memoId) {
        MemoOfDelivery memo = memoOfDeliveryRepository.findById(memoId).get();
        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository
                .findAllByCustomerAndCargoOperationAndStartDateAndMemoOfDelivery(memo.getCustomer(),
                        memo.getCargoOperation(),
                        memo.getStartDate(),
                        null);
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList;
    }

    public List<DeliveryOfWagonDto> getSuitableDeliveryForMemoOfDispatch(Long memoId) {
        MemoOfDispatch memo = memoOfDispatchRepository.findById(memoId).get();
        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository
                .findAllByCustomerAndCargoOperationAndMemoOfDispatch(
                        memo.getCustomer(),
                        memo.getCargoOperation(),
                        null);
        deliveryOfWagonList = deliveryOfWagonList.stream().filter(delivery -> delivery.getMemoOfDelivery() != null &&
                (delivery.getEndDate() == null || delivery.getEndDate().equals(memo.getEndDate())))
                .collect(Collectors.toList());
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList;
    }

    public boolean addMemoOfDeliveryToDelivery(Long deliveryId, Long memoId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(memoOfDeliveryRepository.findById(memoId).get());
        return deliveryOfWagonRepository.save(delivery) != null;

    }

    public boolean addMemoOfDispatchToDelivery(Long deliveryId, Long memoId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoId).get();
        delivery.setMemoOfDispatch(memoOfDispatch);
        delivery.setEndDate(memoOfDispatch.getEndDate());
        return deliveryOfWagonRepository.save(delivery) != null;
    }

    public boolean removeMemoOfDeliveryFromDelivery(Long deliveryId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(null);
        return deliveryOfWagonRepository.save(delivery) != null;
    }

    public void removeMemoOfDispatchFromDelivery(Long deliveryId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDispatch(null);
        deliveryOfWagonRepository.save(delivery);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public BaseRateAndPenaltyResponse getBaseRateAndPenaltyById(StatementWithRateRequest request) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(request.getDeliveryId()).get();
        BaseRate baseRate = new BaseRate();
        if (request.getPayTime() > 0) {
            baseRate = baseRateRepository
                    .findFirstByWagonGroup_GroupIdAndHoursAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                    delivery.getWagonType().getWagonGroup().getGroupId(), request.getPayTime(), request.getDate()).get();
        }
        Penalty penalty = penaltyRepository
                .findFirstByWagonType_TypeIdAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        delivery.getWagonType().getTypeId(), request.getDate()).get();

        return new BaseRateAndPenaltyResponse(baseRate.getRate(), penalty.getPenalty());
    }
}
