package com.ogunheper.testcontainers.demo.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateProductRequest {

    private String name;

    private SalesDatesType salesDatesType;

    private List<Date> salesDates;
}
