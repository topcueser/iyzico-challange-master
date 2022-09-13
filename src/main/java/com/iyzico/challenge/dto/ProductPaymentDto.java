package com.iyzico.challenge.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductPaymentDto {

    @NotNull
    @Min(1)
    private Long productId;

    @Min(1)
    private int quantity;

    public ProductPaymentDto() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
