package br.edu.fatecpg.spring.Jackson.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Endereco(
        String cep,
        @JsonAlias("logradouro")String  rua,
        String bairro,
        @JsonAlias("localidade")String cidade,
        String uf
) {}
