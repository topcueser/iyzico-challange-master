package com.iyzico.challenge;

import com.iyzico.challenge.entity.Product;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestProductUtils {

    public static Product createProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Mouse");
        product.setDescription("Apple Magic Mouse");
        product.setStock(99);
        product.setPrice(new BigDecimal(1999));
        return product;
    }

    public static List<Product> createProductList() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Mouse");
        product1.setDescription("Apple Magic Mouse");
        product1.setStock(99);
        product1.setPrice(new BigDecimal(1999));

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Drone");
        product2.setDescription("Drone DJI Mini 2 Fly");
        product2.setStock(99);
        product2.setPrice(new BigDecimal(14299));

        return Arrays.asList(product1, product2);
    }

}
