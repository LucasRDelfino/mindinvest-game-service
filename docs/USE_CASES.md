# Casos de Uso - MindInvest Game Service

## Visão Geral dos Casos de Uso

O MindInvest Game Service implementa casos de uso relacionados ao gerenciamento de um jogo educativo de investimentos. Os casos de uso estão organizados em dois domínios principais: **Gerenciamento de Personagens** e **Gerenciamento de Rodadas**.

## Diagrama de Casos de Uso

```
                    ┌─────────────────────────────────────────┐
                    │          MindInvest Game Service        │
                    │                                         │
┌──────────┐        │  ┌─────────────────────────────────┐    │
│          │        │  │     Gerenciar Personagens       │    │
│          │        │  │                                 │    │
│   Admin  │◄───────┼──┤ UC01: Criar Personagem         │    │
│   Game   │        │  │ UC02: Listar Personagens       │    │
│          │        │  │ UC03: Buscar Personagem por ID │    │
│          │        │  │ UC04: Atualizar Personagem     │    │
└──────────┘        │  │ UC05: Deletar Personagem       │    │
                    │  └─────────────────────────────────┘    │
                    │                                         │
┌──────────┐        │  ┌─────────────────────────────────┐    │
│          │        │  │       Gerenciar Rodadas         │    │
│ Content  │        │  │                                 │    │
│ Creator  │◄───────┼──┤ UC06: Criar Rodada             │    │
│          │        │  │ UC07: Listar Rodadas           │    │
│          │        │  │ UC08: Buscar Rodada por ID     │    │
└──────────┘        │  │ UC09: Listar Rodadas por       │    │
                    │  │       Personagem               │    │
┌──────────┐        │  │ UC10: Atualizar Rodada         │    │
│          │        │  │ UC11: Deletar Rodada           │    │
│ Player/  │◄───────┼──┤                                 │    │
│ Client   │        │  └─────────────────────────────────┘    │
│          │        │                                         │
└──────────┘        └─────────────────────────────────────────┘
```

## Casos de Uso Detalhados

### UC01: Criar Personagem

**Ator Principal:** Admin Game, Content Creator  
**Objetivo:** Criar um novo personagem para o jogo  
**Pré-condições:** Sistema disponível  
**Pós-condições:** Personagem criado e armazenado no sistema  

**Fluxo Principal:**
1. Ator envia requisição POST para `/api/personagens`
2. Sistema valida os dados de entrada (nome, papel, descrição)
3. Sistema cria nova instância de Personagem
4. Sistema persiste o personagem no banco de dados
5. Sistema retorna o personagem criado com ID gerado

**Fluxos Alternativos:**
- **A1:** Dados inválidos
  - Sistema retorna erro 400 com detalhes da validação
- **A2:** Erro interno
  - Sistema retorna erro 500

**Implementação:**
- **Controller:** `PersonagemController.criar()`
- **Service:** `PersonagemService.criar()`
- **Repository:** `PersonagemRepository.save()`

---

### UC02: Listar Personagens

**Ator Principal:** Admin Game, Content Creator, Player  
**Objetivo:** Obter lista de todos os personagens disponíveis  
**Pré-condições:** Sistema disponível  
**Pós-condições:** Lista de personagens retornada  

**Fluxo Principal:**
1. Ator envia requisição GET para `/api/personagens`
2. Sistema busca todos os personagens no banco
3. Sistema converte entidades para DTOs
4. Sistema retorna lista de personagens

**Implementação:**
- **Controller:** `PersonagemController.listar()`
- **Service:** `PersonagemService.listar()`
- **Repository:** `PersonagemRepository.findAll()`

---

### UC03: Buscar Personagem por ID

**Ator Principal:** Admin Game, Content Creator, Player  
**Objetivo:** Obter detalhes de um personagem específico  
**Pré-condições:** Sistema disponível, ID válido  
**Pós-condições:** Personagem encontrado retornado  

**Fluxo Principal:**
1. Ator envia requisição GET para `/api/personagens/{id}`
2. Sistema busca personagem pelo ID
3. Sistema converte entidade para DTO
4. Sistema retorna o personagem

**Fluxos Alternativos:**
- **A1:** Personagem não encontrado
  - Sistema retorna erro 404

**Implementação:**
- **Controller:** `PersonagemController.buscarPorId()`
- **Service:** `PersonagemService.buscarPorId()`
- **Repository:** `PersonagemRepository.findById()`

---

### UC04: Atualizar Personagem

**Ator Principal:** Admin Game, Content Creator  
**Objetivo:** Modificar dados de um personagem existente  
**Pré-condições:** Sistema disponível, personagem existe  
**Pós-condições:** Personagem atualizado no sistema  

