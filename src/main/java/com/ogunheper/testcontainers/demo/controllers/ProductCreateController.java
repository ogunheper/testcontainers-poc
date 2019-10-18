package com.ogunheper.testcontainers.demo.controllers;

import com.ogunheper.testcontainers.demo.model.CreateProductRequest;
import com.ogunheper.testcontainers.demo.model.IdResponse;
import com.ogunheper.testcontainers.demo.services.ProductCreateService;
import com.ogunheper.testcontainers.demo.services.ProductUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/products")
@RequiredArgsConstructor
public class ProductCreateController {

    private final ProductCreateService productCreateService;
    private final ProductUploadService productUploadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IdResponse<String> create(@RequestBody CreateProductRequest createProductRequest) {
        return productCreateService.create(createProductRequest);
    }

    @PostMapping(path = "/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestParam("count") Integer count) {
        productUploadService.upload(count);
    }
}
