# VexaLabs Commerce API

Laboratório de backend Java. As regras de colaboração e mentoria estão em
[AGENTS.md](AGENTS.md). As demandas são mantidas nas
[GitHub Issues](https://github.com/vexalabs-dev/commerce-api/issues).

## Ambiente local

Requisitos: JDK 21 e Docker com Compose. O Maven Wrapper acompanha o projeto.
A aplicação roda na máquina; somente o PostgreSQL 17 roda no container.

1. Clone o repositório e selecione a branch em que está trabalhando.
2. Copie `.env.example` para `.env` e escolha uma senha local antes de iniciar um
   banco novo. No PowerShell: `Copy-Item .env.example .env`.
3. Inicie o Docker e execute `docker compose up -d` na raiz do projeto.
4. Confira `docker compose logs --tail 30 postgres` e aguarde
   `database system is ready to accept connections`.
5. Disponibilize `DB_URL`, `DB_USER` e `DB_PASSWORD` ao processo Java e execute
   `CommerceApiApplication` ou `./mvnw spring-boot:run`.

O Compose lê `.env`. O Spring não lê esse arquivo automaticamente na configuração
atual. No IntelliJ, use as variáveis de ambiente da configuração de execução ou o
plugin EnvFile: habilite o arquivo `.env`, mantendo **Executable desmarcado**.
A IDE deve usar JDK 21 e a classe principal
`com.vexalabs.commerceapi.CommerceApiApplication`.

Alternativa no PowerShell (substitua a senha pela mesma do banco):

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5433/commerce'
$env:DB_USER = 'commerce'
$env:DB_PASSWORD = 'replace_with_your_local_password'
.\mvnw.cmd spring-boot:run
```

A aplicação escuta em `http://localhost:8080`; o banco fica em
`localhost:5433`. Se mudar o nome do banco ou usuário, mantenha a URL e as
variáveis coerentes com o Compose.

O Flyway aplica os arquivos de `src/main/resources/db/migration` na inicialização.
O Hibernate usa `ddl-auto=validate` para conferir o mapeamento. Preserve migrations
já aplicadas; mudanças posteriores devem receber uma nova versão.

O volume Docker preserva os dados localmente. Git/push não transporta esse volume
para outra máquina: um ambiente novo começa com banco vazio, criado pelas
migrations. Alterar a senha no `.env` não muda a senha de um banco já inicializado.
Não remova o volume para resolver configurações sem considerar os dados existentes.

## Testes

Com o banco ativo e as variáveis disponíveis ao processo:

```powershell
.\mvnw.cmd test
```

Em Linux/macOS, use `./mvnw test`. Atualmente existe apenas o teste de carga do
contexto, que depende do PostgreSQL configurado. Ele não comprova as regras de
negócio ou os contratos HTTP.

## Registro de continuidade — 2026-09-29

Este registro é um retrato da passagem de trabalho, não um backlog permanente.
Confirme o estado atual no código e na issue antes de continuar.

- Demanda: [ECOM-001 / issue #1](https://github.com/vexalabs-dev/commerce-api/issues/1).
- Branch: `feat/ECOM-001-product-catalog`.
- Implementados: entidade, repository, serviço com validações básicas, controller
  de cadastro/listagem/consulta e migration V1 para a tabela `product`.
- O log fornecido pelo desenvolvedor confirmou conexão, aplicação da V1 e
  inicialização HTTP na porta 8080.
- A demanda continua em andamento, sem aprovação final para merge.
- Pendentes conhecidos: tradução das exceções para respostas HTTP adequadas,
  testes dos comportamentos e validação das requisições e persistência após
  reinício. Não presumir que esses fluxos já foram verificados.
- O próximo agente deve continuar a mentoria e permitir que o desenvolvedor
  implemente e investigue; não completar as pendências automaticamente.

Validação deste checkpoint: `mvnw.cmd -q test` passou em 2026-09-29, com as
variáveis locais fornecidas ao processo e o PostgreSQL existente. A V1 foi
validada como já aplicada. Não houve teste automatizado dos endpoints nem
recriação de um banco vazio nesta verificação.
