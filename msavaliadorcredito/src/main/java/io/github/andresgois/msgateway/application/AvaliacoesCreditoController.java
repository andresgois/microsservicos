package io.github.andresgois.msgateway.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.andresgois.msgateway.application.exception.DadosClienteNotFoundException;
import io.github.andresgois.msgateway.application.exception.ErroComunicacaoException;
import io.github.andresgois.msgateway.application.exception.ErroSolicitacaoCartao;
import io.github.andresgois.msgateway.application.representation.DadosAvaliacao;
import io.github.andresgois.msgateway.application.representation.DadosSolicitacaoEmissaoCartao;
import io.github.andresgois.msgateway.application.representation.ProtocoloSolicitacaoCartao;
import io.github.andresgois.msgateway.application.representation.RetornoAvaliacaoCliente;
import io.github.andresgois.msgateway.application.representation.SituacaoCliente;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliacoesCreditoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {
			SituacaoCliente cliente = avaliadorCreditoService.obeterSituacaoCliente(cpf);
			return ResponseEntity.ok(cliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoException e2) {
			return ResponseEntity.status(HttpStatus.resolve(e2.getStatus())).body(e2.getMessage());
		}
	}

	
	@PostMapping
	public ResponseEntity<?> realizarAvaliacao(@RequestBody DadosAvaliacao dados){
		try {
			RetornoAvaliacaoCliente retorno = avaliadorCreditoService
					.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retorno);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoException e2) {
			return ResponseEntity.status(HttpStatus.resolve(e2.getStatus())).body(e2.getMessage());
		}
	}
	
	@PostMapping("solicitacoes-cartao")
	public ResponseEntity<?> solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
		try {
			ProtocoloSolicitacaoCartao retorno = avaliadorCreditoService
					.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(retorno);
		} catch (ErroSolicitacaoCartao e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		} 
	}
}
