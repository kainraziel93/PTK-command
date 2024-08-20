package com.paytonkawa.commande_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paytonkawa.commande_service.entity.Command;

public interface CommandRepo extends JpaRepository<Command, Integer> {

}
