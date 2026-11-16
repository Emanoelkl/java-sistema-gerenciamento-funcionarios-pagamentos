#  Sistema de Gerenciamento de Funcionários e Folha de Pagamento

Sistema desenvolvido em **Java** com **Maven** e **JUnit 5** para gerenciar colaboradores de diferentes regimes de contratação, calcular suas remunerações e tributos, e gerar relatórios consolidados da folha de pagamento.

O projeto foi construído aplicando **Programação Orientada a Objetos (POO)**, **Arquitetura em Camadas**, **Tratamento de Exceções Personalizadas** e **Desenvolvimento Orientado a Testes (TDD)**.

---

##  Funcionalidades

* **Cadastro de Funcionários:** Suporte para três categorias distintas (`CLT`, `PJ` e `Comissionado`), com validação de dados obrigatórios e bloqueio de duplicidade (código e CPF).
* **Consultas Flexíveis:** Busca de colaboradores por código identificador, CPF ou nome (busca exata ou parcial).
* **Cálculo de Remuneração e Tributos:** Cálculo polimórfico individual conforme as regras de cada categoria.
* **Gestão de Registros:** Listagem completa, exibição detalhada de dados e remoção de funcionários cadastrados.
* **Relatórios e Fechamento:** Geração de relatório detalhado da folha e cálculo do valor total pago pela empresa.

---

##  Conceitos e Arquitetura Aplicados

### Programação Orientada a Objetos (POO)
* **Encapsulamento:** Todos os atributos são privados (`private`), com validações aplicadas diretamente nos construtores e métodos *setters*.
* **Abstração e Herança:** Classe abstrata `Funcionario` definindo o contrato base e atributos comuns para as subclasses `FuncionarioCLT`, `FuncionarioPJ` e `FuncionarioComissionado`.
* **Interfaces:**
    * `Identificavel`: Garante que toda entidade possua um código identificador único.
    * `Tributavel`: Define o contrato para cálculo de impostos/descontos aplicáveis.
* **Polimorfismo:**
    * **Sobrescrita (`@Override`):** Método `calcularRemuneracao()` implementado de forma específica em cada subclasse.
    * **Sobrecarga (*Overload*):** Métodos `buscarFuncionario` na camada `repository` com diferentes assinaturas (`int codigo`, `String cpf` e `String nome, boolean buscaExata`).

### Tratamento de Exceções
* Exceções personalizadas para regras de negócio: `FuncionarioJaCadastradoException` e `FuncionarioNaoEncontradoException`.
* Validações de integridade de dados (CPF, valores negativos, percentuais e campos obrigatórios) utilizando `IllegalArgumentException`.

### Arquitetura em Camadas
```text
src/
├── main/java/com/[seunome]/[projeto]/
│   ├── exception/       # Exceções personalizadas do domínio
│   ├── model/           # Entidades, classes abstratas e interfaces
│   ├── repository/      # Armazenamento em memória e consultas
│   ├── service/         # Regras de negócio, cálculos e relatórios
│   ├── view/            # Interface de interação via console (CLI)
│   └── Main.java        # Ponto de entrada e injeção de dependências
└── test/java/com/[seunome]/[projeto]/
    ├── exception/       # Testes unitários das exceções
    ├── model/           # Testes de cálculo, polimorfismo e validações
    ├── repository/      # Testes de persistência e sobrecarga de buscas
    └── service/         # Testes de regras de negócio e folha de pagamento
```

---

##  Tecnologias Utilizadas

* **Java** (JDK 17+)
* **Apache Maven** (Gerenciamento de dependências e build)
* **JUnit 5 (Jupiter)** (Testes unitários e TDD)

---

##  Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Maven instalado (ou utilizar o Maven integrado da sua IDE, como IntelliJ IDEA, Eclipse ou VS Code).

### Passo a passo
1. Clone este repositório:
   ```bash
   git clone https://github.com/Emanoelkl/java-sistema-gerenciamento-funcionarios-pagamentos.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd java-sistema-gerenciamento-funcionarios-pagamentos
   ```
3. Compile o projeto e execute todos os testes unitários com o Maven:
   ```bash
   mvn test
   ```
4. Execute a classe `Main.java` pela sua IDE ou via terminal para iniciar o menu interativo no console.

---

##  Testes Automatizados (TDD)

Todo o núcleo do sistema foi desenvolvido seguindo o ciclo **Red → Green → Refactor**, garantindo cobertura para cenários de sucesso, regras de exceção, validações de entrada e comportamento polimórfico entre as categorias de funcionários.

---