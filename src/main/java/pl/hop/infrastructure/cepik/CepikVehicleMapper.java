package pl.hop.infrastructure.cepik;

import pl.hop.domain.CepikVehicle;
import pl.hop.infrastructure.cepik.model.VehicleDto;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CepikVehicleMapper {

    public CepikVehicle map(String id, VehicleDto attributes) {
        var builder = CepikVehicle.builder()
                .cepikId(id);

        Optional.ofNullable(attributes) //TODO caly czas zabezpieczam sie prze nullami, bo nie wiemy co przyjdzie z naszego API
                .ifPresent(dto -> builder
                        .brand(dto.getMarka())
                        .model(dto.getModel())
                        .type(dto.getTyp())
                        .engineCapacity(dto.getPojemnoscSkokowaSilnika())
                        .weight(dto.getMasaWlasna())
                        .fuel(dto.getRodzajPaliwa())
                );
        return builder.build();
    }
}

