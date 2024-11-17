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

import com.pruebaTecnica.ecommerce.application.OrderService;
import com.pruebaTecnica.ecommerce.domain.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    @PostMapping
    public Mono<Order> createOrder(@RequestBody Order order) {
        return service.createOrder(order);
    }

    @PutMapping("/{id}")
    public Mono<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return service.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteOrder(@PathVariable Long id) {
        return service.deleteOrder(id);
    }
}
