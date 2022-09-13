package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.ProductPostDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.ProductAlreadyExistException;
import com.iyzico.challenge.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    List<Product> getAllList();
    Product findById(Long productId) throws ProductNotFoundException;
    Product save(ProductPostDto productPostDto) throws ProductAlreadyExistException;
    Product update(Long id, ProductPostDto productPostDto) throws ProductNotFoundException;
    Product updateStock(Long productId, int stock) throws ProductNotFoundException;
}
