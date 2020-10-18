package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.CargoOperationMapper;
import com.browarna.railwaycarsserving.mapper.CustomerMapper;
import com.browarna.railwaycarsserving.mapper.DeliveryOfWagonMapper;
import com.browarna.railwaycarsserving.mapper.UserMapper;
import com.browarna.railwaycarsserving.model.*;
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
    private final WagonTypeRepository wagonTypeRepository;
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final CargoOperationRepository cargoOperationRepository;
    private final OwnerRepository ownerRepository;
    private final CargoTypeRepository cargoTypeRepository;
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
        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository.findAllByMemoOfDelivery_MemoOfDeliveryId(memoId);
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList;
    }

    public DeliveryOfWagonDto getDeliveryOfWagonById(Long deliveryId) {

        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryId).get();
        DeliveryOfWagonDto deliveryOfWagonDto = deliveryOfWagonMapper.mapToDto(deliveryOfWagon);
        return deliveryOfWagonDto;
    }

    public DeliveryOfWagonDto createDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        if (deliveryOfWagonRepository.findByWagon_WagonNumberAndStartDate(
                deliveryOfWagonDto.getWagon().getWagonNumber(),
                deliveryOfWagonDto.getStartDate()).isPresent()) {
            return new DeliveryOfWagonDto();
        }
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonMapper.map(deliveryOfWagonDto);
        deliveryOfWagon.setCreated(Instant.now());
        deliveryOfWagon.setWagon(getOrCreateWagon(deliveryOfWagonDto));
        deliveryOfWagon.setWagonType(wagonTypeRepository
                .findByTypeName(deliveryOfWagonDto.getWagonType().getTypeName()).get());
        deliveryOfWagon.setOwner(ownerRepository
                .findByOwner(deliveryOfWagonDto.getOwner().getOwner()));
        deliveryOfWagon.setCargoOperation(cargoOperationRepository
                .findById(deliveryOfWagonDto.getCargoOperation().getOperationId()).get());
        deliveryOfWagon.setAuthor(authService.getCurrentUser());
        deliveryOfWagon.setCustomer(customerRepository
                .findByCustomerName(deliveryOfWagonDto.getCustomer().getCustomerName()));
        deliveryOfWagon.setCargoType(cargoTypeRepository
                .findByTypeName(deliveryOfWagonDto.getCargoType().getTypeName()));
        if (deliveryOfWagonDto.getMemoOfDelivery() != null && deliveryOfWagonDto.getMemoOfDelivery().getMemoOfDeliveryId() != null) {
            deliveryOfWagon.setMemoOfDelivery(memoOfDeliveryRepository.findById(deliveryOfWagonDto.getMemoOfDelivery().getMemoOfDeliveryId()).get());
        }
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.save(deliveryOfWagon));
    }

    public DeliveryOfWagonDto updateDeliveryOfWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryOfWagonDto.getDeliveryId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to update"));

        deliveryOfWagon.setWagon(getOrCreateWagon(deliveryOfWagonDto));
        deliveryOfWagon.setCargoOperation(cargoOperationRepository
                .findById(deliveryOfWagonDto.getCargoOperation().getOperationId()).get());
        deliveryOfWagon.setWagonType(wagonTypeRepository
                .findByTypeName(deliveryOfWagonDto.getWagonType().getTypeName()).get());
        deliveryOfWagon.setOwner(ownerRepository
                .findByOwner(deliveryOfWagonDto.getOwner().getOwner()));
        deliveryOfWagon.setCargoWeight(deliveryOfWagonDto.getCargoWeight());
        deliveryOfWagon.setLoadUnloadWork(deliveryOfWagonDto.isLoadUnloadWork());
        deliveryOfWagon.setShuntingWorks(deliveryOfWagonDto.getShuntingWorks());
        deliveryOfWagon.setStartDate(deliveryOfWagonDto.getStartDate());
        deliveryOfWagon.setEndDate(deliveryOfWagonDto.getEndDate());
        deliveryOfWagon.setCustomer(customerRepository
                .findByCustomerName(deliveryOfWagonDto.getCustomer().getCustomerName()));
        deliveryOfWagon.setCargoType(cargoTypeRepository
                .findByTypeName(deliveryOfWagonDto.getCargoType().getTypeName()));
        if (deliveryOfWagonDto.getMemoOfDelivery() != null
                && deliveryOfWagonDto.getMemoOfDelivery().getMemoOfDeliveryId() != null) {
            deliveryOfWagon.setMemoOfDelivery(memoOfDeliveryRepository
                    .findById(deliveryOfWagonDto.getMemoOfDelivery().getMemoOfDeliveryId()).get());
        } else {
            deliveryOfWagon.setMemoOfDelivery(null);
        }

        if (deliveryOfWagonDto.getMemoOfDispatch() != null
                && deliveryOfWagonDto.getMemoOfDispatch().getMemoOfDispatchId() != null) {
            deliveryOfWagon.setMemoOfDispatch(memoOfDispatchRepository
                    .findById(deliveryOfWagonDto.getMemoOfDispatch().getMemoOfDispatchId()).get());
        } else {
            deliveryOfWagon.setMemoOfDispatch(null);
        }
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.save(deliveryOfWagon));
    }

    private Wagon getOrCreateWagon(DeliveryOfWagonDto deliveryOfWagonDto) {
        Wagon wagon;
        Optional<Wagon> byWagonNumber = wagonRepository.findByWagonNumber(deliveryOfWagonDto.getWagon().getWagonNumber());
        if (byWagonNumber.isPresent()) {
            wagon = byWagonNumber.get();
        } else {
            wagon = new Wagon();
            wagon.setWagonNumber(deliveryOfWagonDto.getWagon().getWagonNumber());
        }
        wagon.setWagonType(wagonTypeRepository.findByTypeName(deliveryOfWagonDto.getWagonType().getTypeName()).get());
        wagon.setOwner(ownerRepository.findByOwner(deliveryOfWagonDto.getOwner().getOwner()));
        wagonRepository.save(wagon);
        return wagon;
    }

    public void deleteDeliveryOfWagon(Long deliveryId) {
        DeliveryOfWagon deliveryOfWagon = deliveryOfWagonRepository.findById(deliveryId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find DeliveryOfWagon by id to delete"));
        deliveryOfWagonRepository.delete(deliveryOfWagon);
    }

    public DeliveryOfWagonDto getDeliveryForAutocomplete(String wagonNumber) {
        List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonRepository
                .findAllByWagon_WagonNumber(wagonNumber);
        if (deliveryOfWagonList.isEmpty()) {
            return new DeliveryOfWagonDto();
        }
        List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon)).collect(Collectors.toList());
        return deliveryOfWagonDtoList.get(deliveryOfWagonDtoList.size() - 1);
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

    public DeliveryOfWagonDto addMemoOfDeliveryToDelivery(Long deliveryId, Long memoId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(memoOfDeliveryRepository.findById(memoId).get());
        return deliveryOfWagonMapper.mapToDto(deliveryOfWagonRepository.save(delivery));

    }

    public void addMemoOfDispatchToDelivery(Long deliveryId, Long memoId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoId).get();
        delivery.setMemoOfDispatch(memoOfDispatch);
        delivery.setEndDate(memoOfDispatch.getEndDate());
        deliveryOfWagonRepository.save(delivery);
    }

    public void removeMemoOfDeliveryFromDelivery(Long deliveryId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDelivery(null);
        deliveryOfWagonRepository.save(delivery);
    }

    public void removeMemoOfDispatchFromDelivery(Long deliveryId) {
        DeliveryOfWagon delivery = deliveryOfWagonRepository.findById(deliveryId).get();
        delivery.setMemoOfDispatch(null);
        deliveryOfWagonRepository.save(delivery);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }
}
