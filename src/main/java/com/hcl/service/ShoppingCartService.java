package com.hcl.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hcl.dto.CartProductDTO;
import com.hcl.dto.PurchaseHistoryDto;
import com.hcl.dto.SearchProductDto;
import com.hcl.entity.Cart;
import com.hcl.entity.CartProduct;
import com.hcl.entity.DeliveryMode;
import com.hcl.entity.PaymentMode;
import com.hcl.entity.Product;
import com.hcl.entity.Purchase;
import com.hcl.entity.User;
import com.hcl.exceptions.InvalidInputException;
import com.hcl.exceptions.RecordInsertionException;
import com.hcl.exceptions.UserNotFoundException;
import com.hcl.model.ProductCartRequest;
import com.hcl.model.ProductRequest;
import com.hcl.model.PurchaseRequest;
import com.hcl.repository.CartRepository;
import com.hcl.repository.DeliveryModeRepository;
import com.hcl.repository.PaymentModeRepository;
import com.hcl.repository.ProductRepository;
import com.hcl.repository.PurchaseRepository;
import com.hcl.repository.UserRepository;

@Service
public class ShoppingCartService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	PaymentModeRepository paymentModeRepository;
	
	@Autowired
	DeliveryModeRepository deliveryModeRepository;
	
	@Autowired
	PurchaseRepository purchaseRepository;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public User insertUserDetails(User user) {
		return userRepository.save(user);
	}

	public Product insertProductDetails(Product product) {
		return productRepository.save(product);
	}

	public Cart insertCartDetails(Cart cart) {

		return cartRepository.save(cart);
	}


	public List<SearchProductDto> searchProductDetailsByName(String productName){
		List<Product> productList = productRepository.findByProductNameIgnoreCase(productName);
		List<SearchProductDto> productListDto = productList.stream()
				.map(product -> new SearchProductDto(product.getProductId(),product.getProductName(),product.getPrice()))
				.collect(Collectors.toList());

		return productListDto;
	}

	public Cart addElectronicProductsToCart(ProductCartRequest productCartRequest) {
		try {
			Cart cart = new Cart();
			cart.setUserId(productCartRequest.getUserId());
			List<Integer> productIds = productCartRequest.getProdList().stream().map(ProductRequest::getProductId).collect(Collectors.toList());
			List<Product> productList = productRepository.findByProductIdIn(productIds);
			Map<Integer, Integer> prodPricesMap  = productList.stream().collect(Collectors.toMap(Product::getProductId, Product::getPrice));
			List<ProductRequest> productAndQty = productCartRequest.getProdList();
			List<CartProduct> cartProductList = new ArrayList<CartProduct>();
			productAndQty.stream().forEach( prodAndQty ->{
				CartProduct cartProduct = new CartProduct();
				cartProduct.setPrice(prodPricesMap.get(prodAndQty.getProductId())*prodAndQty.getQuantity());
				cartProduct.setProductId(prodAndQty.getProductId());
				cartProduct.setQuantity(prodAndQty.getQuantity());
				cartProductList.add(cartProduct);
			});
			cart.setCartProdcut(cartProductList);
			return  cartRepository.save(cart);
		} catch (Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}

	}

	public Purchase addPurchaseDetailstoCart(PurchaseRequest purcaseRequest) {
		try {
			Purchase purchase = new Purchase();
			Optional<User> findByUserId = userRepository.findById(purcaseRequest.getUserId());
			
			if(!findByUserId.isPresent()) {
				throw new UserNotFoundException("user not found exception");
			}
			
			Optional<Cart> findBycartId = cartRepository.findById(purcaseRequest.getCartId());

			Optional<PaymentMode> findPaymentModeId = paymentModeRepository.findById(purcaseRequest.getPaymentModeId());

			Optional<DeliveryMode> findBydeliveryModeId = deliveryModeRepository.findById(purcaseRequest.getDeliveryModeId());

			if (!findBycartId.isPresent() || !findPaymentModeId.isPresent() || !findBydeliveryModeId.isPresent()) {
				throw new InvalidInputException("Invalid Cart Information");
			} 

			purchase.setPurchaseOn(dateFormat.format(new java.util.Date()));
			
			  purchase.setCart(findBycartId.get());
			  purchase.setPaymentMode(findPaymentModeId.get());
			  purchase.setDeliveryMode(findBydeliveryModeId.get());
			  purchase.setUser(findByUserId.get());
			 
			purchaseRepository.save(purchase);

			
			return purchaseRepository.save(purchase);
			
		}catch(Exception e) {
			throw new RecordInsertionException(e.getMessage());
		}
	}

	public PaymentMode insertPaymentModeDetails(PaymentMode paymentMode) {
		
		return paymentModeRepository.save(paymentMode);
	}

	public DeliveryMode insertDeliveryModeDetails(DeliveryMode deliveryMode) {
		return deliveryModeRepository.save(deliveryMode);
	}

	/*
	 * @Override public PurchaseHistoryDto getUserOrders(Integer userId) {
	 * 
	 * List<Purchase> purchases = new ArrayList<>(); Optional<User> user =
	 * userRepository.findById(userId);
	 * 
	 * if (user.isPresent()) { purchases = purchaseRepository.findbyUserId(userId);
	 * }
	 * 
	 * PurchaseHistoryDto purchaseDetails = new PurchaseHistoryDto();
	 * purchaseDetails.setUserName(user.get().getUserName());
	 * List<PurchaseHistoryDto> purchaseDataList = new ArrayList<>();
	 * purchases.stream().forEach(purchase -> { List<CartProduct> cartProdcuts =
	 * purchase.getCart().getCartProdcut(); cartProducts.stream() .map(cartProd ->
	 * new CartProductDTO( productRepository.findById(cartProd.)
	 * 
	 * });
	 * 
	 * 
	 * return purchaseDetails;
	 * 
	 * }
	 */



}
