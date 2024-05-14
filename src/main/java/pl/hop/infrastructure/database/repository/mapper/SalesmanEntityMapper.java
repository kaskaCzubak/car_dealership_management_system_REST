package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.Salesman;
import pl.hop.infrastructure.database.entity.SalesmanEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SalesmanEntityMapper {

    @Mapping(target = "invoices", ignore = true)
    Salesman mapFromEntity(SalesmanEntity entity);
}
