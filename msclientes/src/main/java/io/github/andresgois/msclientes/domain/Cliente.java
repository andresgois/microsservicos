package io.github.andresgois.msclientes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String nome;
	private Integer idade;
	
	public Cliente(String cpf, String nome, Integer idade) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}
	
}
