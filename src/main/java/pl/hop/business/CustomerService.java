package pl.hop.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.hop.business.dao.CustomerDAO;
import pl.hop.domain.Customer;
import pl.hop.domain.exception.NotFoundException;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerDAO customerDAO;


    public void issueInvoice(Customer customer) {
        customerDAO.issueInvoice(customer);
    }


    public Customer findCustomer(String email) {
        return customerDAO.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("Could not find customer by email: [%s]".formatted(email)));
    }


    public void saveServiceRequest(Customer customer) {
        customerDAO.saveServiceRequest(customer);
    }


    public Customer saveCustomer(Customer customer) {
        return customerDAO.saveCustomer(customer);
    }
}
