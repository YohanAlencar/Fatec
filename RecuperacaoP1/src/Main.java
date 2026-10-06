import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        String url = "https://dummyjson.com/carts?limit=0";

        // --- PARTE 2: Consumo da API e Desserialização ---
        RespostaCarrinhos resposta = consumirApi(url);

        if (resposta == null || resposta.getCarts() == null) {
            System.err.println("Não foi possível carregar os dados para processar os relatórios.");
            return;
        }

        List<Carrinho> carrinhos = resposta.getCarts();

        // Lambda de formatação do Desafio Extra 2
        Consumer<Carrinho> formatadorCarrinho = c -> System.out.printf(
                "Carrinho #%d | Usuário: %d | Itens: %d | Total: US$ %.2f%n",
                c.getId(), c.getUserId(), c.getTotalQuantity(), c.getTotal()
        );

        System.out.println("=== RELATÓRIO DE ANÁLISE DE PEDIDOS ===\n");

        // --- PARTE 3: Streams e Lambdas ---

        // 1. Filtragem (filter): Carrinhos com total acima de US$ 1.000
        System.out.println("1. Carrinhos com total acima de US$ 1.000:");
        carrinhos.stream()
                .filter(c -> c.getTotal() > 1000.0)
                .forEach(formatadorCarrinho);

        System.out.println("\n------------------------------------------------\n");

        // 2. Mapeamento (flatMap + map): Títulos dos produtos com desconto acima de 15%
        System.out.println("2. Títulos dos produtos com desconto acima de 15%:");
        carrinhos.stream()
                .flatMap(c -> c.getProducts().stream())
                .filter(p -> p.getDiscountPercentage() > 15.0)
                .map(ProdutoCarrinho::getTitle)
                .distinct()
                .forEach(titulo -> System.out.println("- " + titulo));

        System.out.println("\n------------------------------------------------\n");

        // 3. Ordenação (sorted): Carrinhos pela economia (getEconomia()), da maior para a menor
        System.out.println("3. Carrinhos ordenados pela economia (da maior para a menor):");
        carrinhos.stream()
                .sorted(Comparator.comparingDouble(Carrinho::getEconomia).reversed())
                .forEach(c -> System.out.printf("Economia: US$ %.2f | %s", c.getEconomia(),
                        String.format("Carrinho #%d | Usuário: %d | Total: US$ %.2f%n", c.getId(), c.getUserId(), c.getTotal())));

        System.out.println("\n------------------------------------------------\n");

        // 4. Redução (reduce): Soma do discountedTotal de todos os carrinhos
        System.out.println("4. Soma do discountedTotal de todos os carrinhos:");
        double somaDiscountedTotal = carrinhos.stream()
                .map(Carrinho::getDiscountedTotal)
                .reduce(0.0, Double::sum);
        System.out.printf("Soma Total com Desconto: US$ %.2f%n", somaDiscountedTotal);

        System.out.println("\n------------------------------------------------\n");

        // 5. Agrupamento (groupingBy): Quantidade de carrinhos por número de produtos (totalProducts)
        System.out.println("5. Quantidade de carrinhos por número de produtos (totalProducts):");
        Map<Integer, Long> carrinhosPorTotalProdutos = carrinhos.stream()
                .collect(Collectors.groupingBy(Carrinho::getTotalProducts, Collectors.counting()));

        carrinhosPorTotalProdutos.forEach((qtdProdutos, qtdCarrinhos) ->
                System.out.printf("%d produto(s): %d carrinho(s)%n", qtdProdutos, qtdCarrinhos)
        );

        System.out.println("\n------------------------------------------------\n");

        // --- DESAFIOS EXTRAS ---

        // Desafio Extra 1: Encontre o carrinho de maior valor com max e Optional
        System.out.println("Desafio Extra 1: Carrinho de maior valor:");
        Optional<Carrinho> carrinhoMaiorValor = carrinhos.stream()
                .max(Comparator.comparingDouble(Carrinho::getTotal));

        carrinhoMaiorValor.ifPresentOrElse(
                formatadorCarrinho,
                () -> System.out.println("Nenhum carrinho encontrado.")
        );
    }

    private static RespostaCarrinhos consumirApi(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("Erro na requisição HTTP! Código de status: " + response.statusCode());
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(response.body(), RespostaCarrinhos.class);

        } catch (Exception e) {
            System.err.println("Falha ao consumir ou desserializar os dados da API: " + e.getMessage());
            return null;
        }
    }
}