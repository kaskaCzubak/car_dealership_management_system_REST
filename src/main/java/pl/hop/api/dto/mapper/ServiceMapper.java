package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.ServiceDTO;
import pl.hop.domain.Service;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceDTO map(Service service);
}
