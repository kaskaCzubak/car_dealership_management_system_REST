package pl.hop.business.dao;

import pl.hop.domain.Part;

import java.util.List;
import java.util.Optional;

public interface PartDAO {

    Optional<Part> findBySerialNumber(String serialNumber);

    List<Part> findAll();
}
