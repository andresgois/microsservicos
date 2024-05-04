package io.github.andresgois.msgateway.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.andresgois.msgateway.application.representation.CartaoSaveRequest;
import io.github.andresgois.msgateway.application.representation.CartoesPorClienteResponse;
import io.github.andresgois.msgateway.domain.Cartao;
import io.github.andresgois.msgateway.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
@Slf4j
public class CartaoResources {

	private final CartaoService cartaoService;
	private final ClienteCartaoService clienteCartaoService;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody CartaoSaveRequest request){
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartaoesRendaAteh(@RequestParam("renda") Long renda){
		List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteResponse>> getCartaoesRendaAteh(@RequestParam("cpf") String cpf){
		log.info("CPF: {}", cpf);
		 List<ClienteCartao> list = clienteCartaoService.listCartoesByCpf(cpf);
		 List<CartoesPorClienteResponse> resultList = list.stream()
		 	.map(CartoesPorClienteResponse::fromModel)
		 	.collect(Collectors.toList());
		return ResponseEntity.ok(resultList);
	}
}
