package com.ogunheper.testcontainers.demo.persistence.domain;

import com.ogunheper.testcontainers.demo.model.ProductSalesDates;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    private String name;

    private ProductSalesDates productSalesDates;
}
