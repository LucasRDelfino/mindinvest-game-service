# Arquitetura do Sistema - MindInvest Game Service

## Visão Geral da Arquitetura

O MindInvest Game Service segue uma arquitetura em camadas (Layered Architecture) baseada nos princípios do Spring Boot, promovendo separação de responsabilidades e facilidade de manutenção.

## Diagrama de Arquitetura - Camadas

```
┌─────────────────────────────────────────────────────────────┐
│                    CAMADA DE APRESENTAÇÃO                   │
│  ┌─────────────────────┐  ┌─────────────────────────────┐   │
│  │ PersonagemController│  │    RodadaController         │   │
│  │                     │  │                             │   │
│  │ - POST /personagens │  │ - POST /rodadas             │   │
│  │ - GET /personagens  │  │ - GET /rodadas              │   │
│  │ - GET /personagens/{id}│ - GET /rodadas/{id}        │   │
│  │ - PUT /personagens/{id}│ - GET /rodadas/personagem/{id}│  │
│  │ - DELETE /personagens/{id}│ - PUT /rodadas/{id}     │   │
│  │                     │  │ - DELETE /rodadas/{id}      │   │
│  └─────────────────────┘  └─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAMADA DE NEGÓCIO                        │
│  ┌─────────────────────┐  ┌─────────────────────────────┐   │
│  │  PersonagemService  │  │     RodadaService           │   │
│  │                     │  │                             │   │
│  │ - criar()           │  │ - criar()                   │   │
│  │ - listar()          │  │ - listar()                  │   │
│  │ - buscarPorId()     │  │ - buscarPorId()             │   │
│  │ - atualizar()       │  │ - listarPorPersonagem()     │   │
│  │ - deletar()         │  │ - atualizar()               │   │
│  │                     │  │ - deletar()                 │   │
│  │                     │  │ - toDTO()                   │   │
│  └─────────────────────┘  └─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────┐
│                 CAMADA DE ACESSO A DADOS                    │
│  ┌─────────────────────┐  ┌─────────────────────────────┐   │
│  │PersonagemRepository │  │    RodadaRepository         │   │
│  │                     │  │                             │   │
│  │extends JpaRepository│  │extends JpaRepository        │   │
│  │<Personagem, Long>   │  │<Rodada, Long>               │   │
│  │                     │  │                             │   │
│  │                     │  │+ findByPersonagemId()       │   │
│  └─────────────────────┘  └─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────┐
│                    CAMADA DE DADOS                          │
│                                                             │
│                    ┌─────────────────┐                     │
│                    │   H2 Database   │                     │
│                    │                 │                     │
│                    │ ┌─────────────┐ │                     │
│                    │ │ personagem  │ │                     │
│                    │ └─────────────┘ │                     │
│                    │ ┌─────────────┐ │                     │
│                    │ │   rodada    │ │                     │
│                    │ └─────────────┘ │                     │
│                    └─────────────────┘                     │
└─────────────────────────────────────────────────────────────┘
```

## Diagrama de Componentes

```
┌─────────────────────────────────────────────────────────────────┐
│                        SPRING BOOT APPLICATION                  │
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │      DTOs       │    │   Controllers   │                    │
│  │                 │    │                 │                    │
│  │ PersonagemDTO   │◄───┤PersonagemCtrl   │                    │
│  │ RodadaDTO       │    │RodadaController │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           ▲                       │                            │
│           │                       ▼                            │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │    Services     │    │   Validation    │                    │
│  │                 │    │                 │                    │
│  │PersonagemService│    │ @Valid          │                    │
│  │RodadaService    │    │ @RequestBody    │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                                                    │
│           ▼                                                    │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │  Repositories   │    │     Models      │                    │
│  │                 │    │                 │                    │
│  │PersonagemRepo   │───►│   Personagem    │                    │
│  │RodadaRepo       │    │   Rodada        │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           ▼                       ▼                            │
│  ┌─────────────────┐    ┌─────────────────┐                    │
│  │   Spring Data   │    │   JPA/Hibernate │                    │
│  │      JPA        │    │   Annotations   │                    │
│  └─────────────────┘    └─────────────────┘                    │
│           │                       │                            │
│           └───────────┬───────────┘                            │
│                       ▼                                        │
│           ┌─────────────────────────┐                          │
│           │      H2 Database        │                          │
│           │    (In-Memory/File)     │                          │
│           └─────────────────────────┘                          │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    DEPENDÊNCIAS EXTERNAS                        │
│                                                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │
│  │   Lombok    │  │   Flyway    │  │   Maven     │             │
│  │             │  │             │  │             │             │
│  │ @Data       │  │ Migrations  │  │ Dependencies│             │
│  │ @Builder    │  │ Versioning  │  │ Build Tool  │             │
│  │ @NoArgsConst│  │             │  │             │             │
│  └─────────────┘  └─────────────┘  └─────────────┘             │
└─────────────────────────────────────────────────────────────────┘
```

