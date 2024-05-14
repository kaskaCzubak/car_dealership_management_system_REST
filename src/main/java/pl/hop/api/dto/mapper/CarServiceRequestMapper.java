package pl.hop.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.hop.api.dto.CarServiceCustomerRequestDTO;
import pl.hop.api.dto.CarServiceMechanicProcessingUnitDTO;
import pl.hop.api.dto.CarServiceRequestDTO;
import pl.hop.domain.*;

@Mapper(componentModel = "spring")
public interface CarServiceRequestMapper extends OffsetDateTimeMapper {

    default CarServiceRequest map(CarServiceCustomerRequestDTO dto) {
        if (dto.isNewCarCandidate()) {
            return CarServiceRequest.builder()
                .customer(customerMap(dto))
                .car(carToServiceMap(dto))
                .customerComment(dto.getCustomerComment())
                .build();
        } else {
            return CarServiceRequest.builder()
                .customer(Customer.builder()
                    .email(dto.getExistingCustomerEmail())
                    .build())
                .car(CarToService.builder()
                    .vin(dto.getExistingCarVin())
                    .build())
                .customerComment(dto.getCustomerComment())
                .build();
        }
    }

    private static CarToService carToServiceMap(CarServiceCustomerRequestDTO dto) {
        return CarToService.builder()
                .vin(dto.getCarVin())
                .brand(dto.getCarBrand())
                .model(dto.getCarModel())
                .year(dto.getCarYear())
                .build();
    }


    private static Customer customerMap(CarServiceCustomerRequestDTO dto) {
        return Customer.builder()
                .name(dto.getCustomerName())
                .surname(dto.getCustomerSurname())
                .phone(dto.getCustomerPhone())
                .email(dto.getCustomerEmail())
                .address(addressMap(dto))
                .build();
    }

    private static Address addressMap(CarServiceCustomerRequestDTO dto) {
        return Address.builder()
                .country(dto.getCustomerAddressCountry())
                .city(dto.getCustomerAddressCity())
                .postalCode(dto.getCustomerAddressPostalCode())
                .address(dto.getCustomerAddressStreet())
                .build();
    }

    @Mapping(source = "car.vin", target = "carVin")
    @Mapping(source = "receivedDateTime", target = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "completedDateTime", target = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    CarServiceRequestDTO map(CarServiceRequest request);

    @Mapping(source = "mechanicComment", target = "comment")
    CarServiceProcessingRequest map(CarServiceMechanicProcessingUnitDTO dto);
}
