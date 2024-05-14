package pl.hop.business.mapper;

import pl.hop.domain.CarToBuy;
import pl.hop.domain.CarToService;

public class CarToServiceMapper {

    public static CarToService map(CarToBuy carToBuy) {
        return CarToService.builder()
                .vin(carToBuy.getVin())
                .brand(carToBuy.getBrand())
                .model(carToBuy.getModel())
                .year(carToBuy.getYear())
                .build();
    }
}
