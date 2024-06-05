package pl.hop.integration.support;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.hop.api.controller.rest.CepikRestController;
import pl.hop.api.dto.CepikVehicleDTO;

import java.time.LocalDate;
import java.util.Map;

public interface CepikControllerTestSupport {

    RequestSpecification requestSpecification();
    default CepikVehicleDTO getCepikRandomVehicle(final LocalDate dateFrom, final LocalDate dateTo) {
        Map<String, Object> params = Map.of(
                "firstRegistrationDateFrom", dateFrom.toString(),
                "firstRegistrationDateTo", dateTo.toString()
        );
        return requestSpecification()
                .params(params)
                .get(CepikRestController.API_CEPIK + CepikRestController.CEPIK_RANDOM)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CepikVehicleDTO.class);
    }
}
