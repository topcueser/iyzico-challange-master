package com.iyzico.challenge.controller;

import com.iyzico.challenge.dto.ProductPostDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.ProductAlreadyExistException;
import com.iyzico.challenge.exception.ProductNotFoundException;
import com.iyzico.challenge.response.ResponseHandler;
import com.iyzico.challenge.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllList() {
        List<Product> products = productService.getAllList();
        return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, products);
    }

    @PostMapping
    public ResponseEntity<Object> saveNewProduct(@Valid @RequestBody ProductPostDto productPostDto) throws ProductAlreadyExistException {
        Product saveProduct = productService.save(productPostDto);
        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.CREATED, saveProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductPostDto productPostDto) throws ProductNotFoundException {
        Product updateProduct = productService.update(id, productPostDto);
        return ResponseHandler.generateResponse("Updated!", HttpStatus.OK, updateProduct);
    }

}
