package io.github.andresgois.msgateway.application.representation;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartaoAprovado {

	private String cartao;
	private String bandeira;
	private BigDecimal limiteAprovado;
}
