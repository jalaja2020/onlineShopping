package com.hcl.model;

import java.util.List;

import lombok.Data;

@Data
public class ProductCartRequest {
	private int userId;
	private List<ProductRequest> prodList;
}
