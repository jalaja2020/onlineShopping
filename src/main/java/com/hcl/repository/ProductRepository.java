package com.hcl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.entity.Product;
import java.lang.String;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	//List<Product> findByProductName(String productname);
	List<Product> findByProductNameIgnoreCase( String productName);

	List<Product> findByProductIdIn(List<Integer> iProdIds);
}