## Fluxo de Dados

### Fluxo de Requisição (Request Flow)

```
Cliente HTTP
     │
     ▼
┌─────────────────┐
│   Controller    │ ← Recebe requisição HTTP
│                 │   Valida parâmetros
└─────────────────┘   Converte para DTO
     │
     ▼
┌─────────────────┐
│    Service      │ ← Processa lógica de negócio
│                 │   Valida regras
└─────────────────┘   Converte DTO para Entity
     │
     ▼
┌─────────────────┐
│   Repository    │ ← Acessa dados
│                 │   Executa queries
└─────────────────┘   Retorna entities
     │
     ▼
┌─────────────────┐
│   Database      │ ← Persiste/recupera dados
└─────────────────┘
```

### Fluxo de Resposta (Response Flow)

```
Database
     │
     ▼
┌─────────────────┐
│   Repository    │ ← Retorna entities
└─────────────────┘
     │
     ▼
┌─────────────────┐
│    Service      │ ← Converte Entity para DTO
│                 │   Aplica regras de negócio
└─────────────────┘
     │
     ▼
┌─────────────────┐
│   Controller    │ ← Converte DTO para JSON
│                 │   Define status HTTP
└─────────────────┘
     │
     ▼
Cliente HTTP
```

## Padrões Arquiteturais Utilizados

### 1. Layered Architecture (Arquitetura em Camadas)
- **Presentation Layer**: Controllers
- **Business Layer**: Services
- **Data Access Layer**: Repositories
- **Data Layer**: Database

### 2. Repository Pattern
- Abstração do acesso a dados
- Implementação automática via Spring Data JPA
- Queries customizadas quando necessário

### 3. Data Transfer Object (DTO)
- Separação entre modelo de domínio e API
- Controle sobre dados expostos
- Validação de entrada

### 4. Dependency Injection
- Inversão de controle via Spring
- Baixo acoplamento entre componentes
- Facilita testes unitários

### 5. Builder Pattern
- Criação de objetos complexos
- Implementado via Lombok @Builder
- Código mais legível

## Princípios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada classe tem uma única responsabilidade
- Controllers: gerenciar HTTP
- Services: lógica de negócio
- Repositories: acesso a dados

### Open/Closed Principle (OCP)
- Extensível via herança e interfaces
- Spring Data JPA permite extensão de funcionalidades

### Liskov Substitution Principle (LSP)
- Interfaces bem definidas
- Implementações substituíveis

### Interface Segregation Principle (ISP)
- Interfaces específicas e coesas
- Repository interfaces focadas

### Dependency Inversion Principle (DIP)
- Dependência de abstrações (interfaces)
- Injeção de dependência via Spring

## Configurações e Propriedades

### application.properties
```properties
# Nome da aplicação
spring.application.name=mindInvest-game-service

# Configuração do H2 (desenvolvimento)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Console H2
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=validate

# Flyway
spring.flyway.enabled=true
```

## Considerações de Segurança

### Validação de Entrada
- Uso de `@Valid` nos controllers
- DTOs com validações apropriadas
- Sanitização de dados

### Tratamento de Erros
- Exceptions customizadas
- Respostas HTTP apropriadas
- Logs de segurança

### Configuração de Banco
- Banco em memória para desenvolvimento
- Configuração externa para produção
- Migrations controladas via Flyway

## Escalabilidade e Performance

### Otimizações Implementadas
- Lazy loading via JPA
- Queries otimizadas
- Uso de DTOs para reduzir payload

### Possíveis Melhorias
- Cache de dados frequentes
- Paginação em listagens
- Índices de banco de dados
- Connection pooling
