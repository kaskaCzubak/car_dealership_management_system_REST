package pl.hop.business.dao;

import pl.hop.domain.CepikVehicle;

import java.time.LocalDate;
import java.util.List;

public interface CepikVehicleDAO {

    List<CepikVehicle> getCepikVehicles(final LocalDate dateFrom, final LocalDate dateTo);

    CepikVehicle getCepikVehicle(final String vehicleId);
}
