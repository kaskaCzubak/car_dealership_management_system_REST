package pl.hop.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.hop.domain.Invoice;
import pl.hop.infrastructure.database.entity.InvoiceEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvoiceEntityMapper {

    InvoiceEntity mapToEntity(Invoice invoice);
}
