package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.CargoOperationMapper;
import com.browarna.railwaycarsserving.mapper.CustomerMapper;
import com.browarna.railwaycarsserving.mapper.DeliveryOfWagonMapper;
import com.browarna.railwaycarsserving.mapper.UserMapper;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.model.Wagon;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class DeliveryOfWagonService {
    private final DeliveryOfWagonRepository deliveryOfWagonRepository;
    private final DeliveryOfWagonMapper deliveryOfWagonMapper;
    private final UserMapper userMapper;
    private final CargoOperationMapper cargoOperationMapper;
    private final CustomerMapper customerMapper;
    private final AuthService authService;
    private final WagonRepository wagonRepository;
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public List<DeliveryOfWagonDto> getAllDeliveryOfWagons() {

        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository.findAll();
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList;
    }

    public List<DeliveryOfWagonDto> getDeliveryByMemoId(Long memoId) {
//        MemoOfDelivery memo = memoOfDeliveryRepository.findById(memoId).get();
        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository.findAllByMemoOfDelivery_MemoId(memoId);
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList;
    }

    public DeliveryOfWagonDto getDeliveryOfWagonById(Long deliveryId) {

        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryId).get();
        DeliveryOfWagonDto deliveryOfWagonDto = deliveryOfWagonMapper.mapToDto(deliveryOfWagon);
        return deliveryOfWagonDto;
    }

    public void createDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonMapper.map(deliveryOfWagonDto);
        deliveryOfWagon.setCreated(Instant.now());
        deliveryOfWagon.setWagon(getOrCreateWagon(deliveryOfWagonDto.getWagon().getWagonNumber()));
        deliveryOfWagon.setCargoOperation(cargoOperationRepository.findById(deliveryOfWagonDto.getCargoOperation().getOperationId()).get());
        deliveryOfWagon.setAuthor(authService.getCurrentUser());
        deliveryOfWagon.setCustomer(customerRepository.findByCustomerName(deliveryOfWagonDto.getCustomer().getCustomerName()));
        if (deliveryOfWagonDto.getMemoOfDelivery() != null && deliveryOfWagonDto.getMemoOfDelivery().getMemoId() != null) {
            deliveryOfWagon.setMemoOfDelivery(memoOfDeliveryRepository.findById(deliveryOfWagonDto.getMemoOfDelivery().getMemoId()).get());
        }
        deliveryOfWagonRepository.save(deliveryOfWagon);
    }

    public void updateDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryOfWagonDto.getDeliveryId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to update"));

        deliveryOfWagon.setWagon(getOrCreateWagon(deliveryOfWagon.getWagon().getWagonNumber()));
        deliveryOfWagon.setCargoOperation(cargoOperationRepository.findById(deliveryOfWagonDto.getCargoOperation().getOperationId()).get());
        deliveryOfWagon.setCargoWeight(deliveryOfWagonDto.getCargoWeight());
        deliveryOfWagon.setLoadUnloadWork(deliveryOfWagonDto.isLoadUnloadWork());
        deliveryOfWagon.setShuntingWorks(deliveryOfWagonDto.getShuntingWorks());
        deliveryOfWagon.setStartDate(deliveryOfWagonDto.getStartDate());
        deliveryOfWagon.setEndDate(deliveryOfWagonDto.getEndDate());
        deliveryOfWagon.setCustomer(customerRepository.findByCustomerName(deliveryOfWagonDto.getCustomer().getCustomerName()));
        if (deliveryOfWagonDto.getMemoOfDelivery().getMemoId() != null) {
            deliveryOfWagon.setMemoOfDelivery(memoOfDeliveryRepository.findById(deliveryOfWagonDto.getMemoOfDelivery().getMemoId()).get());
        } else {
            deliveryOfWagon.setMemoOfDelivery(null);
        }

        deliveryOfWagonRepository.save(deliveryOfWagon);
    }

    private Wagon getOrCreateWagon(String wagonNumber) {
        if (wagonRepository.findByWagonNumber(wagonNumber).isPresent()) {
            return wagonRepository.findByWagonNumber(wagonNumber).get();
        } else {
            Wagon wagon = new Wagon();
            wagon.setWagonNumber(wagonNumber);
            wagonRepository.save(wagon);
            return wagon;
        }
    }

    public void deleteDeliveryOfWagon(Long deliveryId) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to delete"));
        deliveryOfWagonRepository.delete(deliveryOfWagon);
    }

    public List<DeliveryOfWagonDto> getSuitableDelivery(Long memoId) {
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

    public void addMemoToDelivery(Long deliveryId, Long memoId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(memoOfDeliveryRepository.findById(memoId).get());
        deliveryOfWagonRepository.save(delivery);
    }

    public void removeMemoToDelivery(Long deliveryId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(null);
        deliveryOfWagonRepository.save(delivery);
    }
}
