package pl.hop.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.hop.api.dto.CarServiceCustomerRequestDTO;
import pl.hop.api.dto.CarServiceRequestDTO;
import pl.hop.api.dto.CarServiceRequestsDTO;
import pl.hop.api.dto.mapper.CarServiceRequestMapper;
import pl.hop.business.CarServiceRequestService;
import pl.hop.domain.CarServiceRequest;

import java.util.Map;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ServiceRestController.API_SERVICE)
public class ServiceRestController {

    public static final String API_SERVICE = "/api/service";
    public static final String SERVICE_REQUEST = "/request";

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceRequestMapper carServiceRequestMapper;

    @PostMapping(value = SERVICE_REQUEST)
    public CarServiceRequestsDTO makeServiceRequest(
            @Valid @RequestBody CarServiceCustomerRequestDTO carServiceCustomerRequestDTO
    ) {
        CarServiceRequest serviceRequest = carServiceRequestMapper.map(carServiceCustomerRequestDTO);
        carServiceRequestService.makeServiceRequest(serviceRequest);
        return CarServiceRequestsDTO.builder()
                .carServiceRequests(getAvailableCarServiceRequests())
                .build();
    }

    private List<CarServiceRequestDTO> getAvailableCarServiceRequests() {
        return carServiceRequestService.availableServiceRequests().stream()
                .map(carServiceRequestMapper::map)
                .toList();
    }
}
