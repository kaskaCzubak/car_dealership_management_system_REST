package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.CarToBuy;
import pl.hop.infrastructure.database.entity.CarToBuyEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarToBuyEntityMapper {

    @Mapping(target = "invoice", ignore = true)
    CarToBuy mapFromEntity(CarToBuyEntity entity);
}
