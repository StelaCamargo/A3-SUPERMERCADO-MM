# A3-SUPERMERCADO-MM

# 🛒 Sistema de Mini Mercado — Projeto A3

Sistema de gerenciamento de mini mercado desenvolvido em **Java**, executado inteiramente via terminal (console). Permite o gerenciamento de produtos, clientes e realização de compras com controle de estoque e persistência de dados em arquivos CSV.

---

## 📋 Funcionalidades

- **CRUD de Produtos** — cadastrar, listar, buscar, alterar e remover produtos
- **CRUD de Clientes** — cadastrar, listar, buscar, alterar e remover clientes
- **Realização de Compra** — suporte a clientes cadastrados e anônimos, com validação de estoque e exibição de recibo
- **Controle de Estoque** — atualização automática do estoque após cada compra
- **Persistência em CSV** — dados salvos e carregados automaticamente nos arquivos da pasta `data/`

---

## 🗂️ Estrutura de Menus

```
Menu Principal
├── 1. Produtos
│   ├── 1. Cadastrar Produto
│   ├── 2. Listar Produtos
│   ├── 3. Buscar Produto
│   ├── 4. Alterar Produto
│   ├── 5. Remover Produto
│   └── 0. Voltar
├── 2. Clientes
│   ├── 1. Cadastrar Cliente
│   ├── 2. Listar Clientes
│   ├── 3. Buscar Cliente
│   ├── 4. Alterar Cliente
│   ├── 5. Remover Cliente
│   └── 0. Voltar
├── 3. Realizar Compra
├── 4. Controle de Estoque
└── 0. Sair
```

---

## 🏗️ Arquitetura do Projeto

O projeto segue uma arquitetura em camadas, separando responsabilidades por pacotes:

| Pacote | Responsabilidade |
|---|---|
| `model` | Classes de entidade (`Cliente`, `Produto`, `ItemCarrinho`) |
| `repository` | Leitura e escrita nos arquivos CSV |
| `util` | Utilitários: manipulação de CSV e formatação de tela |
| `view` | Menus e interação com o usuário no terminal |
| `src` | Classe principal (`Main.java`) |
| `data` | Arquivos `.csv` com os dados persistidos |

---

## 🧱 Classes de Modelo

### `Produto`
| Atributo | Tipo | Descrição |
|---|---|---|
| `codigo` | `int` | Identificador único do produto |
| `nome` | `String` | Nome do produto |
| `preco` | `double` | Valor unitário |
| `estoque` | `int` | Quantidade disponível em estoque |

### `Cliente`
| Atributo | Tipo | Descrição |
|---|---|---|
| `cpf` | `String` | CPF do cliente |
| `nome` | `String` | Nome completo |
| `telefone` | `String` | Telefone de contato |
| `email` | `String` | Endereço de e-mail |

### `ItemCarrinho`
Representa um item adicionado durante uma compra, associando produto e quantidade.

---

## 💾 Persistência de Dados

Os dados são armazenados na pasta `data/` em formato `.csv`:

| Arquivo | Conteúdo |
|---|---|
| `produtos.csv` | Cadastro de produtos e estoque |
| `clientes.csv` | Cadastro de clientes |

Exemplo de formato:

```csv
codigo;nome;preco;estoque
1;Coca-Cola;5.50;10
2;Chocolate;3.00;20
```

Os arquivos são carregados automaticamente ao iniciar o sistema e atualizados a cada operação.

---

## 📁 Estrutura do Projeto

```
mini-mercado/
├── src/
│   └── Main.java
├── model/
│   ├── Cliente.java
│   ├── ItemCarrinho.java
│   └── Produto.java
├── repository/
│   ├── ClienteRepository.java
│   └── ProdutoRepository.java
├── util/
│   ├── CsvUtil.java
│   └── Screen.java
├── view/
│   ├── Clientes.java
│   ├── Compras.java
│   └── MenuProduto.java
├── data/
│   ├── clientes.csv
│   └── produtos.csv
└── README.md
```

---

## ▶️ Como Executar

### Pré-requisitos

- [Java JDK 11+](https://www.oracle.com/java/technologies/downloads/) instalado
- Terminal (CMD, PowerShell, Bash, Git Bash, etc.)

### Opção 1 — Pela IDE (recomendado)

Importe a pasta do projeto no **IntelliJ IDEA**, **Eclipse** ou **VS Code** e execute a classe `Main.java`.

### Opção 2 — Pelo terminal

**1. Clone o repositório:**

```bash
git clone https://github.com/StelaCamargo/A3-SUPERMERCADO-MM
```

**2. Compile:**

```bash
javac -d bin src/Main.java model/*.java repository/*.java util/*.java view/*.java
```

**3. Execute:**

```bash
java -cp bin Main
```

---

## 👥 Integrantes do Grupo
| Nome                       | |    RA     |
| Amanda Araujo de Almeida   | |82614684   |
| Anna Clara da Silva        | | 826126539 |
| Leticia Salinas            | |8261109906 |
| Stela de Camargo dos Santos| |8261110656 |
| Vinícius Brandão Ambrosio  | |826184010  |


---

## 📌 Observações

- O sistema funciona **exclusivamente no terminal**, sem interface gráfica.
- **Não utiliza banco de dados** — toda persistência é feita em arquivos CSV.
- Possui tratamento de erros e validações de entrada de dados.
