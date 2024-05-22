package pl.hop.business.dao;

import pl.hop.domain.CepikVehicle;

import java.util.*;
import java.time.LocalDate;

public interface CepikVehicleDao {

    List<CepikVehicle> getCepikVehicles(final LocalDate dateFrom, final LocalDate dateTo);

    CepikVehicle getCepikVehicle(final String vehicleId);
}
