package br.edu.fatecpg.Model;

public class Funcionario {
    private String nome;
    private String departamento;
    private double salario;
    private int anosDeServico;

    public Funcionario(String nome, String departamento, double salario, int anosDeServico) {
        this.nome = nome;
        this.departamento = departamento;
        this.salario = salario;
        this.anosDeServico = anosDeServico;
    }

    public String getNome() { return nome; }
    public String getDepartamento() { return departamento; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public int getAnosDeServico() { return anosDeServico; }

    @Override
    public String toString() {
        return String.format("Funcionário: %s, Departamento: %s, Salário: R$ %.2f, Anos de Serviço: %d",
                nome, departamento, salario, anosDeServico);
    }
}