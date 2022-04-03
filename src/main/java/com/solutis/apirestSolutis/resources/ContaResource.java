package com.solutis.apirestSolutis.resources;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutis.apirestSolutis.models.Conta;
import com.solutis.apirestSolutis.models.Saldo;
import com.solutis.apirestSolutis.repository.ContaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController // Classe que vai receber as requisições http
@RequestMapping(value = "/api") // URI padrão da API
@Api(value = "API REST Contas")
@CrossOrigin(origins = "*") // Qualquer domínio pode acessar essa aplicação
public class ContaResource {
	@Autowired
	ContaRepository contaRepository;

	@GetMapping("/contas")
	@ApiOperation(value = "Retorna uma lista de contas")
	public List<Conta> listaContas() {
		return this.contaRepository.findAll();
	}

	@GetMapping("/conta/{idConta}")
	@ApiOperation(value = "Retorna uma conta")
	public Conta listaContaUnica(@PathVariable(value = "idConta") long idConta) {
		return this.contaRepository.findById(idConta);
	}

	@PostMapping("/conta")
	@ApiOperation(value = "Salva uma conta")
	public void salvaConta(@RequestBody Conta conta) { // Anotação necessária, pois vai receber o
														// JSON no corpo da requisição
		if (conta.getCiclo().equals("Parcelada")) {
			Calendar c = Calendar.getInstance();
			for (int i = 1; i <= conta.getParcelas(); i++) {
				c.add(Calendar.DATE, +30);
				Conta contaParcelada = new Conta();
				contaParcelada.setDescricao(conta.getDescricao());
				contaParcelada.setTipo(conta.getTipo());
				contaParcelada.setCiclo(conta.getCiclo());
				contaParcelada.setParcelas(i);
				contaParcelada.setValor(conta.getValor().divide(BigDecimal.valueOf(conta.getParcelas())));
				contaParcelada.setData(c.getTime());
				this.contaRepository.save(contaParcelada);
			}
		} else {
			this.contaRepository.save(conta);
		}
	}

	@DeleteMapping("/conta/{idConta}")
	@ApiOperation(value = "Deleta uma conta")
	public void deletaConta(@RequestBody @PathVariable(value = "idConta") Conta conta) { // Anotação necessária, pois
																							// vai receber o JSON no
		// corpo da requisição
		this.contaRepository.delete(conta);
	}

	@PutMapping("/conta")
	@ApiOperation(value = "Atualiza uma conta")
	public Conta atualizaConta(@RequestBody Conta conta) { // Anotação necessária, pois vai receber o
															// JSON no corpo da requisição
		return this.contaRepository.save(conta);
	}

	@GetMapping("/conta")
	@ApiOperation(value = "Saldo de contas a pagar - contas a receber")
	public Saldo saldoConta() {
		List<Conta> contas = this.contaRepository.findAll();
		Saldo saldo = new Saldo();
		float valor = 0;
		for (Conta listaConta : contas) {
			if (listaConta.getTipo().equals("Receber")) {
				valor = valor + listaConta.getValor().floatValue();
			}
			if (listaConta.getTipo().equals("Pagar")) {
				valor = valor - listaConta.getValor().floatValue();
			}
		}
		saldo.setSaldo(BigDecimal.valueOf(valor));
		saldo.setContas(contas);
		return saldo;
	}
}
