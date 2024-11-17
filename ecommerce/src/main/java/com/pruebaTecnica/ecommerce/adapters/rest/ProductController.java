/**
 * 
 */
package com.pruebaTecnica.ecommerce.adapters.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebaTecnica.ecommerce.application.ProductService;
import com.pruebaTecnica.ecommerce.domain.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
}
