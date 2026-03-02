package com.example.cacheperformance.service;

import com.example.cacheperformance.model.Product;
import com.example.cacheperformance.model.ProductDTO;
import com.example.cacheperformance.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO getProductFromDb(Long id) {
        Product product = productRepository.findByIdWithCategory(id).orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o ID: " + id));

        return ProductDTO.from(product);
    }

}
