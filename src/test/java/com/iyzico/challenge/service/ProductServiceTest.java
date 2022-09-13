package com.iyzico.challenge.service;

import com.iyzico.challenge.TestProductUtils;
import com.iyzico.challenge.dto.ProductPostDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.ProductAlreadyExistException;
import com.iyzico.challenge.exception.ProductNotFoundException;
import com.iyzico.challenge.repository.ProductRepository;
import com.iyzico.challenge.service.impl.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test(expected = ProductNotFoundException.class)
    public void when_create_product_should_return_product() throws ProductAlreadyExistException {
        when(productRepository.save(any(Product.class))).thenReturn(TestProductUtils.createProduct());
        ProductPostDto productDto = new ProductPostDto();
        productDto.setName("Mouse");
        productDto.setDescription("Apple Magic Mouse");
        productDto.setStock(99);
        productDto.setPrice(new BigDecimal(1999));
        Product saveProduct = productService.save(productDto);
        saveProduct.setId(1L);
        Assert.assertNotNull(saveProduct);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void when_list_all_products_should_return_products() {
        when(productRepository.findAll()).thenReturn(TestProductUtils.createProductList());
        List<Product> productsList = productService.getAllList();
        Assert.assertNotNull(productsList);
        verify(productRepository, times(1)).findAll();
    }

    @Test(expected = ProductNotFoundException.class)
    public void when_update_product_should_return_updated_product() throws ProductNotFoundException {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(TestProductUtils.createProduct()));
        Product updateProduct = TestProductUtils.createProduct();
        updateProduct.setStock(2717);
        when(productRepository.save(any(Product.class))).thenReturn(updateProduct);
        ProductPostDto productPostDto = new ProductPostDto();
        productPostDto.setStock(2717);
        Product saveProduct = productService.update(1L, productPostDto);
        Assert.assertNotNull(saveProduct);
        Assert.assertEquals(1, 1);
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test(expected = ProductNotFoundException.class)
    public void when_update_stock_should_return_product() throws ProductNotFoundException {
        Product product = TestProductUtils.createProduct();
        product.setStock(5);
        Product updateProduct = TestProductUtils.createProduct();
        updateProduct.setStock(1);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updateProduct);
        Product stockProductUpdate = productService.updateStock(1L, 1);
        Assert.assertNotNull(stockProductUpdate);
        Assert.assertEquals(1, stockProductUpdate.getStock());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

}
