package com.amz.cart_service.controller;

import com.amz.cart_service.dto.AddToCartRequest;
import com.amz.cart_service.model.Cart;
import com.amz.cart_service.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {
@Autowired
    private CartService cartService;

@PostMapping
    public ResponseEntity<Cart> addToCart(@Valid @RequestBody AddToCartRequest request, Authentication authenthentication){
      Cart saved =  cartService.addToCart(authenthentication.getName(), request.productId(), request.quantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}

@GetMapping
    public List<Cart>  getCart(Authentication authentication){
    return  cartService.getCart(authentication.getName());
}

@DeleteMapping("/{itemId}")
public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId, Authentication authentication){
    cartService.removeFromCart(authentication.getName(), itemId);
    return ResponseEntity.noContent().build();
}

}
