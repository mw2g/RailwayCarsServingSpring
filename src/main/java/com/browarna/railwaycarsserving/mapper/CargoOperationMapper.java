package com.browarna.railwaycarsserving.mapper;

import com.browarna.railwaycarsserving.dto.CargoOperationDto;
import com.browarna.railwaycarsserving.model.CargoOperation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CargoOperationMapper {
    CargoOperation map(CargoOperationDto cargoOperationDto);

    CargoOperationDto mapToDto(CargoOperation cargoOperation);
}
