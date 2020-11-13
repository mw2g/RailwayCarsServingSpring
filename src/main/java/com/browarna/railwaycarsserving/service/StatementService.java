package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.StatementDto;
import com.browarna.railwaycarsserving.dto.StatementRateResponse;
import com.browarna.railwaycarsserving.dto.StatementWithRateResponse;
import com.browarna.railwaycarsserving.exceptions.RailwayCarsServingException;
import com.browarna.railwaycarsserving.mapper.StatementMapper;
import com.browarna.railwaycarsserving.model.*;
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
public class StatementService {
    private final StatementRepository statementRepository;
    private final StatementMapper statementMapper;
    private final AuthService authService;
    private final CargoOperationRepository cargoOperationRepository;
    private final CustomerRepository customerRepository;
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final SignerRepository signerRepository;
    private final TimeNormRepository timeNormRepository;
    private final TariffRepository tariffRepository;
    private final IndexToBaseRateRepository indexToBaseRateRepository;

    public List<StatementDto> getAllStatements() {

        List<Statement> statementList = statementRepository.findAll();
        List<StatementDto> statementDtoList = statementList.stream()
                .map(statement -> statementMapper.mapToDto(statement)).collect(Collectors.toList());
        return statementDtoList;
    }

    public StatementWithRateResponse getStatementById(Long statementId) {

        Statement statement = statementRepository.findById(statementId).get();
        StatementDto statementDto = statementMapper.mapToDto(statement);
        Date date = statementDto.getCreated();
        TimeNorm deliveryDispatchTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "deliveryDispatchTime", date).get();
        TimeNorm turnoverTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "turnoverTime", date).get();
        Tariff deliveryDispatchTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "deliveryDispatchWork", date).get();
        Tariff shuntingTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "shuntingWork", date).get();
        IndexToBaseRate indexToBaseRate = indexToBaseRateRepository
                .findFirstByRelevanceDateLessThanEqualOrderByRelevanceDateDesc(date).get();
        StatementRateResponse response = new StatementRateResponse(deliveryDispatchTimeNorm, turnoverTimeNorm,
                deliveryDispatchTariff, shuntingTariff, indexToBaseRate);
        StatementWithRateResponse statementWithRateResponse = new StatementWithRateResponse(statementDto, response);
        return statementWithRateResponse;
    }

//    public StatementDto getStatementById(Long statementId) {
//
//        Statement statement = statementRepository.findById(statementId).get();
//        StatementDto statementDto = statementMapper.mapToDto(statement);
//        return statementDto;
//    }

    public StatementDto createStatement(StatementDto statementDto) {
        Statement statement = statementMapper.map(statementDto);
        statement.setCreated(statementDto.getCreated());
        statement.setAuthor(authService.getCurrentUser());
        statement.setCargoOperation(cargoOperationRepository
                .findByOperationName(statementDto.getCargoOperation()));
        statement.setCustomer(customerRepository
                .findByCustomerName(statementDto.getCustomer()));
        statement.setComment(statementDto.getComment());
        return statementMapper.mapToDto(statementRepository.save(statement));
    }

    public StatementDto updateStatement(StatementDto statementDto) {
        Statement statement = statementRepository.findById(statementDto.getStatementId())
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find statement by id to update"));
        statement.setCreated(statementDto.getCreated());
        CargoOperation cargoOperation = cargoOperationRepository
                .findByOperationName(statementDto.getCargoOperation());
        statement.setCargoOperation(cargoOperation);
        statement.setCustomer(customerRepository
                .findByCustomerName(statementDto.getCustomer()));
        statement.setSigner(signerRepository.findByInitials(statementDto.getSigner()));
        statement.setComment(statementDto.getComment());

//        for (MemoOfDispatchDto memo : statementDto.getMemoOfDispatchList()) {
//            MemoOfDispatch memoOfDispatchById = memoOfDispatchRepository.findById(memo.getMemoOfDispatchId()).get();
//            memoOfDispatchById.setStartDate(statement.getStartDate());
//            memoOfDispatchById.setCustomer(statement.getCustomer());
//            memoOfDispatchById.setCargoOperation(statement.getCargoOperation());
//            if (cargoOperation.getOperationId() != 1) {
//                memoOfDispatchById.setLoadUnloadWork(false);
//            }
//            memoOfDispatchRepository.save(memoOfDispatchById);
//        }

        return statementMapper.mapToDto(statementRepository.save(statement));
    }

    public void deleteStatement(Long statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> new RailwayCarsServingException("Can`t find statement by id to delete"));
        List<MemoOfDispatch> memoOfDispatchList = statement.getMemoOfDispatchList();
        for (MemoOfDispatch memo : memoOfDispatchList) {
            memo.setStatement(null);
            memoOfDispatchRepository.save(memo);
        }
        statementRepository.delete(statement);
//        for (DeliveryOfWagon delivery : statement.getDeliveryOfWagonList()) {
//            memoOfDispatchRepository.delete(delivery);
//        }
    }

    public StatementRateResponse getRate(Long id) {
        Statement statement = statementRepository.findById(id).get();
        Date date = statement.getCreated();
//        Date date = Date.from(statement.getCreated());
        StatementRateResponse response = new StatementRateResponse();
        TimeNorm turnoverTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                "turnoverTime", date).get();
        TimeNorm deliveryDispatchTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                "deliveryDispatchTime", date).get();
        response.setTurnoverTimeNorm(turnoverTimeNorm);
        return response;
    }
}
