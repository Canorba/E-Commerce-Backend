/**
 * 
 */
package com.pruebaTecnica.ecommerce.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Order {

	@Id
	private Long id;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
}
