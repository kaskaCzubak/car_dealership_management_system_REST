package pl.hop.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.CepikVehicleDao;
import pl.hop.domain.CepikVehicle;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.domain.exception.ProcessingException;

import java.util.*;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class CepikService {

    private final CepikVehicleDao cepikVehicleDao;

    public CepikVehicle findRandom(final LocalDate dateFrom, final LocalDate dateTo) {
        log.debug("Looking for random CEPIK vehicle, first registration between: [{}] and: [{}]", dateFrom, dateTo);
        List<CepikVehicle> cepikVehicles = cepikVehicleDao.getCepikVehicles(dateFrom, dateTo);
        if (cepikVehicles.isEmpty()) {
            throw new ProcessingException(
                    "CEPIK returned empty list for first registration between: [%s] and: [%s]"
                            .formatted(dateFrom, dateTo)
            );
        }

        return Optional.ofNullable(cepikVehicles.get(new Random().nextInt(cepikVehicles.size())))
                //TODO z takiego zakresu wyciagne sobie losowy samochód
                //newRandom() można zaimplementować jako stream i findAny()
                .map(anyVehicle -> cepikVehicleDao.getCepikVehicle(anyVehicle.getCepikId()))
                .orElseThrow(() -> new NotFoundException("Could not find random CEPIK vehicle"));
    }

}
