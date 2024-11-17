/**
 * 
 */
package com.pruebaTecnica.ecommerce.adapters.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.pruebaTecnica.ecommerce.domain.Order;

/**
 * 
 */
public interface OrderRepository extends ReactiveCrudRepository<Order, Long>{

}
