package io.github.andresgois.msgateway.application.representation;

import lombok.Data;

@Data
public class DadosCliente {

	private Long id;
	private String cpf;
	private String nome;
	private Integer idade;
}
