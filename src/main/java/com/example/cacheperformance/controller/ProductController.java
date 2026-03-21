package com.example.cacheperformance.controller;

import com.example.cacheperformance.model.ProductDTO;
import com.example.cacheperformance.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/no-cache/{id}")
    public ProductDTO getProductNoCache(@PathVariable Long id) {
        return productService.getProductFromDb(id);
    }

    @GetMapping("/local-cache/{id}")
    public ProductDTO getProductLocalCache(@PathVariable Long id) {
        return productService.getProductLocalCache(id);
    }

    @GetMapping("/redis-cache/{id}")
    public ProductDTO getProductRedisCache(@PathVariable Long id) {
        return productService.getProductDistributedCache(id);
    }

    @GetMapping("/no-cache/category/{categoryName}")
    public List<ProductDTO> getProductsByCategoryNoCache(@PathVariable String categoryName) {
        return productService.getProductsByCategoryFromDb(categoryName);
    }

    @GetMapping("/local-cache/category/{categoryName}")
    public List<ProductDTO> getProductsByCategoryLocalCache(@PathVariable String categoryName) {
        return productService.getProductsByCategoryLocalCache(categoryName);
    }

    @GetMapping("/redis-cache/category/{categoryName}")
    public List<ProductDTO> getProductsByCategoryRedisCache(@PathVariable String categoryName) {
        return productService.getProductsByCategoryDistributedCache(categoryName);
    }
}
