package com.ogunheper.testcontainers.demo.services;

import com.google.common.collect.ImmutableList;
import com.ogunheper.testcontainers.demo.model.DiscreteProductSalesDates;
import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import com.ogunheper.testcontainers.demo.persistence.repository.ProductRepository;
import lombok.Builder;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder
public class ProductUploadService {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

    private final ProductRepository productRepository;

    public void upload(Integer count) {
        productRepository.deleteAll();
        productRepository.saveAll(
                this.products(count)
        );
    }

    @SneakyThrows
    private List<Product> products(int count) {
        final Date startDate = dateFormat.parse("2021-01-01T00:00:00Z");
        return IntStream.range(0, count)
                .mapToObj(index -> aProduct(startDate, index))
                .collect(Collectors.toList());
    }

    private Product aProduct(Date startDate, Integer index) {
        final Date date = Date.from(startDate.toInstant().plus(Duration.ofDays(index)));
        return Product.builder()
                .name("")
                .productSalesDates(
                        DiscreteProductSalesDates.builder()
                                .dates(ImmutableList.of(date))
                                .build()
                )
                .build();
    }
}
