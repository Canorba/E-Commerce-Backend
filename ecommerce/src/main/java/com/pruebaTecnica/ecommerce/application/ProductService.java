/**
 * 
 */
package com.pruebaTecnica.ecommerce.application;

import org.springframework.stereotype.Service;

import com.pruebaTecnica.ecommerce.adapters.persistence.ProductRepository;
import com.pruebaTecnica.ecommerce.domain.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 */
@Service
public class ProductService {

	 private final ProductRepository repository;

	    public ProductService(ProductRepository repository) {
	        this.repository = repository;
	    }

	    public Flux<Product> getAllProducts() {
	        return repository.findAll();
	    }

	    public Mono<Product> getProductById(Long id) {
	        return repository.findById(id);
	    }

	    public Mono<Product> createProduct(Product product) {
	        return repository.save(product);
	    }

	    public Mono<Product> updateProduct(Long id, Product updatedProduct) {
	        return repository.findById(id)
	                .flatMap(existingProduct -> {
	                    existingProduct.setName(updatedProduct.getName());
	                    existingProduct.setDescription(updatedProduct.getDescription());
	                    existingProduct.setPrice(updatedProduct.getPrice());
	                    return repository.save(existingProduct);
	                });
	    }

	    public Mono<Void> deleteProduct(Long id) {
	        return repository.deleteById(id);
	    }
}
