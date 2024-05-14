package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.domain.CarPurchaseRequest;

@Mapper(componentModel = "spring")
public interface CarPurchaseMapper {

     CarPurchaseRequest map(final CarPurchaseDTO dto);
}
