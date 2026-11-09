package com.amz.cart_service.service;

import com.amz.cart_service.dto.ProductResponse;
import com.amz.cart_service.exception.CartItemNotFoundException;
import com.amz.cart_service.exception.InsufficientStockException;
import com.amz.cart_service.exception.ProductNotFoundException;
import com.amz.cart_service.model.Cart;
import com.amz.cart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RestClient productRestClient;

    public Cart addToCart(String username, Long productId, Integer quantity){
        ProductResponse product=fetchProduct(productId);

        Cart item = cartRepository.findByUsernameAndProductId(username, productId)
                .orElseGet(() -> {
                    Cart newItem = new Cart();
                    newItem.setUsername(username);
                    newItem.setProductId(productId);
                    newItem.setQuantity(0);
                    return newItem;
                });

        int newQuantity=item.getQuantity()+quantity;
        if(product.stockQuantity()<newQuantity){
            throw new InsufficientStockException(
                    "Only " + product.stockQuantity()+" in stock for product"+productId);
        }
        item.setQuantity(newQuantity);
        return cartRepository.save(item);

    }

    public List<Cart> getCart(String username){
        return cartRepository.findByUsername(username);

    }

    public void removeFromCart(String username,Long itemId){
        Cart item = cartRepository.findById(itemId)
                .orElseThrow(()->new CartItemNotFoundException("Cart item not found: " + itemId));
            if(!item.getUsername().equals(username)){
                throw new CartItemNotFoundException("Cart item not found "+itemId);
            }
            cartRepository.delete(item);
    }


    private ProductResponse fetchProduct(Long productId){

        try{
            return productRestClient.get()
                    .uri("/api/products/{id}",productId)
                    .retrieve()
                    .body(ProductResponse.class);
        }
        catch(HttpClientErrorException.NotFound e){
            throw new ProductNotFoundException("Product not found with id "+productId);
        }
    }


}
