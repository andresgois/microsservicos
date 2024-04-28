package io.github.andresgois.msgateway.application.representation;

import java.math.BigDecimal;

import io.github.andresgois.msgateway.domain.Cartao;
import io.github.andresgois.msgateway.domain.enums.BandeiraCartao;
import lombok.Data;

@Data
public class CartaoSaveRequest {

	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limiteBasico;
	
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limiteBasico);
	}
}
