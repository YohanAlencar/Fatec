package br.edu.fatecpg.Main;

import br.edu.fatecpg.Model.Funcionario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Ana Silva", "TI", 4500.0, 12));
        funcionarios.add(new Funcionario("Bruno Souza", "RH", 2800.0, 4));
        funcionarios.add(new Funcionario("Carla Dias", "TI", 5200.0, 11));
        funcionarios.add(new Funcionario("Daniel Alves", "Financeiro", 3100.0, 2));
        funcionarios.add(new Funcionario("Eduarda Lima", "RH", 3500.0, 15));
        funcionarios.add(new Funcionario("Fernando Costa", "TI", 2900.0, 1));
        funcionarios.add(new Funcionario("Gabriela Rocha", "Financeiro", 6000.0, 8));
        funcionarios.add(new Funcionario("Helio Santos", "Financeiro", 4000.0, 6));

        System.out.println("--- LISTA INICIAL ---");
        funcionarios.forEach(System.out::println);


        System.out.println("\n 1. Funcionários com salário > R$ 3000 ");
        List<Funcionario> salarioMaior3000 = funcionarios.stream()
                .filter(f -> f.getSalario() > 3000.0)
                .collect(Collectors.toList());
        salarioMaior3000.forEach(System.out::println);


        System.out.println("\n 2. Aumento de 5% para quem tem mais de 10 anos de serviço ");
        List<Funcionario> funcionariosComAumento = funcionarios.stream()
                .map(f -> {
                    if (f.getAnosDeServico() > 10) {
                        return new Funcionario(f.getNome(), f.getDepartamento(), f.getSalario() * 1.05, f.getAnosDeServico());
                    }
                    return f;
                })
                .collect(Collectors.toList());
        funcionariosComAumento.forEach(System.out::println);


        System.out.println("\n 3. Ordenados por Nome em Ordem Alfabética ");
        List<Funcionario> ordenadosPorNome = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
        ordenadosPorNome.forEach(System.out::println);


        double totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(0.0, Double::sum);
        System.out.println(String.format("\n 4. Total Gasto com Salários: R$ %.2f ", totalSalarios));


        System.out.println("\n 5. Média Salarial por Departamento ");
        Map<String, Double> mediaPorDepartamento = funcionarios.stream()
                .collect(Collectors.groupingBy(
                        Funcionario::getDepartamento,
                        Collectors.averagingDouble(Funcionario::getSalario)
                ));
        mediaPorDepartamento.forEach((dept, media) ->
                System.out.println(String.format("Departamento: %s | Média: R$ %.2f", dept, media)));


        System.out.println("\n DESAFIOS ADICIONAIS ");


        funcionarios.stream()
                .max(Comparator.comparingInt(Funcionario::getAnosDeServico))
                .ifPresent(f -> System.out.println("Maior tempo de serviço: " + f.getNome() + " (" + f.getAnosDeServico() + " anos)"));


        System.out.println("\nSaída com Formatação Personalizada:");
        funcionarios.forEach(f -> System.out.println(
                String.format("Funcionário: %s, Departamento: %s, Salário: R$ %.2f",
                        f.getNome(), f.getDepartamento(), f.getSalario())
        ));
    }
}