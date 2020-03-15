package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.SearchProductDto;
import com.hcl.entity.Cart;
import com.hcl.entity.DeliveryMode;
import com.hcl.entity.PaymentMode;
import com.hcl.entity.Product;
import com.hcl.entity.Purchase;
import com.hcl.entity.User;
import com.hcl.model.ProductCartRequest;
import com.hcl.model.PurchaseRequest;
import com.hcl.service.ShoppingCartService;

@RestController
public class ShoppingcartController {
	
	@Autowired
	ShoppingCartService shoppingCartService;
	@GetMapping("/test")
	public String shopping() {
		return "hi";
	}
	@PostMapping("/UserDetails")
	public ResponseEntity<User> insertUserDetails(@RequestBody User user)
	{
		return new ResponseEntity<User>(shoppingCartService.insertUserDetails(user),HttpStatus.OK);
	}
	
	@PostMapping("/ProductDetails")
	public ResponseEntity<Product> insertProductDetails(@RequestBody Product product)
	{
		return new ResponseEntity<Product>( shoppingCartService.insertProductDetails(product),HttpStatus.OK);
	}
	
	@PostMapping("/CartDetails")
	public ResponseEntity<Cart> insertCartDetails(@RequestBody Cart cart)
	{
		return new ResponseEntity<Cart>( shoppingCartService.insertCartDetails(cart),HttpStatus.OK);
	}
	
	@PostMapping("/paymentModeDetails")
	public ResponseEntity<PaymentMode> insertCartDetails(@RequestBody PaymentMode paymentMode)
	{
		return new ResponseEntity<PaymentMode>( shoppingCartService.insertPaymentModeDetails(paymentMode),HttpStatus.OK);
	}
	
	@PostMapping("/deliveryModeDetails")
	public ResponseEntity<DeliveryMode> insertCartDetails(@RequestBody DeliveryMode deliveryMode)
	{
		return new ResponseEntity<DeliveryMode>( shoppingCartService.insertDeliveryModeDetails(deliveryMode),HttpStatus.OK);
	}
	
	
	@GetMapping("/searchProducts/{productName}")
	public ResponseEntity<List<SearchProductDto>> searchProductDetails(@PathVariable("productName") String productName)
	{
		return new ResponseEntity<List<SearchProductDto>>(shoppingCartService.searchProductDetailsByName(productName),HttpStatus.OK);
	}
	

	@PostMapping("/productCart")
	public ResponseEntity<Cart> purchageCartProducts(@RequestBody ProductCartRequest request) {
		Cart addElectronicProductsToCart = shoppingCartService.addElectronicProductsToCart(request);
		return new ResponseEntity<Cart>(addElectronicProductsToCart, HttpStatus.OK);
	}
	
	@PostMapping("/purchageCart")
	public ResponseEntity<Purchase> purchageCartProducts(@RequestBody PurchaseRequest purcaseRequest) {
		Purchase addPurchaseDetailstoCart = shoppingCartService.addPurchaseDetailstoCart(purcaseRequest);
		return new ResponseEntity<Purchase>(addPurchaseDetailstoCart, HttpStatus.OK);
	}

	
	/*
	 * @GetMapping(value = { "/purchaseHistory/{userId}" }) public
	 * ResponseEntity<PurchaseHistoryDto> getUserOrders(@PathVariable(name =
	 * "userId") Integer userId) { return new
	 * ResponseEntity<>(shoppingCartService.getUserOrders(userId), HttpStatus.OK);
	 * 
	 * }
	 * 
	 */

}
