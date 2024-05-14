package pl.hop.testData;

import pl.hop.domain.Invoice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class InvoiceTestData {

    public static Invoice invoiceWithoutCustomer(){
        return Invoice.builder()
                .dateTime(OffsetDateTime.of(2025, 10, 1, 12, 0, 0, 0, ZoneOffset.UTC))
                .car(CarToBuyTestData.someCarToBuy())
                .salesman(SalesmanTestData.someSalesman())
                .build();
    }}
