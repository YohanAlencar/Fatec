package br.edu.fatecpg.spring.Jackson.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    public <T> T obterDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter o JSON: " + e.getMessage());
        }
    }
}