package com.solutis.apirestSolutis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solutis.apirestSolutis.models.Conta;

/*
 * Motivo de criação da interface:
 * Maneira para facilitar a implementação do código. O JpaRepository já possui vários métodos
 * prontos para fazer persistência no banco de dados. Então, métodos como save, delete, findAll,
 * findById e entre outros, estão contidos nesta classe. Basta criar apenas uma instância dessa
 * interface e utilizar os métodos no nosso Model Conta. Não será necessário criar DAO ou
 * classe Transaction.
 * */
public interface ContaRepository extends JpaRepository<Conta, Long> {
	Conta findById(long idConta);
}
