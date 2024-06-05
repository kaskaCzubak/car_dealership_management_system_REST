package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.domain.Invoice;

@Mapper(componentModel = "spring")
public interface InvoiceMapper  {

    InvoiceDTO map(final Invoice invoice);


}
