package com.amz.cart_service.dto;

public record ProductResponse(Long id, String name, Double price, Integer stockQuantity) {
}
