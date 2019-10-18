package com.ogunheper.testcontainers.demo.services;

import com.ogunheper.testcontainers.demo.model.*;
import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public class ProductMappingService {

    public Product getProduct(CreateProductRequest createProductRequest) {
        final Product product = this.aNewProduct();

        product.setName(createProductRequest.getName());
        product.setProductSalesDates(
                this.getProductSalesDates(
                        createProductRequest.getSalesDatesType(),
                        createProductRequest.getSalesDates()
                )
        );

        return product;
    }

    public Product aNewProduct() {
        final Product product = Product.builder().build();
        return product;
    }

    public ProductSalesDates getProductSalesDates(SalesDatesType salesDatesType, List<Date> dates) {
        switch (salesDatesType) {
            case DISCRETE:
                return DiscreteProductSalesDates.builder()
                        .dates(dates)
                        .build();
            case CONTINUOUS:
                return ContinuousProductSalesDates.builder()
                        .dates(dates)
                        .build();
        }

        throw new IllegalStateException("Unexpected salesDatesType");
    }
}
