package pl.hop.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.hop.business.dao.CustomerDAO;
import pl.hop.domain.Customer;
import pl.hop.domain.exception.NotFoundException;
import pl.hop.testData.CustomerTestData;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDAO customerDAO;

    @Test
    void shouldIssueInvoiceCorrectly() {
        // given
        Customer customerToIssueInvoice = CustomerTestData.someCustomer();
        //when, then
        customerService.issueInvoice(customerToIssueInvoice);
        verify(customerDAO).issueInvoice(customerToIssueInvoice);
    }

    @Test
    void shouldFindCustomerWithEmail() {
        // given
        Customer customerWithEmail = CustomerTestData.someCustomer();
        when(customerDAO.findByEmail(customerWithEmail.getEmail())).thenReturn(Optional.of(customerWithEmail));
        // when
        Customer result = customerService.findCustomer(customerWithEmail.getEmail());
        // then
        verify(customerDAO).findByEmail(customerWithEmail.getEmail());
        assertEquals(customerWithEmail, result);
    }

    @Test
    void shouldThrowWhenFindingNoExistingEmail() {
        // given
        Customer customerWithNoExistingEmail = CustomerTestData.someCustomer();
        // when, then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> customerService.findCustomer(customerWithNoExistingEmail.getEmail()),
                "NotFoundException was expected");
        assertEquals(String.format("Could not find customer by email: [%s]", customerWithNoExistingEmail.getEmail()),
                exception.getMessage());
    }

    @Test
    void shouldSaveServiceRequestCorrectly() {
        // given
        Customer customerToSave = CustomerTestData.someCustomer();
        //when, then
        customerService.saveServiceRequest(customerToSave);
        verify(customerDAO).saveServiceRequest(customerToSave);
    }

    @Test
    void shouldSaveCustomerCorrectly() {
        // given
        Customer customerToSave = CustomerTestData.someCustomer();
        when(customerDAO.saveCustomer(customerToSave)).thenReturn(customerToSave);
        // when
        Customer result = customerService.saveCustomer(customerToSave);
        // then
        verify(customerDAO).saveCustomer(customerToSave);
        assertEquals(customerToSave, result);
    }
}