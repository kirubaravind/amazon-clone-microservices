package com.amz.cart_service.dto;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
public record AddToCartRequest(@NotNull(message="ProductId is required") Long productId,
                               @NotNull(message="Quantity is required")
                               @Positive(message="Quantity must be greater than zero") Integer quantity) {}

