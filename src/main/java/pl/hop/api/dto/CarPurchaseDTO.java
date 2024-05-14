package pl.hop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPurchaseDTO {

    @Email
    private String existingCustomerEmail;
    private String customerName;
    private String customerSurname;
    @Size(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    @Email
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressPostalCode;
    private String customerAddressStreet;

    private String carVin;
    private String salesmanPesel;

    public static CarPurchaseDTO buildDefaultData() {
        return CarPurchaseDTO.builder()
            .customerName("Alfred")
            .customerSurname("Samochodowy")
            .customerPhone("+48 754 552 234")
            .customerEmail("alf.samoch@gmail.com")
            .customerAddressCountry("Polska")
            .customerAddressCity("Wrocław")
            .customerAddressPostalCode("50-001")
            .customerAddressStreet("Bokserska 15")
            .build();
    }



}
