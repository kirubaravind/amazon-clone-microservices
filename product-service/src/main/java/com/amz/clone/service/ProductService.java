package com.amz.clone.service;

import com.amz.clone.exception.ProductNotFoundException;
import com.amz.clone.model.Product;
import com.amz.clone.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @Cacheable(value = "products", key = "#id")
    public Product getProductByID(Long id){
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found with id:"+id));
    }
    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    @CachePut(value = "products", key = "#id")
    public Product updateProduct(Long id, Product updatedProduct){
        Product existing=getProductByID(id);
        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setDescription(updatedProduct.getDescription());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        return productRepository.save(existing);
    }
}
