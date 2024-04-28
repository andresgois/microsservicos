package io.github.andresgois.msgateway.infra.mqqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.andresgois.msgateway.application.representation.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final Queue queueEmissaoCartoes; // referente a esse BEan : MQConfig
	
	// faz a publicação
	public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		 String json = convertIntoJson(dados);
		 rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
	}
	// Converte a entidade para String e envia-lo ao RabbitMQ
	private String convertIntoJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dados);
	}
	
	
}
