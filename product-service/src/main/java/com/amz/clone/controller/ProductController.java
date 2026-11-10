package com.amz.clone.controller;

import com.amz.clone.model.Product;
import com.amz.clone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/products")
public class ProductController {
@Autowired
    private ProductService productService;

@PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
    return new ResponseEntity<Product>(productService.createProduct(product), HttpStatus.CREATED);
}

@GetMapping
    public PagedModel<Product> getAllProducts(@PageableDefault(size = 10, sort="id")Pageable pageable){
    return new PagedModel<>(productService.getAllProducts(pageable));
}

@GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
    return ResponseEntity.ok(productService.getProductByID(id));
    }
@PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product) {
    return ResponseEntity.ok(productService.updateProduct(id, product));
}
@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
}




}
