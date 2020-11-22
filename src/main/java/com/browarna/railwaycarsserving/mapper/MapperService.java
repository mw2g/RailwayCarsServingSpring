package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.model.*;
import com.browarna.railwaycarsserving.repository.*;
import com.browarna.railwaycarsserving.dto.CustomerDto;
import com.browarna.railwaycarsserving.dto.DeliveryOfWagonDto;
import com.browarna.railwaycarsserving.model.*;
import com.browarna.railwaycarsserving.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MapperService {

    private final CargoTypeRepository cargoTypeRepository;
    private final CargoOperationRepository cargoOperationRepository;
    private final WagonTypeRepository wagonTypeRepository;
    private final OwnerRepository ownerRepository;
    private final CustomerRepository customerRepository;
    private final WagonRepository wagonRepository;
    private final MemoOfDeliveryRepository memoOfDeliveryRepository;
    private final MemoOfDispatchRepository memoOfDispatchRepository;
    private final StatementRepository statementRepository;
    private final SignerRepository signerRepository;
    private final DeliveryOfWagonMapper deliveryOfWagonMapper;
    private final CustomerMapper customerMapper;

    MemoOfDelivery idToMemoOfDelivery(Long memoOfDeliveryId) {
        return memoOfDeliveryId != null ? memoOfDeliveryRepository.findById(memoOfDeliveryId).get() : null;
    }

    MemoOfDispatch idToMemoOfDispatch(Long memoOfDispatchId) {
        return memoOfDispatchId != null ? memoOfDispatchRepository.findById(memoOfDispatchId).get() : null;
    }

    CargoType typeNameToCargoType(String cargoTypeName) {
        return cargoTypeRepository.findByTypeName(cargoTypeName);
    }

    CargoOperation operationNameToCargoOperation(String operationName) {
        return cargoOperationRepository.findByOperationName(operationName);
    }

    WagonType typeNameToWagonType(String typeName) {
        return wagonTypeRepository.findByTypeName(typeName).get();
    }

    Wagon wagonNumberToWagon(String wagonNumber) {
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

    Owner ownerNameToOwner(String ownerName) {
        return ownerRepository.findByOwnerName(ownerName);
    }

    Customer customerNameToCustomer(String customerName) {
        return customerRepository.findByCustomerName(customerName);
    }


    Customer mapCustomer(CustomerDto customerDto) {
        return customerMapper.map(customerDto);
    }

    CustomerDto mapToDtoCustomer(Customer customer) {
        return customerMapper.mapToDto(customer);
    }

    Long getMemoOfDeliveryId(MemoOfDelivery memoOfDelivery) {
        return memoOfDelivery != null ? memoOfDelivery.getMemoOfDeliveryId() : null;
    }

    Long getMemoOfDispatchId(MemoOfDispatch memoOfDispatch) {
        return memoOfDispatch != null ? memoOfDispatch.getMemoOfDispatchId() : null;
    }

    Signer signerInitialsToSigner(String signerInitials) {
        if (signerInitials != null) {
            return signerRepository.findByInitials(signerInitials);
        } else {
            return null;
        }
    }

    String signerToSignerInitials(Signer signer) {
        if (signer != null) {
            return signer.getInitials();
        } else {
            return null;
        }
    }

    String cargoTypeToCargoTypeName(CargoType cargoType) {
        if (cargoType != null) {
            return cargoType.getTypeName();
        } else {
            return null;
        }
    }

    String cargoOperationToCargoOperationName(CargoOperation cargoOperation) {
        if (cargoOperation != null) {
            return cargoOperation.getOperationName();
        } else {
            return null;
        }
    }

    String wagonTypeToWagonTypeName(WagonType wagonType) {
        if (wagonType != null) {
            return wagonType.getTypeName();
        } else {
            return null;
        }
    }

    String wagonToWagonNumber(Wagon wagon) {
        if (wagon != null) {
            return wagon.getWagonNumber();
        } else {
            return null;
        }
    }

    String ownerToOwnerName(Owner owner) {
        if (owner != null) {
            return owner.getOwnerName();
        } else {
            return null;
        }
    }

    String customerToCustomerName(Customer customer) {
        if (customer != null) {
            return customer.getCustomerName();
        } else {
            return null;
        }
    }

    String authorToAuthorInitials(User author) {
        if (author != null) {
            return author.getInitials();
        } else {
            return null;
        }
    }

    List<DeliveryOfWagon> mapDeliveryOfWagonList(List<DeliveryOfWagonDto> deliveryOfWagonDtoList) {
        if (deliveryOfWagonDtoList != null) {
            List<DeliveryOfWagon> deliveryOfWagonList = deliveryOfWagonDtoList.stream()
                    .map(deliveryOfWagonDto -> deliveryOfWagonMapper.map(deliveryOfWagonDto))
                    .collect(Collectors.toList());
            return deliveryOfWagonList;
        } else {
            return null;
        }
    }

    List<DeliveryOfWagonDto> mapToDtoDeliveryOfWagonList(List<DeliveryOfWagon> deliveryOfWagonList) {
        if (deliveryOfWagonList != null) {
            List<DeliveryOfWagonDto> deliveryOfWagonDtoList = deliveryOfWagonList.stream()
                    .map(deliveryOfWagon -> deliveryOfWagonMapper.mapToDto(deliveryOfWagon))
                    .collect(Collectors.toList());
            return deliveryOfWagonDtoList;
        } else {
            return null;
        }
    }

    Long statementToStatementId(Statement statement) {
        if (statement != null) {
            return statement.getStatementId();
        }
        return null;
    }

    Statement statementIdToStatement(Long statementId) {
        if (statementId != null) {
            return statementRepository.findById(statementId).get();
        }
        return null;
    }

}
