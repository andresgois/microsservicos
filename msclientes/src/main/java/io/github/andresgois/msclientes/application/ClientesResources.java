package io.github.andresgois.msclientes.application;

import java.net.URI;
import java.util.Optional;

import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.andresgois.msclientes.application.representation.ClienteSaveRequest;
import io.github.andresgois.msclientes.domain.Cliente;
import io.github.andresgois.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientesResources {
	
	private final ClienteService service;

	// http://localhost:58766/clientes/path
	@GetMapping("path")
	public String getMethodName() {
		log.info("Hello");
		return "Hello MSCliente";
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity buscarPorCpf(@RequestParam("cpf") String cpf){
		Optional<Cliente> cliente = service.getByCpf(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente.get());
	}
	
	@PostMapping
	public ResponseEntity<?> criarCliente(@RequestBody ClienteSaveRequest request){
		Cliente cli = request.toModel();
		service.save(cli);
		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.query("cpf={cpf}")
			.buildAndExpand(cli.getCpf()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
