package io.github.andresgois.msgateway.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.andresgois.msgateway.domain.Cartao;
import io.github.andresgois.msgateway.domain.ClienteCartao;
import io.github.andresgois.msgateway.domain.DadosSolicitacaoEmissaoCartao;
import io.github.andresgois.msgateway.infra.repository.CartaoRepository;
import io.github.andresgois.msgateway.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubcriber {

	private final CartaoRepository cartaoRepository;
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	// Ouvinte
	@RabbitListener(queues = "${mq.queue.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		try {
			System.out.println(payload);
			ObjectMapper mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper
					.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
