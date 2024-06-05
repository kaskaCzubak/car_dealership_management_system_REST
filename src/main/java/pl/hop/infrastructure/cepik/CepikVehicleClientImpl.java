package pl.hop.infrastructure.cepik;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.CepikVehicleDAO;
import pl.hop.domain.CepikVehicle;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.infrastructure.cepik.api.PojazdyApi;
import pl.hop.infrastructure.cepik.api.SownikiApi;
import pl.hop.infrastructure.cepik.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CepikVehicleClientImpl implements CepikVehicleDAO {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String VOIVODESHIP_KEY = "MAZOWIECKIE";
    private static final String VOIVODESHIP_DICTIONARY = "wojewodztwa";

    private final PojazdyApi pojazdyApi;
    private final SownikiApi sownikiApi;
    private final CepikVehicleMapper cepikVehicleMapper;

    @Override
    public CepikVehicle getCepikVehicle(String vehicleId) {
        return Optional.ofNullable(pojazdyApi.getPojazd(vehicleId, null).block())
                .map(JsonApiForObjectVehicle::getData)
                .map(data -> cepikVehicleMapper.map(data.getId(), data.getAttributes()))
                .orElseThrow(() -> new NotFoundException("Could not find a vehicle with vehicleId: [%s]".formatted(vehicleId)));
    }

    @Override
    public List<CepikVehicle> getCepikVehicles(LocalDate dateFrom, LocalDate dateTo) {

        var dictionaryEntry = getDictionaryEntry();
        var vehicles = callVehicles(dateFrom, dateTo, dictionaryEntry.getKluczSlownika());

        return vehicles
                .map(JsonApiForListVehicle::getData)
                .map(data -> data.stream()
                        .map(vehicleData -> cepikVehicleMapper.map(vehicleData.getId(), vehicleData.getAttributes()))
                        .toList())
                .orElse(Collections.emptyList());
    }

    private DictionaryDtoElement getDictionaryEntry() {
        JsonApiForObjectDictionary dictionary = Optional.ofNullable(sownikiApi.getSlownik(VOIVODESHIP_DICTIONARY).block())
                .orElseThrow(() -> new NotFoundException(
                        "Could not find dictionary definition for: [%s]"
                                .formatted(VOIVODESHIP_DICTIONARY)
                ));


         return Optional.ofNullable(dictionary.getData())
                .map(ApiAttributesDtoDictionary::getAttributes)
                .map(DictionaryDto::getDostepneRekordySlownika)
                .flatMap(records -> records.stream()
                        .filter(record -> VOIVODESHIP_KEY.equals(record.getWartoscSlownika()))
                        .findAny())
                .orElseThrow(() ->
                        new NotFoundException(
                                "Could not find dictionary definition for: [%s]"
                                        .formatted(VOIVODESHIP_KEY)));
    }

    private Optional<JsonApiForListVehicle> callVehicles(LocalDate dateFrom, LocalDate dateTo, String key) {
        return Optional.ofNullable(pojazdyApi.getListaPojazdow(
                key,
                dateFrom.format(DATE_TIME_FORMATTER),
                dateTo.format(DATE_TIME_FORMATTER),
                "1",
                true,
                true,
                null,
                "50",
                "1",
                null
        ).block());
    }

}
