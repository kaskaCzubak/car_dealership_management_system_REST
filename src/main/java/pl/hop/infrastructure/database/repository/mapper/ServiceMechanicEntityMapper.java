package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.ServiceMechanic;
import pl.hop.infrastructure.database.entity.ServiceMechanicEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceMechanicEntityMapper {

    ServiceMechanicEntity mapToEntity(ServiceMechanic serviceMechanic);
}
