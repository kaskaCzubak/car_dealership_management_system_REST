package pl.hop.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.MechanicDAO;
import pl.hop.domain.Mechanic;
import pl.hop.domain.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MechanicService {

    private final MechanicDAO mechanicDAO;


    public List<Mechanic> findAvailable() {
        List<Mechanic> availableMechanics = mechanicDAO.findAvailable();
        log.info("Available salesmen: [{}]", availableMechanics.size());
        return availableMechanics;
    }


    public Mechanic findMechanic(String pesel) {
        return mechanicDAO.findByPesel(pesel)
                .orElseThrow(()-> new NotFoundException("Could not find mechanic by pesel: [%s]".formatted(pesel)));
    }
}
