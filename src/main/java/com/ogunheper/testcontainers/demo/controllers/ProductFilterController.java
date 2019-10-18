package com.ogunheper.testcontainers.demo.controllers;

import com.ogunheper.testcontainers.demo.persistence.domain.Product;
import com.ogunheper.testcontainers.demo.services.LocalProductFilterService;
import com.ogunheper.testcontainers.demo.services.MongoProductFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductFilterController {

    private final LocalProductFilterService localProductFilterService;
    private final MongoProductFilterService mongoProductFilterService;

    @GetMapping(params = "filter=local")
    public List<Product> localFilter(
            @RequestParam(name = "availableAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date availableAt
    ) {
        return localProductFilterService.filter(
                availableAt
        );
    }

    @GetMapping(params = "filter=mongo")
    public List<Product> mongoFilter(
            @RequestParam(name = "availableAt", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date availableAt
    ) {
        return mongoProductFilterService.filter(
                availableAt
        );
    }
}
