package pl.hop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarServiceRequestsDTO {

    private List<CarServiceRequestDTO> carServiceRequests;
}
