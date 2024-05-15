package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.hop.api.dto.CarHistoryDTO;
import pl.hop.api.dto.CarToBuyDTO;
import pl.hop.api.dto.CarToServiceDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.domain.CarHistory;
import pl.hop.domain.CarToBuy;
import pl.hop.domain.CarToService;
import pl.hop.domain.Invoice;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper  {

    InvoiceDTO map(final Invoice invoice);


}
