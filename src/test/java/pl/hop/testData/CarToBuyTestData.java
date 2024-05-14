package pl.hop.testData;

import pl.hop.domain.CarToBuy;

import java.math.BigDecimal;

public class CarToBuyTestData {
    public static CarToBuy someCarToBuy() {
        return CarToBuy.builder()
                .vin("1FT7X2B60FEA74019")
                .brand("BMW")
                .model("Series 1")
                .year(2000)
                .color("black")
                .price(new BigDecimal("30000.00"))
                .build();
    }
}
