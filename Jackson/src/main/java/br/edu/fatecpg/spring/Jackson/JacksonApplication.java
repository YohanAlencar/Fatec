package br.edu.fatecpg.spring.Jackson;

import br.edu.fatecpg.spring.Jackson.model.Endereco;
import br.edu.fatecpg.spring.Jackson.service.ConsomeApi;
import br.edu.fatecpg.spring.Jackson.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

@SpringBootApplication
public class JacksonApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(JacksonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		ConverteDados conversor = new ConverteDados();
		int opcao = -1;

		while (opcao != 3) {
			System.out.println("\n=== MENU DE CONSULTA ===");
			System.out.println("1. Consultar");
			System.out.println("2. Listar");
			System.out.println("3. Sair");
			System.out.print("Escolha uma opção: ");

			opcao = scanner.nextInt();
			scanner.nextLine();

			if (opcao == 1) {
				System.out.print("Digite o CEP desejado (apenas números): ");
				String cep = scanner.nextLine();

				try {

					String json = ConsomeApi.consultaEndereco(cep);
					Endereco endereco = conversor.obterDados(json, Endereco.class);

					System.out.println("Resultado da busca: " + endereco);


					ZonedDateTime dataHora = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));
					String registro = dataHora + " - Consulta CEP " + cep + ": " + endereco + "\n";


					try (FileWriter writer = new FileWriter("consultas.log", true)) {
						writer.write(registro);
						System.out.println("Registro salvo no arquivo 'consultas.log'!");
					} catch (IOException e) {
						System.out.println("Erro ao gravar log: " + e.getMessage());
					}

				} catch (Exception e) {
					System.out.println("Erro ao realizar a consulta: " + e.getMessage());
				}

			} else if (opcao == 2) {

				try (BufferedReader reader = new BufferedReader(new FileReader("consultas.log"))) {
					String linha;
					System.out.println("\n=== HISTÓRICO DE LOGS ===");
					while ((linha = reader.readLine()) != null) {
						System.out.println(linha);
					}
				} catch (IOException e) {
					System.out.println("Nenhum histórico encontrado ou erro ao ler arquivo.");
				}

			} else if (opcao != 3) {
				System.out.println("Opção inválida! Tente novamente.");
			}
		}

		System.out.println("Programa finalizado.");
	}
}