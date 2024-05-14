package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.ServicePart;
import pl.hop.infrastructure.database.entity.ServicePartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicePartEntityMapper {

    ServicePartEntity mapToEntity(ServicePart servicePart);
}
