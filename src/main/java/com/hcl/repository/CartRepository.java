package com.hcl.repository;

import org.springframework.data.repository.CrudRepository;

import com.hcl.entity.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer> {

}
