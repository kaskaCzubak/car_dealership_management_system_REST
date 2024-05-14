package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.MechanicDTO;
import pl.hop.domain.Mechanic;

@Mapper(componentModel = "spring")
public interface MechanicMapper {

    MechanicDTO map(final Mechanic mechanic);
}
