package com.example.cacheperformance.util;

import com.example.cacheperformance.model.Category;
import com.example.cacheperformance.model.Product;
import com.example.cacheperformance.repository.CategoryRepository;
import com.example.cacheperformance.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            System.out.println(">>> Brak kategorii... Tworzenie...");
            categoryRepository.saveAll(List.of(
                    Category.builder().name("Elektronika").build(),
                    Category.builder().name("Dom").build(),
                    Category.builder().name("Ogród").build(),
                    Category.builder().name("Sport").build(),
                    Category.builder().name("Akcesoria").build()
            ));
        } else {
            System.out.println(">>> Kategorie już istnieją w bazie.");
        }

        if (productRepository.count() == 0) {
            System.out.println(">>> Brak produktów... Tworzenie 50 000 rekordów...");
            List<Category> categories = categoryRepository.findAll();
            List<Product> batch = new ArrayList<>();

            for (int i = 1; i <= 50000; i++) {
                batch.add(Product.builder()
                        .name("Product " + i)
                        .description("Opis dla produktu " + i + " dociążający bazę danych...")
                        .price(BigDecimal.valueOf(Math.random() * 1000))
                        .category(categories.get(i % categories.size()))
                        .createdAt(LocalDateTime.now())
                        .build());

                if (i % 1000 == 0) {
                    productRepository.saveAll(batch);
                    batch.clear();
                    System.out.println("Wgrano: " + i);
                }
            }
            System.out.println(">>> Inicjalizacja zakończona pomyślnie!");
        } else {
            System.out.println(">>> Produkty już istnieją w bazie.");
        }
    }
}