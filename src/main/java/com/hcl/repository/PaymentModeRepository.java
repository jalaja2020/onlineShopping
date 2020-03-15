package com.hcl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.entity.PaymentMode;

public interface PaymentModeRepository extends JpaRepository<PaymentMode, Integer>{

}
