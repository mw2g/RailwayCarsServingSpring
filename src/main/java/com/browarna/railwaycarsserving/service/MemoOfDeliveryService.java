package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.dto.MemoOfDeliveryDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.MemoOfDeliveryMapper;
import com.browarna.railwaycarsserving.model.CargoOperation;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDelivery;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class MemoOfDeliveryService {
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final MemoOfDeliveryMapper memoOfDeliveryMapper;
    private final AuthService authService;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryOfWagonRepository deliveryOfWagonRepository;
    private final SignerRepository signerRepository;

    public List<MemoOfDeliveryDto> getAllMemoOfDeliverys() {

        List<MemoOfDelivery> memoOfDeliveryList = memoOfDeliveryRepository.findAll();
        List<MemoOfDeliveryDto> memoOfDeliveryDtoList = memoOfDeliveryList.stream()
                .map(memoOfDelivery -> memoOfDeliveryMapper.mapToDto(memoOfDelivery)).collect(Collectors.toList());
        return memoOfDeliveryDtoList;
    }

    public MemoOfDeliveryDto getMemoOfDeliveryById(Long memoOfDeliveryId) {

        MemoOfDelivery memoOfDelivery = memoOfDeliveryRepository.findById(memoOfDeliveryId).get();
        MemoOfDeliveryDto memoOfDeliveryDto = memoOfDeliveryMapper.mapToDto(memoOfDelivery);
        return memoOfDeliveryDto;
    }

    public MemoOfDeliveryDto createMemoOfDelivery(MemoOfDeliveryDto memoOfDeliveryDto) {
        MemoOfDelivery memoOfDelivery = memoOfDeliveryMapper.map(memoOfDeliveryDto);
        memoOfDelivery.setCreated(Instant.now());
        memoOfDelivery.setAuthor(authService.getCurrentUser());
        memoOfDelivery.setCargoOperation(cargoOperationRepository.findByOperation(memoOfDeliveryDto.getCargoOperation().getOperation()));
        memoOfDelivery.setCustomer(customerRepository.findByCustomerName(memoOfDeliveryDto.getCustomer().getCustomerName()));
        memoOfDelivery.setComment(memoOfDeliveryDto.getComment());
        return memoOfDeliveryMapper.mapToDto(memoOfDeliveryRepository.save(memoOfDelivery));
    }

    public MemoOfDeliveryDto updateMemoOfDelivery(MemoOfDeliveryDto memoOfDeliveryDto) {
        MemoOfDelivery memoOfDelivery = memoOfDeliveryRepository.findById(memoOfDeliveryDto.getMemoOfDeliveryId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find memoOfDelivery by id to update"));
        CargoOperation cargoOperation = cargoOperationRepository
                .findByOperation(memoOfDeliveryDto.getCargoOperation().getOperation());
        memoOfDelivery.setCargoOperation(cargoOperation);
        memoOfDelivery.setCustomer(customerRepository
                .findByCustomerName(memoOfDeliveryDto.getCustomer().getCustomerName()));
        memoOfDelivery.setStartDate(memoOfDeliveryDto.getStartDate());
        memoOfDelivery.setComment(memoOfDeliveryDto.getComment());
        memoOfDelivery.setSigner(signerRepository.findByInitials(memoOfDeliveryDto.getSigner().getInitials()));

        for (DeliveryOfWagonDto delivery : memoOfDeliveryDto.getDeliveryOfWagonList()) {
            DeliveryOfWagon deliveryOfWagonById = deliveryOfWagonRepository.findById(delivery.getDeliveryId()).get();
            deliveryOfWagonById.setStartDate(memoOfDelivery.getStartDate());
            deliveryOfWagonById.setCustomer(memoOfDelivery.getCustomer());
            deliveryOfWagonById.setCargoOperation(memoOfDelivery.getCargoOperation());
            if (cargoOperation.getOperation() != "ВЫГРУЗКА") {
                deliveryOfWagonById.setLoadUnloadWork(false);
            }
            deliveryOfWagonRepository.save(deliveryOfWagonById);
        }

        return memoOfDeliveryMapper.mapToDto(memoOfDeliveryRepository.save(memoOfDelivery));
    }

    public void deleteMemoOfDelivery(Long memoOfDeliveryId) {
        MemoOfDelivery memoOfDelivery = memoOfDeliveryRepository.findById(memoOfDeliveryId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find memoOfDelivery by id to delete"));
        List<DeliveryOfWagon> deliveryOfWagonList = memoOfDelivery.getDeliveryOfWagonList();
        for (DeliveryOfWagon delivery : deliveryOfWagonList) {
            delivery.setMemoOfDelivery(null);
            deliveryOfWagonRepository.save(delivery);
        }
        memoOfDeliveryRepository.delete(memoOfDelivery);
        for (DeliveryOfWagon delivery : memoOfDelivery.getDeliveryOfWagonList()) {
            deliveryOfWagonRepository.delete(delivery);
        }
    }
}
