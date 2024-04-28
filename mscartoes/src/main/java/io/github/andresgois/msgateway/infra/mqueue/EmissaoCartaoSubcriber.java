package io.github.andresgois.msgateway.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCartaoSubcriber {

	@RabbitListener(queues = "${mq.queue.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		System.err.println(payload);
	}
}
