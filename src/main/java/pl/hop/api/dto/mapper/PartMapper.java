package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.hop.api.dto.PartDTO;
import pl.hop.domain.Part;

@Mapper(componentModel = "spring")
public interface PartMapper {

    PartDTO map(Part part);
}
