import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private static List<String> historico = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Opção inválida! Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa a quebra de linha

            switch (opcao) {
                case 1:
                    consultarCep(scanner);
                    break;
                case 2:
                    verConsultados();
                    break;
                case 3:
                    limparHistorico();
                    break;
                case 0:
                    executando = false;
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n----------------------------------");
        System.out.println("     SISTEMA DE CONSULTA VIACEP   ");
        System.out.println("----------------------------------");
        System.out.println("1 - Consultar CEP");
        System.out.println("2 - Ver Consultados");
        System.out.println("3 - Limpar Histórico de Consulta");
        System.out.println("0 - Sair");
        System.out.println("----------------------------------");
    }

    private static void consultarCep(Scanner scanner) {
        System.out.print("Digite o CEP (com ou sem traço): ");
        String cepInput = scanner.nextLine().trim();

        // Limpa formatação deixando apenas números
        String cep = cepInput.replaceAll("[^0-9]", "");

        if (cep.length() != 8) {
            System.out.println("Erro: O CEP precisa ter exatamente 8 dígitos.");
            return;
        }

        try {
            // Criação do cliente HTTP moderno
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
                    .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String resultadoJson = response.body();

                if (resultadoJson.contains("\"erro\":")) {
                    System.out.println("Atenção: CEP não encontrado na base de dados.");
                } else {
                    System.out.println("\n--- Resultado da Busca ---");
                    System.out.println(resultadoJson);

                    historico.add("CEP: " + cep + " -> " + resultadoJson);
                    System.out.println("\nConsulta salva no histórico com sucesso!");
                }
            } else {
                System.out.println("Erro ao conectar à API. Código HTTP: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Falha ao realizar a requisição: " + e.getMessage());
            System.out.println("Verifique sua conexão de internet ou permissões de firewall.");
        }
    }

    private static void verConsultados() {
        System.out.println("\n=== ENDEREÇOS NO HISTÓRICO ===");
        if (historico.isEmpty()) {
            System.out.println("Nenhuma consulta registrada até o momento.");
        } else {
            for (int i = 0; i < historico.size(); i++) {
                System.out.println((i + 1) + ". " + historico.get(i));
            }
        }
    }

    private static void limparHistorico() {
        historico.clear();
        System.out.println("O histórico de consultas foi limpo!");
    }
}