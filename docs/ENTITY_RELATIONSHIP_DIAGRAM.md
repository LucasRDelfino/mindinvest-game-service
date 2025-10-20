# Diagrama de Entidades - MindInvest Game Service

## Diagrama Entidade-Relacionamento (ER)

```
┌─────────────────────────────────────┐
│              PERSONAGEM             │
├─────────────────────────────────────┤
│ PK │ id          │ BIGINT          │
│    │ nome        │ VARCHAR(255)    │
│    │ papel       │ VARCHAR(255)    │
│    │ descricao   │ TEXT            │
└─────────────────────────────────────┘
                    │
                    │ 1
                    │
                    │ tem
                    │
                    │ N
                    ▼
┌─────────────────────────────────────┐
│               RODADA                │
├─────────────────────────────────────┤
│ PK │ id             │ BIGINT       │
│    │ descricao      │ TEXT         │
│    │ escolha_a      │ TEXT         │
│    │ escolha_b      │ TEXT         │
│    │ consequencia_a │ TEXT         │
│    │ consequencia_b │ TEXT         │
│ FK │ personagem_id  │ BIGINT       │
└─────────────────────────────────────┘
```

## Relacionamentos

### PERSONAGEM (1) ──── (N) RODADA
- **Tipo**: Um para Muitos (One-to-Many)
- **Descrição**: Um personagem pode ter várias rodadas associadas
- **Chave Estrangeira**: `personagem_id` na tabela RODADA
- **Cardinalidade**: 
  - Um personagem pode ter zero ou mais rodadas
  - Uma rodada pertence a exatamente um personagem

## Descrição das Entidades

### Entidade: PERSONAGEM

| Campo | Tipo | Restrições | Descrição |
|-------|------|------------|-----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único do personagem |
| nome | VARCHAR(255) | NOT NULL | Nome do personagem no jogo |
| papel | VARCHAR(255) | NOT NULL | Função/papel do personagem (ex: "Investidor Iniciante") |
| descricao | TEXT | NOT NULL | Descrição detalhada do personagem e seu contexto |

**Índices:**
- PRIMARY KEY (id)

**Regras de Negócio:**
- Nome deve ser único por personagem
- Todos os campos são obrigatórios
- Descrição deve ser informativa para contexto do jogo

### Entidade: RODADA

| Campo | Tipo | Restrições | Descrição |
|-------|------|------------|-----------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único da rodada |
| descricao | TEXT | NOT NULL | Descrição do cenário/situação da rodada |
| escolha_a | TEXT | NOT NULL | Primeira opção de escolha disponível |
| escolha_b | TEXT | NOT NULL | Segunda opção de escolha disponível |
| consequencia_a | TEXT | NOT NULL | Consequência/resultado da escolha A |
| consequencia_b | TEXT | NOT NULL | Consequência/resultado da escolha B |
| personagem_id | BIGINT | FOREIGN KEY, NOT NULL | Referência ao personagem da rodada |

**Índices:**
- PRIMARY KEY (id)
- INDEX (personagem_id)

**Chaves Estrangeiras:**
- personagem_id REFERENCES personagem(id) ON DELETE CASCADE

**Regras de Negócio:**
- Toda rodada deve estar associada a um personagem válido
- Escolhas A e B devem ser distintas e claras
- Consequências devem ser educativas e realistas
- Exclusão de personagem remove todas suas rodadas (CASCADE)

## Diagrama UML - Modelo de Classes

```
┌─────────────────────────────────────┐
│            Personagem               │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - nome: String                      │
│ - papel: String                     │
│ - descricao: String                 │
├─────────────────────────────────────┤
│ + getId(): Long                     │
│ + setId(Long): void                 │
│ + getNome(): String                 │
│ + setNome(String): void             │
│ + getPapel(): String                │
│ + setPapel(String): void            │
│ + getDescricao(): String            │
│ + setDescricao(String): void        │
└─────────────────────────────────────┘
                    │
                    │ 1
                    │
                    │
                    │ *
                    ▼
┌─────────────────────────────────────┐
│              Rodada                 │
├─────────────────────────────────────┤
│ - id: Long                          │
│ - descricao: String                 │
│ - escolhaA: String                  │
│ - escolhaB: String                  │
│ - consequenciaA: String             │
│ - consequenciaB: String             │
│ - personagem: Personagem            │
├─────────────────────────────────────┤
│ + getId(): Long                     │
│ + setId(Long): void                 │
│ + getDescricao(): String            │
│ + setDescricao(String): void        │
│ + getEscolhaA(): String             │
│ + setEscolhaA(String): void         │
│ + getEscolhaB(): String             │
│ + setEscolhaB(String): void         │
│ + getConsequenciaA(): String        │
│ + setConsequenciaA(String): void    │
│ + getConsequenciaB(): String        │
│ + setConsequenciaB(String): void    │
│ + getPersonagem(): Personagem       │
│ + setPersonagem(Personagem): void   │
└─────────────────────────────────────┘
```

