package pl.hop.util;

import lombok.experimental.UtilityClass;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CepikVehicleDTO;
import pl.hop.api.dto.InvoiceDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class DtoFixtures {

    public static CepikVehicleDTO someCepikVehicle() {
        return CepikVehicleDTO.builder()
                .cepikId("cepikId")
                .brand("brand")
                .model("model")
                .type("type")
                .engineCapacity(new BigDecimal("1000"))
                .weight(1200)
                .fuel("fuel")
                .build();
    }

    public static CarPurchaseDTO someCarPurchaseDTO() {
        return CarPurchaseDTO.buildDefaultData()
                .withCarVin("1FT7X2B60FEA74019")
                .withSalesmanPesel("73021314515");
    }

    public static InvoiceDTO someInvoiceDTO() {
        return InvoiceDTO.builder()
                .invoiceNumber("invNumb")
                .dateTime(OffsetDateTime.of(2020, 10, 10, 10, 30, 15, 0, ZoneOffset.UTC))
                .build();
    }
}
