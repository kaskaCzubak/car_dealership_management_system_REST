package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.SalesmanDTO;
import pl.hop.domain.Salesman;

@Mapper(componentModel = "spring")
public interface SalesmanMapper {

    SalesmanDTO map(final Salesman salesman);
}
