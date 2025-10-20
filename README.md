# MindInvest Game Service
## Alunos

RM550188 / Gustavo Vegi
Pedro Henrique Silva de Morais / RM98804
Lucas Rodrigues Delfino/ RM550196
Luisa Cristina dos Santos Neves/ RM551889
Gabriel aparecido Cassalho Xavier / RM99794

## Descrição do Projeto

O **MindInvest Game Service** é uma API REST desenvolvida em Spring Boot para gerenciar um jogo de tomada de decisões financeiras. O sistema permite criar e gerenciar personagens e rodadas de jogo, onde cada rodada apresenta duas opções de escolha (A ou B) com suas respectivas consequências.

O jogo é projetado para simular cenários de investimento e educação financeira, onde os jogadores podem aprender através de diferentes personagens e situações apresentadas em cada rodada.

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.6** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - Desenvolvimento de APIs REST
- **Spring Boot Validation** - Validação de dados
- **H2 Database** - Banco de dados em memória para desenvolvimento
- **Flyway** - Controle de versão do banco de dados
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências

## Arquitetura do Sistema

O projeto segue uma arquitetura em camadas (Layered Architecture):

```
┌─────────────────┐
│   Controllers   │ ← Camada de Apresentação (REST API)
├─────────────────┤
│    Services     │ ← Camada de Negócio
├─────────────────┤
│  Repositories   │ ← Camada de Acesso a Dados
├─────────────────┤
│     Models      │ ← Camada de Entidades
└─────────────────┘
```

### Componentes Principais

- **Controllers**: Gerenciam as requisições HTTP e respostas
- **Services**: Contêm a lógica de negócio da aplicação
- **Repositories**: Responsáveis pelo acesso aos dados
- **DTOs**: Objetos de transferência de dados
- **Models**: Entidades JPA que representam as tabelas do banco

## Configuração e Execução

### Pré-requisitos

- Java 21 ou superior
- Maven 3.6 ou superior

### Passos para Execução

1. **Clone o repositório**
   ```bash
   git clone <repository-url>
   cd mindinvest-game-service
   ```

2. **Compile o projeto**
   ```bash
   ./mvnw clean compile
   ```

3. **Execute os testes**
   ```bash
   ./mvnw test
   ```

4. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Acesse a aplicação**
   - A API estará disponível em: `http://localhost:8080`
   - Console do H2: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: (deixe em branco)

## Endpoints da API

### Personagens

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/personagens` | Criar novo personagem |
| GET | `/api/personagens` | Listar todos os personagens |
| GET | `/api/personagens/{id}` | Buscar personagem por ID |
| PUT | `/api/personagens/{id}` | Atualizar personagem |
| DELETE | `/api/personagens/{id}` | Deletar personagem |

### Rodadas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/rodadas` | Criar nova rodada |
| GET | `/api/rodadas` | Listar todas as rodadas |
| GET | `/api/rodadas/{id}` | Buscar rodada por ID |
| GET | `/api/rodadas/personagem/{personagemId}` | Listar rodadas de um personagem |
| PUT | `/api/rodadas/{id}` | Atualizar rodada |
| DELETE | `/api/rodadas/{id}` | Deletar rodada |

## Exemplos de Requisições e Respostas

### Criar Personagem

**Requisição:**
```http
POST /api/personagens
Content-Type: application/json

{
  "nome": "João Investidor",
  "papel": "Investidor Iniciante",
  "descricao": "Um jovem profissional começando sua jornada de investimentos"
}
```

**Resposta:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "nome": "João Investidor",
  "papel": "Investidor Iniciante",
  "descricao": "Um jovem profissional começando sua jornada de investimentos"
}
```

### Listar Personagens

**Requisição:**
```http
GET /api/personagens
```

**Resposta:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "nome": "João Investidor",
    "papel": "Investidor Iniciante",
    "descricao": "Um jovem profissional começando sua jornada de investimentos"
  }
]
```

### Criar Rodada

**Requisição:**
```http
POST /api/rodadas
Content-Type: application/json

{
  "descricao": "Você recebeu um bônus de R$ 5.000. O que fazer?",
  "escolhaA": "Investir tudo em ações de uma empresa promissora",
  "escolhaB": "Dividir entre poupança (50%) e fundos de investimento (50%)",
  "consequenciaA": "Alto risco, alto retorno potencial, mas pode perder tudo",
  "consequenciaB": "Retorno moderado e mais segurança para o capital",
  "personagemId": 1
}
```

**Resposta:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 1,
  "descricao": "Você recebeu um bônus de R$ 5.000. O que fazer?",
  "escolhaA": "Investir tudo em ações de uma empresa promissora",
  "escolhaB": "Dividir entre poupança (50%) e fundos de investimento (50%)",
  "consequenciaA": "Alto risco, alto retorno potencial, mas pode perder tudo",
  "consequenciaB": "Retorno moderado e mais segurança para o capital",
  "personagemId": 1
}
```

### Buscar Rodadas por Personagem

**Requisição:**
```http
GET /api/rodadas/personagem/1
```

**Resposta:**
```http
HTTP/1.1 200 OK
Content-Type: application/json

[
  {
    "id": 1,
    "descricao": "Você recebeu um bônus de R$ 5.000. O que fazer?",
    "escolhaA": "Investir tudo em ações de uma empresa promissora",
    "escolhaB": "Dividir entre poupança (50%) e fundos de investimento (50%)",
    "consequenciaA": "Alto risco, alto retorno potencial, mas pode perder tudo",
    "consequenciaB": "Retorno moderado e mais segurança para o capital",
    "personagemId": 1
  }
]
```

## Modelo de Dados

### Entidade Personagem
- `id` (Long): Identificador único
- `nome` (String): Nome do personagem
- `papel` (String): Papel/função do personagem no jogo
- `descricao` (String): Descrição detalhada do personagem

### Entidade Rodada
- `id` (Long): Identificador único
- `descricao` (String): Descrição do cenário da rodada
- `escolhaA` (String): Primeira opção de escolha
- `escolhaB` (String): Segunda opção de escolha
- `consequenciaA` (String): Consequência da escolha A
- `consequenciaB` (String): Consequência da escolha B
- `personagemId` (Long): Referência ao personagem

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/mindInvest_game_service/
│   │   ├── controller/          # Controllers REST
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── model/               # Entidades JPA
│   │   ├── repository/          # Repositórios de dados
│   │   ├── service/             # Lógica de negócio
│   │   └── MindInvestGameServiceApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/                    # Testes unitários
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.
