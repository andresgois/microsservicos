package io.github.andresgois.msgateway.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException.FeignClientException;
import io.github.andresgois.msgateway.application.exception.DadosClienteNotFoundException;
import io.github.andresgois.msgateway.application.exception.ErroComunicacaoException;
import io.github.andresgois.msgateway.application.representation.CartaoCliente;
import io.github.andresgois.msgateway.application.representation.DadosCliente;
import io.github.andresgois.msgateway.application.representation.SituacaoCliente;
import io.github.andresgois.msgateway.infra.clients.CartaoResourceClient;
import io.github.andresgois.msgateway.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient resourceClient;
	private final CartaoResourceClient cartaoResourceClient;
	
	public SituacaoCliente obeterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoException {
		// Obter dados do cliente - MSCLIENTE
		try {
			ResponseEntity<DadosCliente> dadosCliente = resourceClient.dadosCliente(cpf);
			// Obter cartoes do cliente - MSCARTOES
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartaoResourceClient.getCartoesPorCliente(cpf);
			
			return SituacaoCliente
					.builder()
					.cliente(dadosCliente.getBody())
					.cartoes(cartoesResponse.getBody())
					.build();
		} catch (FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoException(e.getMessage(), status);
		}
	}
	
	
}
