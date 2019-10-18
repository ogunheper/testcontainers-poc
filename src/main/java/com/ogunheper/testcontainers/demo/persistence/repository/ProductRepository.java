package com.ogunheper.testcontainers.demo.persistence.repository;

import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
