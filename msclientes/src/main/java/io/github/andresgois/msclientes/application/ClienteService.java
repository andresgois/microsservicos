package io.github.andresgois.msclientes.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.andresgois.msclientes.domain.Cliente;
import io.github.andresgois.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository repository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}
	
	public Optional<Cliente> getByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
