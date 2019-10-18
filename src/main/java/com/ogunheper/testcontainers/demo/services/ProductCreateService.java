package com.ogunheper.testcontainers.demo.services;

import com.ogunheper.testcontainers.demo.model.CreateProductRequest;
import com.ogunheper.testcontainers.demo.model.IdResponse;
import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import com.ogunheper.testcontainers.demo.persistence.repository.ProductRepository;
import lombok.Builder;

@Builder
public class ProductCreateService {

    private final ProductRepository productRepository;
    private final ProductMappingService productMappingService;

    public IdResponse<String> create(CreateProductRequest createProductRequest) {
        Product product = productMappingService.getProduct(createProductRequest);

        product = productRepository.save(product);

        return IdResponse.<String>builder()
                .id(product.getId())
                .build();
    }
}
