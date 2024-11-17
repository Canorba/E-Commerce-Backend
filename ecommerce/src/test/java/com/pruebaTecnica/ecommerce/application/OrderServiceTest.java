/**
 * 
 */
package com.pruebaTecnica.ecommerce.application;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.pruebaTecnica.ecommerce.adapters.persistence.OrderRepository;
import com.pruebaTecnica.ecommerce.domain.Order;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * 
 */
public class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	private Order order;

	@BeforeEach
	public void setUp() {
		order = new Order(1L, 1L, 2, 200.0);
	}

	@Test
	public void testGetOrderById() {
		// Arrange
		when(orderRepository.findById(1L)).thenReturn(Mono.just(order));

		// Act & Assert
		StepVerifier.create(orderService.getOrderById(1L)).expectNextMatches(o -> o.getQuantity() == 2)
				.verifyComplete();

		verify(orderRepository, times(1)).findById(1L);
	}

	@Test
	public void testCreateOrder() {
		// Arrange
		when(orderRepository.save(order)).thenReturn(Mono.just(order));

		// Act & Assert
		StepVerifier.create(orderService.createOrder(order)).expectNextMatches(o -> o.getQuantity() == 2)
				.verifyComplete();

		verify(orderRepository, times(1)).save(order);
	}

	@Test
	public void testUpdateOrder() {
		// Arrange
		Order updatedOrder = new Order(1L, 1L, 3, 300.0);
		when(orderRepository.findById(1L)).thenReturn(Mono.just(order));
		when(orderRepository.save(updatedOrder)).thenReturn(Mono.just(updatedOrder));

		// Act & Assert
		StepVerifier.create(orderService.updateOrder(1L, updatedOrder)).expectNextMatches(o -> o.getQuantity() == 3)
				.verifyComplete();

		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepository, times(1)).save(updatedOrder);
	}

	@Test
	public void testDeleteOrder() {
		// Arrange
		when(orderRepository.deleteById(1L)).thenReturn(Mono.empty());

		// Act & Assert
		StepVerifier.create(orderService.deleteOrder(1L)).verifyComplete();

		verify(orderRepository, times(1)).deleteById(1L);
	}
}
