package pl.hop.testData;

import pl.hop.domain.CarToService;

public class CarToServiceTestData {

    public static CarToService someCarToService() {
        return CarToService.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("BMW")
                .model("Series 1")
                .year(2000)
                .build();
    }
}
