package com.ogunheper.testcontainers.demo.services;

import com.ogunheper.testcontainers.demo.persistence.domain.Product;

import java.util.Date;
import java.util.List;

public interface ProductFilterService {

    List<Product> filter(Date availableAt);
}
