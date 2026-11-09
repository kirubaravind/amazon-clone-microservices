package com.amz.order_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record PlaceOrderRequest(@NotEmpty(message="order must have atleast one item")
                                List<@Valid OrderItemRequest> items) {

}
