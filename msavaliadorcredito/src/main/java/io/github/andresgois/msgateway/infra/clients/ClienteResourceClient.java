package io.github.andresgois.msgateway.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.andresgois.msgateway.application.representation.DadosCliente;


@FeignClient(value = "msclientes", url = "http://localhost:8080", path = "/clientes")
public interface ClienteResourceClient {
	
	
	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
