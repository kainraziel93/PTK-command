package com.paytonkawa.commande_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paytonkawa.commande_service.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
