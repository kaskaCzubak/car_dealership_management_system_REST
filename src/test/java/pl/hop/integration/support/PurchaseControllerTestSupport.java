package pl.hop.integration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.hop.api.controller.rest.PurchaseRestController;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;

public interface PurchaseControllerTestSupport {
    RequestSpecification requestSpecification();

    //TODO te dwie metody są potrzebne aby na serwerze znalezc te cars i na serwerze wywołać kupno car
    default InvoiceDTO purchaseCar(final CarPurchaseDTO carPurchaseDTO) {
        return requestSpecification()
                .body(carPurchaseDTO)
                .post(PurchaseRestController.API_PURCHASE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InvoiceDTO.class); //TODO takiego typu obiekt ma być użyty jako odpowiedz
    }

    default CarsToBuyDTO findAvailableCars() {
        return requestSpecification()
                .get(PurchaseRestController.API_PURCHASE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CarsToBuyDTO.class);
    }

}
