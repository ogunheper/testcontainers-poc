package com.ogunheper.testcontainers.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class DiscreteProductSalesDates implements ProductSalesDates {

    private List<Date> dates;

    @Override
    public boolean isAvailableAt(Date date) {
        return dates.stream().anyMatch(aDate -> aDate.equals(date));
    }
}
