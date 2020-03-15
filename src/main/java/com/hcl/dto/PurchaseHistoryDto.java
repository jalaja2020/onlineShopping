package com.hcl.dto;

import java.util.List;

import lombok.Data;

@Data
public class PurchaseHistoryDto {

	private String orderPlacedOn;
	private String paymentMode;
	private String deliveryMode;
	private String userName;
	private List<CartProductDTO> cartProducts;

}
