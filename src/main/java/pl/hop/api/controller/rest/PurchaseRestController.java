package pl.hop.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.api.dto.mapper.CarPurchaseMapper;
import pl.hop.api.dto.mapper.InvoiceMapper;
import pl.hop.business.CarPurchaseService;
import pl.hop.domain.CarPurchaseRequest;
import pl.hop.domain.Invoice;

@RestController
@AllArgsConstructor
@RequestMapping(PurchaseRestController.API_PURCHASE)
public class PurchaseRestController {

     public static final String API_PURCHASE = "/api/purchase";

     private final CarPurchaseService carPurchaseService;
     private final CarPurchaseMapper carPurchaseMapper;
     private final InvoiceMapper invoiceMapper;

     @GetMapping
     public CarsToBuyDTO carsPurchaseData() {
          return CarsToBuyDTO.builder()
                  .carsToBuy(carPurchaseService.availableCars().stream() //TODO tutaj potrzebuje przekazać moje samochody czyli będę potrzebować serwisu i pozniej mappera
                          .map(car -> carPurchaseMapper.map(car)) // TODO a->carPurchaseMapper.map(a)
                          .toList())
                  .build();
     }

     @PostMapping
     public InvoiceDTO makePurchase(
             @Valid @RequestBody CarPurchaseDTO carPurchaseDTO
     ) {
          CarPurchaseRequest request = carPurchaseMapper.map(carPurchaseDTO);
          Invoice invoice = carPurchaseService.purchase(request);
          return invoiceMapper.map(invoice);
     }

}