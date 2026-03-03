package com.example.cacheperformance.service;

import com.example.cacheperformance.model.Product;
import com.example.cacheperformance.model.ProductDTO;
import com.example.cacheperformance.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO getProductFromDb(Long id) {
        Product product = productRepository.findByIdWithCategory(id).
                orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o ID: " + id));

        return ProductDTO.from(product);
    }

    @Cacheable(value = "products_local", key = "#id", cacheManager = "localCacheManager")
    @Transactional(readOnly = true)
    public ProductDTO getProductLocalCache(Long id) {
        System.out.println(">>> (LOKALNY) Pobieranie produktu o ID " + id + " prosto z bazy...");
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o ID: " + id));

        return ProductDTO.from(product);
    }

    @Cacheable(value = "products_redis", key = "#id", cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public ProductDTO getProductRedisCache(Long id) {
        System.out.println(">>> (REDIS) Pobieranie produktu o ID " + id + " prosto z bazy...");
        Product product = productRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o ID: " + id));
        return ProductDTO.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryFromDb(String categoryName) {
        List<Product> products = productRepository.findAllByCategoryName(categoryName);
        return products.stream().map(ProductDTO::from).toList();
    }

    @Cacheable(value = "products_local_list", key = "#categoryName", cacheManager = "localCacheManager")
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryLocalCache(String categoryName) {
        System.out.println(">>> (LOKALNY-LISTA) Pobieranie z bazy produktów z kategorii: " + categoryName);
        List<Product> products = productRepository.findAllByCategoryName(categoryName);
        return products.stream().map(ProductDTO::from).toList();
    }

    @Cacheable(value = "products_redis_list", key = "#categoryName", cacheManager = "redisCacheManager")
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByCategoryRedisCache(String categoryName) {
        System.out.println(">>> (REDIS-LISTA) Pobieranie z bazy produktów z kategorii: " + categoryName);
        List<Product> products = productRepository.findAllByCategoryName(categoryName);
        return products.stream().map(ProductDTO::from).toList();
    }
}
