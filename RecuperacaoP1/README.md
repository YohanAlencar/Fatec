# Sistema de Análise de Pedidos com Desserialização JSON e Streams

Este projeto foi desenvolvido como atividade de avaliação (Recuperação P1). Ele consome a API pública DummyJSON, realiza a desserialização de dados JSON para objetos Java e processa um relatório detalhado utilizando unicamente **Java Streams** e **Lambdas**, sem o uso de laços `for` ou `while`.

## 🚀 Tecnologias Utilizadas
- **Java 17+**
- **HttpClient** (`java.net.http`)
- **Jackson Databind** (para desserialização JSON)

## 📌 Funcionalidades
1. Consumo do endpoint `https://dummyjson.com/carts?limit=0`.
2. Tratamento defensivo de exceções e erros HTTP sem interromper a execução.
3. Consultas com Streams:
    - **Filtragem**: Carrinhos com valor total acima de US$ 1.000.
    - **Mapeamento**: Nomes dos produtos com desconto acima de 15%.
    - **Ordenação**: Carrinhos ordenados pela economia (`total - discountedTotal`), da maior para a menor.
    - **Redução**: Soma do total com desconto de todos os carrinhos.
    - **Agrupamento**: Quantidade de carrinhos agrupada pelo número de produtos (`totalProducts`).
4. Desafios Extras:
    - Busca do carrinho de maior valor com `max` e `Optional`.
    - Saída formatada via expressão Lambda (`Consumer<Carrinho>`).

## 🛠️ Como Executar
1. Certifique-se de ter o **JDK 17** instalado e configurado.
2. Adicione a biblioteca **Jackson Databind** (versão `2.17.0` ou superior) às bibliotecas do projeto (`Project Structure > Libraries`).
3. Compile e execute a classe `Main.java`.