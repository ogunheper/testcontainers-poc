package com.ogunheper.testcontainers.demo.services;

import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Builder
public class LocalProductFilterService implements ProductFilterService {

    private final MongoTemplate mongoTemplate;

    public List<Product> filter(
            Date availableAt
    ) {
        final Query query = this.anEmptyQuery();

        final List<Product> products = mongoTemplate.find(query, Product.class);

        return this.applyDateFilter(products, availableAt);
    }

    private Query anEmptyQuery() {
        return new Query();
    }

    private List<Product> applyDateFilter(List<Product> products, Date availableAt) {
        if (Objects.isNull(availableAt)) {
            return products;
        }

        return products.stream()
                .filter(product -> product.getProductSalesDates().isAvailableAt(availableAt))
                .collect(Collectors.toList());
    }
}
