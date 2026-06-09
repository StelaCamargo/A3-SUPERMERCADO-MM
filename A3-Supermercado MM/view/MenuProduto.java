package view;

import model.Produto;
import repository.ProdutoRepository;
import util.Screen;

import java.util.List;
import java.util.Scanner;

public class MenuProduto {

    private Scanner leitor;
    private ProdutoRepository repo = new ProdutoRepository();

    public MenuProduto(Scanner leitor) {
        this.leitor = leitor;
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            Screen.clear();
            System.out.println("====================================");
            System.out.println("         GERENCIAR PRODUTOS         ");
            System.out.println("====================================");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Buscar Produto");
            System.out.println("4. Alterar Produto");
            System.out.println("5. Remover Produto");
            System.out.println("0. Voltar");
            System.out.println("====================================");
            System.out.print("Escolha uma opcao: ");

            if (leitor.hasNextInt()) {
                opcao = leitor.nextInt();
                leitor.nextLine();
            } else {
                System.out.println("Erro: Digite apenas numeros!");
                leitor.nextLine();
                pausar();
                continue;
            }

            switch (opcao) {
                case 1: cadastrar(); break;
                case 2: listar();    break;
                case 3: buscar();    break;
                case 4: alterar();   break;
                case 5: remover();   break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opcao invalida!"); pausar();
            }
        }
    }

    // 1. CREATE
    private void cadastrar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("        CADASTRAR PRODUTO           ");
        System.out.println("====================================");

        System.out.print("Digite o Codigo: ");
        String codigo = leitor.nextLine().trim();

        // Verifica código duplicado
        List<Produto> produtos = repo.lerTodos();
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) {
                System.out.println("\nErro: Ja existe um produto com este codigo!");
                pausar();
                return;
            }
        }

        System.out.print("Digite o Nome: ");
        String nome = leitor.nextLine().trim();

        double preco = lerDouble("Digite o Preco: ");
        int estoque  = lerInteiro("Digite o Estoque: ");

        produtos.add(new Produto(codigo, nome, preco, estoque));
        repo.salvarTodos(produtos);

        System.out.println("\nProduto cadastrado com sucesso!");
        pausar();
    }

    // 2. READ — listar
    private void listar() {
        Screen.clear();
        List<Produto> produtos = repo.lerTodos();

        System.out.println("====================================");
        System.out.println("         LISTA DE PRODUTOS          ");
        System.out.println("====================================");

        if (produtos.isEmpty()) {
            System.out.println("(Nenhum produto cadastrado)");
        } else {
            for (Produto p : produtos) {
                System.out.println(p);
            }
        }
        pausar();
    }

    // 3. READ — buscar por código
    private void buscar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          BUSCAR PRODUTO            ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo: ");
        String codigo = leitor.nextLine().trim();

        List<Produto> produtos = repo.lerTodos();
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) {
                System.out.println("\nProduto encontrado:");
                System.out.println(p);
                pausar();
                return;
            }
        }
        System.out.println("\nProduto nao encontrado.");
        pausar();
    }

    // 4. UPDATE
    private void alterar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          ALTERAR PRODUTO           ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo do produto a alterar: ");
        String codigo = leitor.nextLine().trim();

        List<Produto> produtos = repo.lerTodos();
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) {
                System.out.println("\nProduto atual: " + p.getNome());
                System.out.print("Novo Nome: ");
                p.setNome(leitor.nextLine().trim());
                p.setPreco(lerDouble("Novo Preco: "));
                p.setEstoque(lerInteiro("Novo Estoque: "));

                repo.salvarTodos(produtos);
                System.out.println("\nProduto alterado com sucesso!");
                pausar();
                return;
            }
        }
        System.out.println("\nProduto nao encontrado.");
        pausar();
    }

    // 5. DELETE
    private void remover() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          REMOVER PRODUTO           ");
        System.out.println("====================================");
        System.out.print("Digite o Codigo do produto a remover: ");
        String codigo = leitor.nextLine().trim();

        List<Produto> produtos = repo.lerTodos();
        boolean removido = produtos.removeIf(p -> p.getCodigo().equals(codigo));

        if (removido) {
            repo.salvarTodos(produtos);
            System.out.println("\nProduto removido com sucesso!");
        } else {
            System.out.println("\nProduto nao encontrado.");
        }
        pausar();
    }

    // Utilitários
    private double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(leitor.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Use numeros (ex: 9.90)");
            }
        }
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(leitor.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros inteiros.");
            }
        }
    }

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        leitor.nextLine();
    }
}