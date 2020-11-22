package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.mapper.MemoOfDispatchMapper;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import com.browarna.railwaycarsserving.repository.*;
import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.model.Statement;
import com.browarna.railwaycarsserving.model.MemoOfDispatch;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class MemoOfDispatchService {
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final StatementRepository statementRepository;
    private final MemoOfDispatchMapper memoOfDispatchMapper;
    private final AuthService authService;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryOfWagonRepository deliveryOfWagonRepository;
    private final SignerRepository signerRepository;

    public List<MemoOfDispatchDto> getAll(Date afterDate, Date beforeDate) {

        List<MemoOfDispatch> memoOfDispatchList = memoOfDispatchRepository.findAllByEndDateBetween(afterDate, beforeDate);
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
        memoOfDispatch.setCreated(new Date());
        memoOfDispatch.setAuthor(authService.getCurrentUser());
        memoOfDispatch.setCargoOperation(cargoOperationRepository.findByOperationName(memoOfDispatchDto.getCargoOperation()));
        memoOfDispatch.setCustomer(customerRepository.findByCustomerName(memoOfDispatchDto.getCustomer().getCustomerName()));
        memoOfDispatch.setComment(memoOfDispatchDto.getComment());
        return memoOfDispatchMapper.mapToDto(memoOfDispatchRepository.save(memoOfDispatch));
    }

    public void update(MemoOfDispatchDto memoOfDispatchDto) {
        MemoOfDispatch memoOfDispatch = memoOfDispatchRepository.findById(memoOfDispatchDto.getMemoOfDispatchId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find memoOfDispatch by id to update"));
        memoOfDispatch.setCargoOperation(cargoOperationRepository
                .findByOperationName(memoOfDispatchDto.getCargoOperation()));
        memoOfDispatch.setCustomer(customerRepository
                .findByCustomerName(memoOfDispatchDto.getCustomer().getCustomerName()));
        memoOfDispatch.setCreated(memoOfDispatchDto.getCreated());
        memoOfDispatch.setEndDate(memoOfDispatchDto.getEndDate());
        memoOfDispatch.setComment(memoOfDispatchDto.getComment());
        memoOfDispatch.setSigner(signerRepository.findByInitials(memoOfDispatchDto.getSigner()));

//        Statement statement = new Statement();
        if (memoOfDispatchDto.getStatement() != null) {
            Statement statement = statementRepository.findById(memoOfDispatchDto.getStatement())
                    .orElseThrow(() -> new RailwayCarsServingException("Can`t find statement by id to update"));
            memoOfDispatch.setStatement(statement);
        } else{
            memoOfDispatch.setStatement(null);
        }

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

    public List<MemoOfDispatchDto> getSuitableMemoForStatement(Long statementId) {
        Statement statement = statementRepository.findById(statementId).get();
        List<MemoOfDispatch> memoOfDispatchList = memoOfDispatchRepository
                .findAllByCustomerAndCargoOperationAndStatement(
                        statement.getCustomer(),
                        statement.getCargoOperation(),
                        null);
        List<MemoOfDispatchDto> memoOfDispatchDtoList = memoOfDispatchList.stream()
                .map(memo -> memoOfDispatchMapper.mapToDto(memo)).collect(Collectors.toList());
        return memoOfDispatchDtoList;
    }

    public void addStatementToMemoOfDispatch(Long memoIdToAdd, Long statementId) {
        MemoOfDispatch memo = memoOfDispatchRepository.findById(memoIdToAdd).get();
        Statement statement = statementRepository.findById(statementId).get();
        memo.setStatement(statement);
        memoOfDispatchRepository.save(memo);
    }

    public void removeStatementFromMemo(Long memoId) {
        MemoOfDispatch memo = memoOfDispatchRepository.findById(memoId).get();
        memo.setStatement(null);
        memoOfDispatchRepository.save(memo);
    }
}
