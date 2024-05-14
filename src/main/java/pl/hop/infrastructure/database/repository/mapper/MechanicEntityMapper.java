package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.Mechanic;
import pl.hop.infrastructure.database.entity.MechanicEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MechanicEntityMapper {

    @Mapping(target = "serviceMechanics", ignore = true)
    Mechanic mapFromEntity(MechanicEntity entity);
}
