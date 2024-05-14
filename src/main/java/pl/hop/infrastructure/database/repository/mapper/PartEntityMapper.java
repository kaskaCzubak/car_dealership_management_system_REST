package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.Part;
import pl.hop.infrastructure.database.entity.PartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartEntityMapper {

    @Mapping(target = "serviceParts", ignore = true)
    Part mapFromEntity(PartEntity entity);
}
