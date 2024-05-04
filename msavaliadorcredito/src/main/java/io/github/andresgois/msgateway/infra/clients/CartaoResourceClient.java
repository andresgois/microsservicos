package io.github.andresgois.msgateway.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.andresgois.msgateway.application.representation.Cartao;
import io.github.andresgois.msgateway.application.representation.CartaoCliente;

@FeignClient(
		value = "mscartoes", 
		//url = "http://localhost:8080", 
		path = "/cartoes"
		)
public interface CartaoResourceClient {

	@GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesPorCliente(
            @RequestParam("cpf") String cpf
    );
	
	@GetMapping(params = "renda")
	ResponseEntity<List<Cartao>> getCartaoesRendaAteh(@RequestParam("renda") Long renda);
}
