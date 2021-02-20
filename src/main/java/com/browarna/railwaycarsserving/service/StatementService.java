package com.browarna.railwaycarsserving.service;

import com.browarna.railwaycarsserving.dto.StatementRateResponse;
import com.browarna.railwaycarsserving.model.*;
import com.browarna.railwaycarsserving.repository.*;
import com.browarna.railwaycarsserving.dto.StatementDto;
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

    public List<StatementDto> getAllStatements(Date afterDate, Date beforeDate) {

        List<Statement> statementList = statementRepository.findAllByCreatedBetween(afterDate, beforeDate);
        List<StatementDto> statementDtoList = statementList.stream()
                .map(statement -> statementMapper.mapToDto(statement)).collect(Collectors.toList());
        return statementDtoList;
    }

    public StatementWithRateResponse getStatementWithRateById(Long statementId) {

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
        if (statement.getCargoOperation().getOperationName().equals("БЕЗ ОПЕРАЦИИ")) {
            deliveryDispatchTariff.setTariff(0.0);
        }
        Tariff shuntingTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "shuntingWork", date).get();
        Tariff loadUnloadTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "loadUnloadWork", date).get();
        IndexToBaseRate indexToBaseRate = indexToBaseRateRepository
                .findFirstByRelevanceDateLessThanEqualOrderByRelevanceDateDesc(date).get();
        StatementRateResponse response = new StatementRateResponse(deliveryDispatchTimeNorm, turnoverTimeNorm,
                deliveryDispatchTariff, shuntingTariff, loadUnloadTariff, indexToBaseRate);
        StatementWithRateResponse statementWithRateResponse = new StatementWithRateResponse(statementDto, response);
        return statementWithRateResponse;
    }

    public StatementDto getStatementById(Long statementId) {

        Statement statement = statementRepository.findById(statementId).get();
        StatementDto statementDto = statementMapper.mapToDto(statement);
        return statementDto;
    }

    public StatementDto createStatement(StatementDto statementDto) {
        Statement statement = statementMapper.map(statementDto);
        statement.setCreated(statementDto.getCreated());
        statement.setAuthor(authService.getCurrentUser());
        statement.setCargoOperation(cargoOperationRepository
                .findByOperationName(statementDto.getCargoOperation()));
        statement.setCustomer(customerRepository
                .findByCustomerName(statementDto.getCustomer().getCustomerName()));
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
                .findByCustomerName(statementDto.getCustomer().getCustomerName()));
        statement.setSigner(signerRepository.findByInitials(statementDto.getSigner()));
        statement.setComment(statementDto.getComment());

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
    }

    public StatementRateResponse getRate(Long id) {
        Statement statement = statementRepository.findById(id).get();
        Date date = statement.getCreated();
        TimeNorm deliveryDispatchTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "deliveryDispatchTime", date).get();
        TimeNorm turnoverTimeNorm = timeNormRepository
                .findFirstByNormType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "turnoverTime", date).get();
        Tariff deliveryDispatchTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "deliveryDispatchWork", date).get();
        if (statement.getCargoOperation().getOperationName().equals("БЕЗ ОПЕРАЦИИ")) {
            deliveryDispatchTariff.setTariff(0.0);
        }
        Tariff shuntingTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "shuntingWork", date).get();
        Tariff loadUnloadTariff = tariffRepository
                .findFirstByTariffType_TypeCodeAndRelevanceDateLessThanEqualOrderByRelevanceDateDesc(
                        "loadUnloadWork", date).get();
        IndexToBaseRate indexToBaseRate = indexToBaseRateRepository
                .findFirstByRelevanceDateLessThanEqualOrderByRelevanceDateDesc(date).get();
        StatementRateResponse response = new StatementRateResponse(deliveryDispatchTimeNorm, turnoverTimeNorm,
                deliveryDispatchTariff, shuntingTariff, loadUnloadTariff, indexToBaseRate);
        return response;
    }
}
