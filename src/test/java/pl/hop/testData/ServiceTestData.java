package pl.hop.testData;

import pl.hop.domain.Service;

import java.math.BigDecimal;
import java.util.Set;

public class ServiceTestData {

    public static Service someService(){
        return Service.builder()
                .serviceCode("58394-014")
                .description("Tire change")
                .price(new BigDecimal("240.20"))
                .serviceMechanics(Set.of())
                .build();
    }
}
