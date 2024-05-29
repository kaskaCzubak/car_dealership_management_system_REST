package pl.hop.integration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.hop.api.controller.rest.CepikRestController;
import pl.hop.api.dto.CepikVehicleDTO;

import java.time.LocalDate;
import java.util.Map;

public interface CepikControllerTestSupport {
    //TODO w tej klasie piszemy wywołanie do naszego serwera czyli naszą metodę getCepikRandomVehicle()
    // gdzie będzie pobierać losowe pojazdy

    RequestSpecification requestSpecification();
    default CepikVehicleDTO getCepikRandomVehicle(final LocalDate dateFrom, final LocalDate dateTo) {
        Map<String, Object> params = Map.of(
                "firstRegistrationDateFrom", dateFrom.toString(),
                "firstRegistrationDateTo", dateTo.toString()
        );
        return requestSpecification()
                .params(params) //TODO przekazanie parametrów
                .get(CepikRestController.API_CEPIK + CepikRestController.CEPIK_RANDOM)
                // TODO metoda GET na endpoint
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CepikVehicleDTO.class);
    }
}
