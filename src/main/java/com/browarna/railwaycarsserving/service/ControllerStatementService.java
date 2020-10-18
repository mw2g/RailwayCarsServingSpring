package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.ControllerStatementDto;
import com.browarna.railwaycarsserving.dto.MemoOfDispatchDto;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.ControllerStatementMapper;
import com.browarna.railwaycarsserving.model.*;
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
public class ControllerStatementService {
    private final ControllerStatementRepository controllerStatementRepository;
    private final ControllerStatementMapper controllerStatementMapper;
    private final AuthService authService;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final SignerRepository signerRepository;

    public List<ControllerStatementDto> getAllControllerStatements() {

        List<ControllerStatement> controllerStatementList = controllerStatementRepository.findAll();
        List<ControllerStatementDto> controllerStatementDtoList = controllerStatementList.stream()
                .map(controllerStatement -> controllerStatementMapper.mapToDto(controllerStatement)).collect(Collectors.toList());
        return controllerStatementDtoList;
    }

    public ControllerStatementDto getControllerStatementById(Long statementId) {

        ControllerStatement controllerStatement = controllerStatementRepository.findById(statementId).get();
        ControllerStatementDto controllerStatementDto = controllerStatementMapper.mapToDto(controllerStatement);
        return controllerStatementDto;
    }

    public ControllerStatementDto createControllerStatement(ControllerStatementDto controllerStatementDto) {
        ControllerStatement controllerStatement = controllerStatementMapper.map(controllerStatementDto);
        controllerStatement.setCreated(Instant.now());
        controllerStatement.setAuthor(authService.getCurrentUser());
        controllerStatement.setCargoOperation(cargoOperationRepository.findById(controllerStatementDto.getCargoOperation().getOperationId()).get());
        controllerStatement.setCustomer(customerRepository.findByCustomerName(controllerStatementDto.getCustomer().getCustomerName()));
        controllerStatement.setComment(controllerStatementDto.getComment());
        return controllerStatementMapper.mapToDto(controllerStatementRepository.save(controllerStatement));
    }

    public ControllerStatementDto updateControllerStatement(ControllerStatementDto controllerStatementDto) {
        ControllerStatement controllerStatement = controllerStatementRepository.findById(controllerStatementDto.getStatementId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find controllerStatement by id to update"));
        CargoOperation cargoOperation = cargoOperationRepository
                .findById(controllerStatementDto.getCargoOperation().getOperationId()).get();
        controllerStatement.setCargoOperation(cargoOperation);
        controllerStatement.setCustomer(customerRepository
                .findByCustomerName(controllerStatementDto.getCustomer().getCustomerName()));
        controllerStatement.setSigner(signerRepository.findByInitials(controllerStatementDto.getSigner().getInitials()));
        controllerStatement.setComment(controllerStatementDto.getComment());

//        for (MemoOfDispatchDto memo : controllerStatementDto.getMemoOfDispatchList()) {
//            MemoOfDispatch memoOfDispatchById = memoOfDispatchRepository.findById(memo.getMemoOfDispatchId()).get();
//            memoOfDispatchById.setStartDate(controllerStatement.getStartDate());
//            memoOfDispatchById.setCustomer(controllerStatement.getCustomer());
//            memoOfDispatchById.setCargoOperation(controllerStatement.getCargoOperation());
//            if (cargoOperation.getOperationId() != 1) {
//                memoOfDispatchById.setLoadUnloadWork(false);
//            }
//            memoOfDispatchRepository.save(memoOfDispatchById);
//        }

        return controllerStatementMapper.mapToDto(controllerStatementRepository.save(controllerStatement));
    }

    public void deleteControllerStatement(Long controllerStatementId) {
        ControllerStatement controllerStatement = controllerStatementRepository.findById(controllerStatementId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find controllerStatement by id to delete"));
        List<MemoOfDispatch> memoOfDispatchList = controllerStatement.getMemoOfDispatchList();
        for (MemoOfDispatch memo : memoOfDispatchList) {
            memo.setControllerStatement(null);
            memoOfDispatchRepository.save(memo);
        }
        controllerStatementRepository.delete(controllerStatement);
//        for (DeliveryOfWagon delivery : controllerStatement.getDeliveryOfWagonList()) {
//            memoOfDispatchRepository.delete(delivery);
//        }
    }
}
