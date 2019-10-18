package com.ogunheper.testcontainers.demo.configuration;

import com.ogunheper.testcontainers.demo.persistence.repository.ProductRepository;
import com.ogunheper.testcontainers.demo.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ProductMappingService productMappingService() {
        return ProductMappingService.builder().build();
    }

    @Bean
    public ProductCreateService productCreateService(
            ProductMappingService productMappingService,
            ProductRepository productRepository
    ) {
        return ProductCreateService.builder()
                .productMappingService(productMappingService)
                .productRepository(productRepository)
                .build();
    }

    @Bean
    public ProductUploadService productUploadService(ProductRepository productRepository) {
        return ProductUploadService.builder()
                .productRepository(productRepository)
                .build();
    }

    @Bean
    public LocalProductFilterService localProductFilterService(MongoTemplate mongoTemplate) {
        return LocalProductFilterService.builder()
                .mongoTemplate(mongoTemplate)
                .build();
    }

    @Bean
    public MongoProductFilterService mongoProductFilterService(MongoTemplate mongoTemplate) {
        return MongoProductFilterService.builder()
                .mongoTemplate(mongoTemplate)
                .build();
    }
}
