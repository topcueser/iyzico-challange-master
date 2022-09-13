package com.iyzico.challenge.service.impl;

import com.iyzico.challenge.dto.ProductPostDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.ProductAlreadyExistException;
import com.iyzico.challenge.exception.ProductNotFoundException;
import com.iyzico.challenge.repository.ProductRepository;
import com.iyzico.challenge.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllList() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Record Not Found"));
    }

    @Override
    public Product save(ProductPostDto productPostDto) throws ProductAlreadyExistException{

        if(!checkProductNameIsExist(productPostDto.getName())) {
            throw new ProductAlreadyExistException("The product already exists!");
        }

        Product saveProduct = new Product();
        saveProduct.setName(productPostDto.getName());
        saveProduct.setDescription(productPostDto.getDescription());
        saveProduct.setStock(productPostDto.getStock());
        saveProduct.setPrice(productPostDto.getPrice());
        return productRepository.save(saveProduct);
    }

    @Override
    public Product update(Long id, ProductPostDto productPostDto) throws ProductNotFoundException {
        Product updateProduct = this.findById(id);
        updateProduct.setName(productPostDto.getName());
        updateProduct.setDescription(productPostDto.getDescription());
        updateProduct.setPrice(productPostDto.getPrice());
        updateProduct.setStock(productPostDto.getStock());
        return productRepository.save(updateProduct);
    }

    @Override
    public Product updateStock(Long productId, int stock) throws ProductNotFoundException {
        Product product = this.findById(productId);
        product.setStock(stock);
        return productRepository.save(product);
    }

    private boolean checkProductNameIsExist(String name) {
        return !productRepository.findByName(name).isPresent();
    }

}
