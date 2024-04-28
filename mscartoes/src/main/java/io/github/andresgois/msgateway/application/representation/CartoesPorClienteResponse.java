package io.github.andresgois.msgateway.application.representation;

import java.math.BigDecimal;

import io.github.andresgois.msgateway.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartoesPorClienteResponse fromModel(ClienteCartao model) {
		return new CartoesPorClienteResponse(
				model.getCartao().getNome(), 
				model.getCartao().getBandeira().toString(), 
				model.getCartao().getLimiteBasico());
	}
}
