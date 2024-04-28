package io.github.andresgois.msgateway.application.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetornoAvaliacaoCliente {

	private List<CartaoAprovado> aprovado;
}
