package com.pruebaTecnica.ecommerce.application;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pruebaTecnica.ecommerce.adapters.persistence.ProductRepository;
import com.pruebaTecnica.ecommerce.domain.Product;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	private Product product;

	@BeforeEach
	public void setUp() {
		product = new Product(1L, "Product 1", "Description of Product 1", 100.0);
	}

	@Test
	public void testGetProductById() {
		// Arrange
		when(productRepository.findById(1L)).thenReturn(Mono.just(product));

		// Act & Assert
		StepVerifier.create(productService.getProductById(1L)).expectNextMatches(p -> p.getName().equals("Product 1"))
				.verifyComplete();

		verify(productRepository, times(1)).findById(1L);
	}

	@Test
	public void testCreateProduct() {
		// Arrange
		when(productRepository.save(product)).thenReturn(Mono.just(product));

		// Act & Assert
		StepVerifier.create(productService.createProduct(product))
				.expectNextMatches(p -> p.getName().equals("Product 1")).verifyComplete();

		verify(productRepository, times(1)).save(product);
	}

	@Test
	public void testUpdateProduct() {
		// Arrange
		Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 120.0);
		when(productRepository.findById(1L)).thenReturn(Mono.just(product));
		when(productRepository.save(updatedProduct)).thenReturn(Mono.just(updatedProduct));

		// Act & Assertx
		StepVerifier.create(productService.updateProduct(1L, updatedProduct))
				.expectNextMatches(p -> p.getName().equals("Updated Product")).verifyComplete();

		verify(productRepository, times(1)).findById(1L);
		verify(productRepository, times(1)).save(updatedProduct);
	}

	@Test
	public void testDeleteProduct() {
		// Arrange
		when(productRepository.deleteById(1L)).thenReturn(Mono.empty());

		// Act & Assert
		StepVerifier.create(productService.deleteProduct(1L)).verifyComplete();

		verify(productRepository, times(1)).deleteById(1L);
	}
}
