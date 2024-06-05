package pl.hop.integration.rest;

import org.junit.jupiter.api.Test;
import pl.hop.api.dto.CepikVehicleDTO;
import pl.hop.integration.configuration.RestAssuredIntegrationTestBase;
import pl.hop.integration.support.CepikControllerTestSupport;
import pl.hop.integration.support.WiremockTestSupport;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CepikIT
        extends RestAssuredIntegrationTestBase
        implements CepikControllerTestSupport, WiremockTestSupport {

    @Test
    void thatFindingRandomVehicleWorksCorrectly() {
        // given
        LocalDate dateFrom = LocalDate.of(2022, 10, 10);
        LocalDate dateTo = LocalDate.of(2023, 10, 10);
        stubForDictonary(wireMockServer);
        stubForVehicles(wireMockServer, dateFrom.toString(), dateTo.toString());
        stubForVehicle(wireMockServer);

        // when
        CepikVehicleDTO randomVehicle1 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle2 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle3 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle4 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle5 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle6 = getCepikRandomVehicle(dateFrom, dateTo);
        CepikVehicleDTO randomVehicle7 = getCepikRandomVehicle(dateFrom, dateTo);

        Set<CepikVehicleDTO> randomVehicles = new HashSet<>();
        randomVehicles.add(randomVehicle1);
        randomVehicles.add(randomVehicle2);
        randomVehicles.add(randomVehicle3);
        randomVehicles.add(randomVehicle4);
        randomVehicles.add(randomVehicle5);
        randomVehicles.add(randomVehicle6);
        randomVehicles.add(randomVehicle7);
        assertThat(randomVehicles).hasSizeGreaterThan(1);
    }

}

