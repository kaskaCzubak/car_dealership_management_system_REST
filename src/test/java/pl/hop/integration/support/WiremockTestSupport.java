package pl.hop.integration.support;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WiremockTestSupport {
    Map<String, String> VEHICLE_IDS = Map.of(
            "849930752201839", "pojazd-1.json",
            "3948620503340695", "pojazd-2.json",
            "1545997190537371", "pojazd-3.json",
            "2340228674273462", "pojazd-4.json",
            "4280837150558054", "pojazd-5.json"
    );

    default void stubForDictonary(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathMatching("/slowniki/wojewodztwa"))
                        //TODO Jeżeli GET zostanie wywołany na endpoint /slowniki/wojewodztwa to ma zostać zwrócony
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/slowniki-wojewodztwa.json")));
    }

    default void stubForVehicles(WireMockServer wireMockServer, String dateFrom, String dateTo) {
        Map<String, StringValuePattern> queryParamsPattern = Map.of(
                "data-od", equalTo(dateFrom.replace("-", "")),
                // TODO metoda equalTo() jest z klasy WireMock
                //  LocalDate.toString() 2022-10-10 a Cepik wymaga formatu 20221010
                "data-do", equalTo(dateTo.replace("-", ""))
        );
        wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy"))
                .withQueryParams(queryParamsPattern)
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile("wiremock/pojazdy.json")));
    }

    default void stubForVehicle(WireMockServer wireMockServer) {
        VEHICLE_IDS.forEach((vehicleId, fileName) ->
                wireMockServer.stubFor(get(urlPathEqualTo("/pojazdy/%s".formatted(vehicleId)))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/%s".formatted(fileName)))));
    }
}

