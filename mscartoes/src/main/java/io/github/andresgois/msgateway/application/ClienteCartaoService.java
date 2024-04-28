package io.github.andresgois.msgateway.application;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.andresgois.msgateway.domain.ClienteCartao;
import io.github.andresgois.msgateway.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	private final ClienteCartaoRepository repository;
	
	public List<ClienteCartao> listCartoesByCpf(String cpf){
		return repository.findByCpf(cpf);
	}
}
