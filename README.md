# VexaLabs Commerce API

Backend de e-commerce desenvolvido pela VexaLabs para gerenciar o catálogo de
produtos de uma loja. A versão atual oferece cadastro, listagem e consulta de
produtos por identificador.

## Tecnologias

- Java 21 e Spring Boot
- Maven
- PostgreSQL 17
- Spring Data JPA e Flyway
- Docker Compose para o banco de desenvolvimento

## Execução local

Requisitos: JDK 21 e Docker com Compose. O Maven Wrapper está incluído no projeto.

Configure as seguintes variáveis:

| Variável | Finalidade | Valor para o ambiente local |
| --- | --- | --- |
| `DB_NAME` | Banco criado pelo Compose | `commerce` |
| `DB_URL` | URL JDBC utilizada pela aplicação | `jdbc:postgresql://localhost:5433/commerce` |
| `DB_USER` | Usuário do PostgreSQL | Definido pelo ambiente |
| `DB_PASSWORD` | Senha do PostgreSQL | Definida pelo ambiente |

O Compose aceita variáveis do terminal ou de um arquivo `.env` local, ignorado
pelo Git. A aplicação deve receber `DB_URL`, `DB_USER` e `DB_PASSWORD` em seu
próprio processo, por meio do terminal ou da configuração de execução da IDE.
As credenciais devem corresponder às usadas para inicializar o banco.

Inicie o banco:

```sh
docker compose up -d
```

Execute a aplicação com as variáveis configuradas:

```sh
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd` no lugar de `./mvnw`.

A API fica disponível em `http://localhost:8080/api/products`. O PostgreSQL é
publicado em `127.0.0.1:5433` e utiliza um volume para persistir os dados.

O Flyway aplica as migrations na inicialização. O Hibernate valida a estrutura
existente com `ddl-auto=validate`. Migrations já aplicadas devem ser preservadas;
novas mudanças de estrutura devem ser registradas em novas versões.

## API de produtos

| Método | Rota | Operação |
| --- | --- | --- |
| `POST` | `/api/products` | Cadastrar produto |
| `GET` | `/api/products` | Listar produtos |
| `GET` | `/api/products/{id}` | Consultar produto por UUID |

O cadastro recebe nome, descrição opcional e preço de venda em reais. Nome e preço
são obrigatórios. O preço deve ser positivo e ter no máximo duas casas decimais.
Produtos podem ter nomes iguais e são distinguidos pelo identificador gerado.

Cadastros válidos retornam `201`. Consultas retornam `200`. Violações das
validações do serviço retornam `400`; produtos não encontrados retornam `404`.
Esses erros possuem um corpo JSON com o campo `message`.

## Organização

O código é agrupado por funcionalidade. O pacote `product` contém controller,
serviço, entidade, repository e tratamento de exceções do catálogo.
As migrations ficam em `src/main/resources/db/migration`.

## Testes

Com o PostgreSQL disponível e as variáveis configuradas:

```sh
./mvnw test
```

A suíte atual contém um teste de inicialização do contexto com conexão ao banco.
A cobertura automatizada dos comportamentos do catálogo ainda está em desenvolvimento.

## Acompanhamento

Demandas e revisões são acompanhadas nas
[issues do projeto](https://github.com/vexalabs-dev/commerce-api/issues).
