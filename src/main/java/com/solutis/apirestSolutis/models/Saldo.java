package com.solutis.apirestSolutis.models;

import java.math.BigDecimal;
import java.util.List;

public class Saldo {
	private BigDecimal saldo;
	private List<Conta> contas;

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

}
