package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.MemoOfDispatchMapper;
import com.browarna.railwaycarsserving.model.ControllerStatement;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
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
public class MemoOfDispatchService {
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final ControllerStatementRepository controllerStatementRepository;
    private final MemoOfDispatchMapper memoOfDispatchMapper;
    private final AuthService authService;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryOfWagonRepository deliveryOfWagonRepository;
    private final SignerRepository signerRepository;

    public List<MemoOfDispatchDto> getAll() {

        List<MemoOfDispatch> memoOfDispatchList = memoOfDispatchRepository.findAll();
        List<MemoOfDispatchDto> memoOfDispatchDtoList = memoOfDispatchList.stream()
                .map(memoOfDispatch -> memoOfDispatchMapper.mapToDto(memoOfDispatch)).collect(Collectors.toList());
        return memoOfDispatchDtoList;
    }

    public MemoOfDispatchDto getById(Long memoOfDispatchId) {

        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoOfDispatchId).get();
        MemoOfDispatchDto memoOfDispatchDto = memoOfDispatchMapper.mapToDto(memoOfDispatch);
        return memoOfDispatchDto;
    }

    public MemoOfDispatchDto create(MemoOfDispatchDto memoOfDispatchDto) {
        MemoOfDispatch memoOfDispatch = memoOfDispatchMapper.map(memoOfDispatchDto);
        memoOfDispatch.setCreated(Instant.now());
        memoOfDispatch.setAuthor(authService.getCurrentUser());
        memoOfDispatch.setCargoOperation(cargoOperationRepository.findByOperation(memoOfDispatchDto.getCargoOperation().getOperation()));
        memoOfDispatch.setCustomer(customerRepository.findByCustomerName(memoOfDispatchDto.getCustomer().getCustomerName()));
        memoOfDispatch.setComment(memoOfDispatchDto.getComment());
        return memoOfDispatchMapper.mapToDto(memoOfDispatchRepository.save(memoOfDispatch));
    }

    public void update(MemoOfDispatchDto memoOfDispatchDto) {
        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoOfDispatchDto.getMemoOfDispatchId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find memoOfDispatch by id to update"));
        memoOfDispatch.setCargoOperation(cargoOperationRepository
                .findByOperation(memoOfDispatchDto.getCargoOperation().getOperation()));
        memoOfDispatch.setCustomer(customerRepository
                .findByCustomerName(memoOfDispatchDto.getCustomer().getCustomerName()));
        memoOfDispatch.setCreated(memoOfDispatchDto.getCreated());
        memoOfDispatch.setEndDate(memoOfDispatchDto.getEndDate());
        memoOfDispatch.setComment(memoOfDispatchDto.getComment());
        memoOfDispatch.setSigner(signerRepository.findByInitials(memoOfDispatchDto.getSigner().getInitials()));
        memoOfDispatchRepository.save(memoOfDispatch);

        List<DeliveryOfWagon> deliveryList = deliveryOfWagonRepository
                .findAllByMemoOfDispatch_MemoOfDispatchId(memoOfDispatch.getMemoOfDispatchId());
        for (DeliveryOfWagon delivery : deliveryList) {
            DeliveryOfWagon deliveryOfWagonById = deliveryOfWagonRepository.findById(delivery.getDeliveryId()).get();
            deliveryOfWagonById.setEndDate(memoOfDispatch.getEndDate());
//            delivery.setCustomer(memoOfDispatch.getCustomer());
//            delivery.setCargoOperation(memoOfDispatch.getCargoOperation());
            deliveryOfWagonRepository.save(deliveryOfWagonById);
        }
    }

    public void delete(Long memoOfDispatchId) {
        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoOfDispatchId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find memoOfDispatch by id to delete"));
        List<DeliveryOfWagon> deliveryOfWagonList = memoOfDispatch.getDeliveryOfWagonList();
        for (DeliveryOfWagon delivery : deliveryOfWagonList) {
            delivery.setMemoOfDispatch(null);
            deliveryOfWagonRepository.save(delivery);
        }
        memoOfDispatchRepository.delete(memoOfDispatch);
    }

    public List<MemoOfDispatchDto> getSuitableMemoForControllerStatement(Long statementId) {
        ControllerStatement statement = controllerStatementRepository.findById(statementId).get();
        List<MemoOfDispatch> memoOfDispatchList = memoOfDispatchRepository
                .findAllByCustomerAndCargoOperationAndControllerStatement(
                        statement.getCustomer(),
                        statement.getCargoOperation(),
                        null);
        List<MemoOfDispatchDto> memoOfDispatchDtoList = memoOfDispatchList.stream()
                .map(memo -> memoOfDispatchMapper.mapToDto(memo)).collect(Collectors.toList());
        return memoOfDispatchDtoList;
    }

    public void addControllerStatementToMemoOfDispatch(Long memoIdToAdd, Long statementId) {
        MemoOfDispatch memo = memoOfDispatchRepository.findById(memoIdToAdd).get();
        ControllerStatement statement = controllerStatementRepository.findById(statementId).get();
        memo.setControllerStatement(statement);
        memoOfDispatchRepository.save(memo);
    }

    public void removeControllerStatementFromMemo(Long memoId) {
        MemoOfDispatch memo = memoOfDispatchRepository.findById(memoId).get();
        memo.setControllerStatement(null);
        memoOfDispatchRepository.save(memo);
    }
}
