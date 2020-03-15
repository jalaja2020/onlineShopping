package com.hcl.dto;

import lombok.Data;

@Data
public class CartProductDTO {
	private String productName;
	private Integer quantity;
	private Double price;
}
