package com.hcl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
  List<Purchase>  findbyUserId(int userid);
}
