package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.CepikVehicleDTO;
import pl.hop.domain.CepikVehicle;


@Mapper(componentModel = "spring")
public interface CepikVehicleMapperDto {

    CepikVehicleDTO map(CepikVehicle cepikVehicle);
}

