package com.iyzico.challenge.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iyzico.challenge.TestProductUtils;
import com.iyzico.challenge.dto.ProductPostDto;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.service.ProductService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    private ProductService productService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private static final String baseURL = "/api/products";

    @Before
    public void init() {
        productService = mock(ProductService.class);
        objectMapper = new ObjectMapper();
        ProductController productController = new ProductController(productService);
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setControllerAdvice(new Exception()).build();

    }

    @Test
    public void when_get_all_list_then_return_all_products() throws Exception {
        when(productService.getAllList()).thenReturn(TestProductUtils.createProductList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseURL)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject convertJsonArray = new JSONObject(responseString);

        List<Product> productsList = objectMapper.readValue(convertJsonArray.getJSONArray("data").toString(),
                new TypeReference<List<Product>>() {
                });
        Assert.assertNotNull(productsList);
        Assert.assertEquals(2, productsList.size());
        verify(productService, times(1)).getAllList();
    }

    @Test
    public void when_create_product_then_return_product() throws Exception{

        Product createProduct = TestProductUtils.createProduct();

        when(productService.save(any())).thenReturn(createProduct);

        ProductPostDto productPostDto = new ProductPostDto();
        productPostDto.setName(createProduct.getName());
        productPostDto.setDescription(createProduct.getDescription());
        productPostDto.setPrice(createProduct.getPrice());
        productPostDto.setStock(createProduct.getStock());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productPostDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject convertJsonArray = new JSONObject(responseString);

        Product product = objectMapper.readValue(convertJsonArray.getJSONObject("data").toString(),
                new TypeReference<Product>() {
                });

        Assert.assertNotNull(product);
        Assert.assertEquals(productPostDto.getName(), product.getName());
        Assert.assertEquals(productPostDto.getStock(), product.getStock());
        Assert.assertEquals(productPostDto.getDescription(), product.getDescription());
        Assert.assertEquals(productPostDto.getPrice(), product.getPrice());
        verify(productService, times(1)).save(any());

    }

    @Test
    public void when_update_product_then_return_product() throws Exception{
        Product updateProduct = TestProductUtils.createProduct();
        when(productService.update(anyLong(), any())).thenReturn(updateProduct);

        ProductPostDto productPostDto = new ProductPostDto();
        productPostDto.setName(updateProduct.getName());
        productPostDto.setDescription(updateProduct.getDescription());
        productPostDto.setPrice(updateProduct.getPrice());
        productPostDto.setStock(updateProduct.getStock());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseURL + "/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productPostDto)))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        JSONObject convertJsonArray = new JSONObject(responseString);

        Product product = objectMapper.readValue(convertJsonArray.getJSONObject("data").toString(),
                new TypeReference<Product>() {
                });

        Assert.assertNotNull(product);
        Assert.assertEquals(productPostDto.getName(), product.getName());
        Assert.assertEquals(productPostDto.getStock(), product.getStock());
        Assert.assertEquals(productPostDto.getDescription(), product.getDescription());
        Assert.assertEquals(productPostDto.getPrice(), product.getPrice());
        verify(productService, times(1)).update(anyLong(), any());
    }


}
