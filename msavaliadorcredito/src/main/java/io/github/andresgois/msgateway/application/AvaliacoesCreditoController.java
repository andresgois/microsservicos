package io.github.andresgois.msgateway.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.andresgois.msgateway.application.representation.SituacaoCliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliacoesCreditoController {
	
	private final AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		SituacaoCliente cliente = avaliadorCreditoService.obeterSituacaoCliente(cpf);
		return ResponseEntity.ok(cliente);
	}
	
}
