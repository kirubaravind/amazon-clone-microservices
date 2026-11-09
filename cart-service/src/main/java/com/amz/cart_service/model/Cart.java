package com.amz.cart_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name="cart_items")
@Data
public class Cart {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

@NotBlank
    private String username;

private Long productId;

@Positive(message="Quantity must be greater than 0")
    private Integer quantity;
}
