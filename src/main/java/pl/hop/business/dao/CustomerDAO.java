package pl.hop.business.dao;

import org.springframework.transaction.annotation.Transactional;
import pl.hop.domain.Customer;

import java.util.Optional;

public interface CustomerDAO {

    Optional<Customer> findByEmail(String email);

    void issueInvoice(Customer customer);

    void saveServiceRequest(Customer customer);

    Customer saveCustomer(Customer customer);
}
