package com.example.banco.repository;

import com.example.banco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, String> {
}