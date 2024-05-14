package pl.hop.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.SalesmanDAO;
import pl.hop.domain.Salesman;
import pl.hop.domain.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SalesmanService {

    private final SalesmanDAO salesmanDAO;


    public List<Salesman> findAvailable() {
        List<Salesman> availableSalesmen = salesmanDAO.findAvailable();
        log.info("Available salesmen: [{}]", availableSalesmen.size());
        return availableSalesmen;
    }


    public Salesman findSalesman(String pesel) {

        return salesmanDAO.findByPesel(pesel)
                .orElseThrow(()-> new NotFoundException("Could not find salesman by pesel: [%s]".formatted(pesel)));
    }
}
