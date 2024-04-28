package io.github.andresgois.msgateway.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException.FeignClientException;
import io.github.andresgois.msgateway.application.exception.DadosClienteNotFoundException;
import io.github.andresgois.msgateway.application.exception.ErroComunicacaoException;
import io.github.andresgois.msgateway.application.exception.ErroSolicitacaoCartao;
import io.github.andresgois.msgateway.application.representation.Cartao;
import io.github.andresgois.msgateway.application.representation.CartaoAprovado;
import io.github.andresgois.msgateway.application.representation.CartaoCliente;
import io.github.andresgois.msgateway.application.representation.DadosCliente;
import io.github.andresgois.msgateway.application.representation.DadosSolicitacaoEmissaoCartao;
import io.github.andresgois.msgateway.application.representation.ProtocoloSolicitacaoCartao;
import io.github.andresgois.msgateway.application.representation.RetornoAvaliacaoCliente;
import io.github.andresgois.msgateway.application.representation.SituacaoCliente;
import io.github.andresgois.msgateway.infra.clients.CartaoResourceClient;
import io.github.andresgois.msgateway.infra.clients.ClienteResourceClient;
import io.github.andresgois.msgateway.infra.mqqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient resourceClient;
	private final CartaoResourceClient cartaoResourceClient;
	private final SolicitacaoEmissaoCartaoPublisher cartaoPublisher;
	
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

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) 
			throws DadosClienteNotFoundException, ErroComunicacaoException {
		try {
			ResponseEntity<DadosCliente> dadosCliente = resourceClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartaoResourceClient.getCartaoesRendaAteh(renda);
			
			List<Cartao> cartoes = cartoesResponse.getBody();
			List<CartaoAprovado> litsaCartoesAprovados = cartoes.stream().map(c -> {
				BigDecimal limiteBasico = c.getLimiteBasico();
				BigDecimal idadeBd = BigDecimal.valueOf(dadosCliente.getBody().getIdade());
				var fator = idadeBd.divide(BigDecimal.TEN);
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(c.getNome());
				aprovado.setBandeira(c.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(litsaCartoesAprovados);
			
		} catch (FeignClientException e) {
			int status = e.status();
			if(HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoException(e.getMessage(), status);
		}
	}
	
	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
		try {
			cartaoPublisher.solicitarCartao(dados);
			String protocolo = UUID.randomUUID().toString();
			return new ProtocoloSolicitacaoCartao(protocolo);
		} catch (Exception e) {
			throw new ErroSolicitacaoCartao(e.getMessage());
		}
	}
}
