package io.github.andresgois.msgateway.application.exception;

public class ErroSolicitacaoCartao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ErroSolicitacaoCartao(String msg) {
		super(msg);
	}
}