**Fluxo Principal:**
1. Ator envia requisição PUT para `/api/personagens/{id}`
2. Sistema valida dados de entrada
3. Sistema busca personagem pelo ID
4. Sistema atualiza os campos do personagem
5. Sistema persiste as alterações
6. Sistema retorna personagem atualizado

**Fluxos Alternativos:**
- **A1:** Personagem não encontrado
  - Sistema retorna erro 404
- **A2:** Dados inválidos
  - Sistema retorna erro 400

**Implementação:**
- **Controller:** `PersonagemController.atualizar()`
- **Service:** `PersonagemService.atualizar()`
- **Repository:** `PersonagemRepository.save()`

---

### UC05: Deletar Personagem

**Ator Principal:** Admin Game  
**Objetivo:** Remover um personagem do sistema  
**Pré-condições:** Sistema disponível, personagem existe  
**Pós-condições:** Personagem removido, rodadas associadas removidas  

**Fluxo Principal:**
1. Ator envia requisição DELETE para `/api/personagens/{id}`
2. Sistema verifica se personagem existe
3. Sistema remove personagem (CASCADE remove rodadas)
4. Sistema retorna confirmação (204 No Content)

**Fluxos Alternativos:**
- **A1:** Personagem não encontrado
  - Sistema retorna erro 404

**Implementação:**
- **Controller:** `PersonagemController.deletar()`
- **Service:** `PersonagemService.deletar()`
- **Repository:** `PersonagemRepository.deleteById()`

---

### UC06: Criar Rodada

**Ator Principal:** Content Creator, Admin Game  
**Objetivo:** Criar uma nova rodada de jogo para um personagem  
**Pré-condições:** Sistema disponível, personagem existe  
**Pós-condições:** Rodada criada e associada ao personagem  

**Fluxo Principal:**
1. Ator envia requisição POST para `/api/rodadas`
2. Sistema valida dados da rodada
3. Sistema verifica se personagem existe
4. Sistema cria nova instância de Rodada
5. Sistema associa rodada ao personagem
6. Sistema persiste a rodada
7. Sistema retorna rodada criada

**Fluxos Alternativos:**
- **A1:** Personagem não encontrado
  - Sistema retorna erro 404
- **A2:** Dados inválidos
  - Sistema retorna erro 400

**Implementação:**
- **Controller:** `RodadaController.criar()`
- **Service:** `RodadaService.criar()`
- **Repository:** `RodadaRepository.save()`, `PersonagemRepository.findById()`

---

### UC07: Listar Rodadas

**Ator Principal:** Admin Game, Content Creator, Player  
**Objetivo:** Obter lista de todas as rodadas disponíveis  
**Pré-condições:** Sistema disponível  
**Pós-condições:** Lista de rodadas retornada  

**Fluxo Principal:**
1. Ator envia requisição GET para `/api/rodadas`
2. Sistema busca todas as rodadas no banco
3. Sistema converte entidades para DTOs
4. Sistema retorna lista de rodadas

**Implementação:**
- **Controller:** `RodadaController.listar()`
- **Service:** `RodadaService.listar()`
- **Repository:** `RodadaRepository.findAll()`

---

### UC08: Buscar Rodada por ID

**Ator Principal:** Admin Game, Content Creator, Player  
**Objetivo:** Obter detalhes de uma rodada específica  
**Pré-condições:** Sistema disponível, ID válido  
**Pós-condições:** Rodada encontrada retornada  

**Fluxo Principal:**
1. Ator envia requisição GET para `/api/rodadas/{id}`
2. Sistema busca rodada pelo ID
3. Sistema converte entidade para DTO
4. Sistema retorna a rodada

**Fluxos Alternativos:**
- **A1:** Rodada não encontrada
  - Sistema retorna erro 404

**Implementação:**
- **Controller:** `RodadaController.buscarPorId()`
- **Service:** `RodadaService.buscarPorId()`
- **Repository:** `RodadaRepository.findById()`

---

### UC09: Listar Rodadas por Personagem

**Ator Principal:** Player, Content Creator, Admin Game  
**Objetivo:** Obter todas as rodadas de um personagem específico  
**Pré-condições:** Sistema disponível, personagem existe  
**Pós-condições:** Lista de rodadas do personagem retornada  

**Fluxo Principal:**
1. Ator envia requisição GET para `/api/rodadas/personagem/{personagemId}`
2. Sistema busca rodadas pelo ID do personagem
3. Sistema converte entidades para DTOs
4. Sistema retorna lista de rodadas

**Implementação:**
- **Controller:** `RodadaController.listarPorPersonagem()`
- **Service:** `RodadaService.listarPorPersonagem()`
- **Repository:** `RodadaRepository.findByPersonagemId()`

---

### UC10: Atualizar Rodada

**Ator Principal:** Content Creator, Admin Game  
**Objetivo:** Modificar dados de uma rodada existente  
**Pré-condições:** Sistema disponível, rodada existe  
**Pós-condições:** Rodada atualizada no sistema  

