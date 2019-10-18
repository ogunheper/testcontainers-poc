package com.ogunheper.testcontainers.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContinuousProductSalesDates implements ProductSalesDates {

    private Date startDate;
    private Date endDate;

    @Builder
    @SuppressWarnings("unused")
    private ContinuousProductSalesDates(List<Date> dates) {
        this.startDate = dates.get(0);
        this.endDate = dates.get(1);
    }

    @Override
    public boolean isAvailableAt(Date date) {
        return startDate.compareTo(date) <= 0 && date.compareTo(endDate) <= 0;
    }
}
