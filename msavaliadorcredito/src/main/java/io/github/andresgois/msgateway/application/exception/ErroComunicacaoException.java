package io.github.andresgois.msgateway.application.exception;

import lombok.Getter;

public class ErroComunicacaoException extends Exception{

	private static final long serialVersionUID = 1L;
	@Getter
	private Integer status;

	public ErroComunicacaoException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}
}
