package br.com.tarefas.config;

import br.com.tarefas.config.dao.TarefaDAO;
import br.com.tarefas.config.model.Tarefa;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TarefaDAO dao = new TarefaDAO();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GERENCIADOR DE TAREFAS ---");
            System.out.println("1. Cadastrar Tarefa");
            System.out.println("2. Listar Todas as Tarefas");
            System.out.println("3. Listar por Categoria");
            System.out.println("4. Listar por Status (Concluídas / Pendentes)");
            System.out.println("5. Atualizar Tarefa");
            System.out.println("6. Marcar Tarefa como Concluída");
            System.out.println("7. Excluir Tarefa");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String desc = scanner.nextLine();

                    // ID 1 inserido automaticamente pelo sistema (Categoria Geral)
                    dao.inserir(new Tarefa(titulo, desc, 1));
                    break;
                case 2:
                    imprimirLista(dao.listarTodas());
                    break;
                case 3:
                    System.out.print("Digite o ID da categoria: ");
                    int idCat = scanner.nextInt();
                    imprimirLista(dao.listarPorCategoria(idCat));
                    break;
                case 4:
                    System.out.print("1 para Concluídas | 2 para Pendentes: ");
                    int st = scanner.nextInt();
                    imprimirLista(dao.listarPorStatus(st == 1));
                    break;
                case 5:
                    System.out.print("ID da tarefa para atualizar: ");
                    int idUp = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo Título: ");
                    String nt = scanner.nextLine();
                    System.out.print("Nova Descrição: ");
                    String nd = scanner.nextLine();
                    dao.atualizar(idUp, nt, nd);
                    break;
                case 6:
                    System.out.print("ID da tarefa para concluir: ");
                    int idConc = scanner.nextInt();
                    dao.marcarComoConcluida(idConc);
                    break;
                case 7:
                    System.out.print("ID da tarefa para excluir: ");
                    int idDel = scanner.nextInt();
                    dao.deletar(idDel);
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private static void imprimirLista(List<Tarefa> tarefas) {
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }
        System.out.println("\n---------------------------------------------------------");
        for (Tarefa t : tarefas) {
            String status = t.isConcluida() ? "[ Concluída ]" : "[ Pendente  ]";
            System.out.printf("ID: %d | %s | %s | Categoria: %s\nDescrição: %s\n---------------------------------------------------------\n",
                    t.getId(), status, t.getTitulo(), t.getNomeCategoria(), t.getDescricao());
        }
    }
}