## Anotações JPA

### Personagem Entity
```java
@Entity
@Table(name = "personagem")
public class Personagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String papel;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    
    @OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rodada> rodadas = new ArrayList<>();
}
```

### Rodada Entity
```java
@Entity
@Table(name = "rodada")
public class Rodada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    
    @Column(name = "escolha_a", nullable = false, columnDefinition = "TEXT")
    private String escolhaA;
    
    @Column(name = "escolha_b", nullable = false, columnDefinition = "TEXT")
    private String escolhaB;
    
    @Column(name = "consequencia_a", nullable = false, columnDefinition = "TEXT")
    private String consequenciaA;
    
    @Column(name = "consequencia_b", nullable = false, columnDefinition = "TEXT")
    private String consequenciaB;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personagem_id", nullable = false)
    private Personagem personagem;
}
```

## Scripts de Criação (DDL)

### Tabela PERSONAGEM
```sql
CREATE TABLE personagem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    papel VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Tabela RODADA
```sql
CREATE TABLE rodada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao TEXT NOT NULL,
    escolha_a TEXT NOT NULL,
    escolha_b TEXT NOT NULL,
    consequencia_a TEXT NOT NULL,
    consequencia_b TEXT NOT NULL,
    personagem_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_rodada_personagem 
        FOREIGN KEY (personagem_id) 
        REFERENCES personagem(id) 
        ON DELETE CASCADE
);

CREATE INDEX idx_rodada_personagem_id ON rodada(personagem_id);
```

## Dados de Exemplo

### Personagens de Exemplo
```sql
INSERT INTO personagem (nome, papel, descricao) VALUES 
('João Silva', 'Investidor Iniciante', 'Jovem profissional de 25 anos começando a investir'),
('Maria Santos', 'Investidora Experiente', 'Executiva de 40 anos com carteira diversificada'),
('Pedro Costa', 'Aposentado Conservador', 'Aposentado de 65 anos focado em preservar capital');
```

### Rodadas de Exemplo
```sql
INSERT INTO rodada (descricao, escolha_a, escolha_b, consequencia_a, consequencia_b, personagem_id) VALUES 
(
    'Você recebeu um bônus de R$ 10.000. Como investir?',
    'Aplicar tudo em ações de uma empresa de tecnologia',
    'Dividir entre poupança (30%), CDB (40%) e fundos (30%)',
    'Alto potencial de ganho, mas risco de perda significativa',
    'Retorno moderado com maior segurança e diversificação',
    1
),
(
    'O mercado está em queda. Qual sua estratégia?',
    'Vender tudo para evitar mais perdas',
    'Manter posições e aproveitar para comprar mais',
    'Evita perdas imediatas mas pode perder oportunidades',
    'Pode aumentar perdas no curto prazo, mas aproveita preços baixos',
    2
);
```

## Considerações de Design

### Normalização
- **3ª Forma Normal**: Eliminação de dependências transitivas
- **Chaves Primárias**: Auto-incrementais para performance
- **Chaves Estrangeiras**: Garantem integridade referencial

### Performance
- **Índices**: Criados em chaves estrangeiras
- **Lazy Loading**: Relacionamentos carregados sob demanda
- **Cascade Operations**: Configuradas adequadamente

### Extensibilidade
- **Campos TEXT**: Permitem conteúdo extenso
- **Timestamps**: Auditoria de criação/modificação
- **Soft Delete**: Possível implementação futura

### Integridade
- **NOT NULL**: Campos obrigatórios definidos
- **CASCADE DELETE**: Mantém consistência
- **Validações**: Implementadas na camada de aplicação
