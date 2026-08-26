import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final List<PokemonFavorito> bancoFavoritos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU POKEMON ---");
            System.out.println("1 - Ver Pokemons da API");
            System.out.println("2 - Salvar Pokemon Favorito");
            System.out.println("3 - Ver Meus Favoritos");
            System.out.println("4 - Excluir Favorito");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            if (leitor.hasNextInt()) {
                opcao = leitor.nextInt();
                leitor.nextLine();
            } else {
                leitor.nextLine();
                continue;
            }

            if (opcao == 1) {
                System.out.println("\nBuscando Pokemons na API...");
                String dados = buscarNaAPI("https://pokeapi.co/api/v2/pokemon?limit=10");

                System.out.println("\n=== POKEMONS DISPONIVEIS ===");
                Pattern pattern = Pattern.compile("\"name\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(dados);

                int contador = 1;
                while (matcher.find()) {
                    String nomePokemon = matcher.group(1);
                    System.out.println(contador + ". " + nomePokemon.substring(0, 1).toUpperCase() + nomePokemon.substring(1));
                    contador++;
                }

            } else if (opcao == 2) {
                System.out.print("\nDigite o nome do Pokemon (ex: pikachu): ");
                String nome = leitor.nextLine().toLowerCase().trim();

                String dados = buscarNaAPI("https://pokeapi.co/api/v2/pokemon/" + nome);

                if (dados.contains("Nao encontrado")) {
                    System.out.println("Pokemon nao encontrado na API!");
                } else {
                    String altura = extrairDado(dados, "\"height\":");
                    String peso = extrairDado(dados, "\"weight\":");
                    String xp = extrairDado(dados, "\"base_experience\":");

                    bancoFavoritos.removeIf(p -> p.nome.equalsIgnoreCase(nome));
                    bancoFavoritos.add(new PokemonFavorito(nome, altura, peso, xp));

                    System.out.println(nome.toUpperCase() + " salvo nos favoritos com sucesso!");
                }

            } else if (opcao == 3) {
                System.out.println("\n=== MEUS FAVORITOS ===");

                if (bancoFavoritos.isEmpty()) {
                    System.out.println("Nenhum Pokemon salvo ainda.");
                } else {
                    for (PokemonFavorito p : bancoFavoritos) {
                        System.out.println("Nome: " + p.nome.toUpperCase());
                        System.out.println("  -> 1. Altura: " + p.altura);
                        System.out.println("  -> 2. Peso: " + p.peso);
                        System.out.println("  -> 3. Experiencia Base: " + p.xp);
                        System.out.println("--------------------------------");
                    }
                }

            } else if (opcao == 4) {
                System.out.print("\nDigite o nome do Pokemon para excluir: ");
                String nomeExcluir = leitor.nextLine().toLowerCase().trim();

                boolean removido = bancoFavoritos.removeIf(p -> p.nome.equalsIgnoreCase(nomeExcluir));

                if (removido) {
                    System.out.println("Removido dos favoritos!");
                } else {
                    System.out.println("Pokemon nao estava na sua lista.");
                }
            }
        }
        leitor.close();
    }

    public static String buscarNaAPI(String urlParaBuscar) {
        try {
            URL url = new URL(urlParaBuscar);
            InputStream resposta = url.openStream();
            Scanner scanner = new Scanner(resposta);
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return "Nao encontrado";
        }
    }

    public static String extrairDado(String texto, String chave) {
        try {
            int inicio = texto.indexOf(chave) + chave.length();
            int fim = texto.indexOf(",", inicio);
            return texto.substring(inicio, fim).trim();
        } catch (Exception e) {
            return "N/A";
        }
    }

    static class PokemonFavorito {
        String nome;
        String altura;
        String peso;
        String xp;

        public PokemonFavorito(String nome, String altura, String peso, String xp) {
            this.nome = nome;
            this.altura = altura;
            this.peso = peso;
            this.xp = xp;
        }
    }
}