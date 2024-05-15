package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarToBuyDTO;
import pl.hop.domain.CarPurchaseRequest;
import pl.hop.domain.CarToBuy;

@Mapper(componentModel = "spring")
public interface CarPurchaseMapper {

     CarPurchaseRequest map(final CarPurchaseDTO dto);

    CarToBuyDTO map(CarToBuy car);
}
