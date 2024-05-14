package pl.hop.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.hop.api.dto.CarServiceCustomerRequestDTO;
import pl.hop.api.dto.mapper.CarServiceRequestMapper;
import pl.hop.business.CarServiceRequestService;
import pl.hop.domain.CarServiceRequest;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ServiceController {

    private static final String SERVICE_NEW = "/service/new";
    private static final String SERVICE_REQUEST = "/service/request";

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceRequestMapper carServiceRequestMapper;

    @GetMapping(value = SERVICE_NEW)
    public ModelAndView carServicePage() {
        Map<String, ?> model = Map.of(
            "carServiceRequestDTO", CarServiceCustomerRequestDTO.buildDefault()
        );
        return new ModelAndView("car_service_request", model);
    }

    @PostMapping(value = SERVICE_REQUEST)
    public String makeServiceRequest(
        @ModelAttribute("carServiceRequestDTO") CarServiceCustomerRequestDTO carServiceCustomerRequestDTO,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return "error";
        }
        CarServiceRequest serviceRequest = carServiceRequestMapper.map(carServiceCustomerRequestDTO);
        carServiceRequestService.makeServiceRequest(serviceRequest);

        return "car_service_request_done";
    }
}