**Fluxo Principal:**
1. Ator envia requisição PUT para `/api/rodadas/{id}`
2. Sistema valida dados de entrada
3. Sistema busca rodada pelo ID
4. Sistema verifica se novo personagem existe (se alterado)
5. Sistema atualiza os campos da rodada
6. Sistema persiste as alterações
7. Sistema retorna rodada atualizada

**Fluxos Alternativos:**
- **A1:** Rodada não encontrada
  - Sistema retorna erro 404
- **A2:** Personagem não encontrado
  - Sistema retorna erro 404
- **A3:** Dados inválidos
  - Sistema retorna erro 400

**Implementação:**
- **Controller:** `RodadaController.atualizar()`
- **Service:** `RodadaService.atualizar()`
- **Repository:** `RodadaRepository.save()`, `PersonagemRepository.findById()`

---

### UC11: Deletar Rodada

**Ator Principal:** Content Creator, Admin Game  
**Objetivo:** Remover uma rodada do sistema  
**Pré-condições:** Sistema disponível, rodada existe  
**Pós-condições:** Rodada removida do sistema  

**Fluxo Principal:**
1. Ator envia requisição DELETE para `/api/rodadas/{id}`
2. Sistema verifica se rodada existe
3. Sistema remove a rodada
4. Sistema retorna confirmação (204 No Content)

**Fluxos Alternativos:**
- **A1:** Rodada não encontrada
  - Sistema retorna erro 404

**Implementação:**
- **Controller:** `RodadaController.deletar()`
- **Service:** `RodadaService.deletar()`
- **Repository:** `RodadaRepository.deleteById()`

## Mapeamento Casos de Uso → Serviços

### PersonagemService

| Caso de Uso | Método do Serviço | Responsabilidade |
|-------------|-------------------|------------------|
| UC01 | `criar(PersonagemDTO)` | Validar e criar personagem |
| UC02 | `listar()` | Buscar e converter todos personagens |
| UC03 | `buscarPorId(Long)` | Buscar personagem específico |
| UC04 | `atualizar(Long, PersonagemDTO)` | Atualizar dados do personagem |
| UC05 | `deletar(Long)` | Remover personagem |

### RodadaService

| Caso de Uso | Método do Serviço | Responsabilidade |
|-------------|-------------------|------------------|
| UC06 | `criar(RodadaDTO)` | Validar e criar rodada |
| UC07 | `listar()` | Buscar e converter todas rodadas |
| UC08 | `buscarPorId(Long)` | Buscar rodada específica |
| UC09 | `listarPorPersonagem(Long)` | Buscar rodadas por personagem |
| UC10 | `atualizar(Long, RodadaDTO)` | Atualizar dados da rodada |
| UC11 | `deletar(Long)` | Remover rodada |

## Regras de Negócio Implementadas

### Personagem
- **RN01:** Nome do personagem é obrigatório
- **RN02:** Papel do personagem é obrigatório
- **RN03:** Descrição do personagem é obrigatória
- **RN04:** ID deve ser único e gerado automaticamente
- **RN05:** Exclusão de personagem remove todas rodadas associadas

### Rodada
- **RN06:** Descrição da rodada é obrigatória
- **RN07:** Ambas escolhas (A e B) são obrigatórias
- **RN08:** Ambas consequências (A e B) são obrigatórias
- **RN09:** Rodada deve estar associada a um personagem válido
- **RN10:** ID deve ser único e gerado automaticamente

## Validações Implementadas

### Entrada de Dados
- Validação de campos obrigatórios via Bean Validation
- Validação de tipos de dados
- Validação de integridade referencial

### Saída de Dados
- Conversão segura Entity → DTO
- Tratamento de exceções
- Códigos HTTP apropriados

## Tratamento de Exceções

### Exceções de Negócio
- `RuntimeException`: "Personagem não encontrado"
- `RuntimeException`: "Rodada não encontrada"

### Códigos HTTP
- **200 OK**: Operação bem-sucedida
- **201 Created**: Recurso criado (implícito via 200)
- **204 No Content**: Exclusão bem-sucedida
- **400 Bad Request**: Dados inválidos
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno

## Extensibilidade dos Casos de Uso

### Casos de Uso Futuros Possíveis
- **UC12:** Autenticar Usuário
- **UC13:** Registrar Escolha do Jogador
- **UC14:** Calcular Pontuação
- **UC15:** Gerar Relatório de Performance
- **UC16:** Exportar Dados do Jogo
- **UC17:** Importar Cenários
- **UC18:** Gerenciar Categorias de Personagens

### Pontos de Extensão
- Interface de serviços permite implementações alternativas
- DTOs podem ser estendidos com novos campos
- Repositórios podem ter queries customizadas adicionadas
- Controllers podem ter novos endpoints
