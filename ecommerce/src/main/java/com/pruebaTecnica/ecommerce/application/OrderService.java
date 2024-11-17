/**
 * 
 */
package com.pruebaTecnica.ecommerce.application;

import org.springframework.stereotype.Service;

import com.pruebaTecnica.ecommerce.adapters.persistence.OrderRepository;
import com.pruebaTecnica.ecommerce.domain.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 */
@Service
public class OrderService {

	private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Flux<Order> getAllOrders() {
        return repository.findAll();
    }

    public Mono<Order> getOrderById(Long id) {
        return repository.findById(id);
    }

    public Mono<Order> createOrder(Order order) {
        return repository.save(order);
    }

    public Mono<Order> updateOrder(Long id, Order updatedOrder) {
        return repository.findById(id)
                .flatMap(existingOrder -> {
                    existingOrder.setProductId(updatedOrder.getProductId());
                    existingOrder.setQuantity(updatedOrder.getQuantity());
                    existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
                    return repository.save(existingOrder);
                });
    }

    public Mono<Void> deleteOrder(Long id) {
        return repository.deleteById(id);
    }
}
