/**
 * 
 */
package com.pruebaTecnica.ecommerce.adapters.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pruebaTecnica.ecommerce.domain.Product;

/**
 * 
 */
public interface ProductRepository extends ReactiveCrudRepository<Product, Long>{

}
