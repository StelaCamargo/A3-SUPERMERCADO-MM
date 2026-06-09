package view;

import model.Cliente;
import repository.ClienteRepository;
import util.Screen;

import java.util.List;
import java.util.Scanner;

/**
 * Menu de gerenciamento de Clientes (CRUD completo).
 * Pessoa 2 — Clientes
 */
public class Clientes {

    private Scanner leitor;
    private ClienteRepository repo = new ClienteRepository();

    public Clientes(Scanner leitor) {
        this.leitor = leitor;
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            Screen.clear();
            System.out.println("====================================");
            System.out.println("          GERENCIAR CLIENTES        ");
            System.out.println("====================================");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente");
            System.out.println("4. Alterar Cliente");
            System.out.println("5. Remover Cliente");
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
        System.out.println("        CADASTRAR CLIENTE           ");
        System.out.println("====================================");

        System.out.print("Digite o CPF: ");
        String cpf = leitor.nextLine().trim();

        List<Cliente> clientes = repo.lerTodos();
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("\nErro: Ja existe um cliente com este CPF!");
                pausar();
                return;
            }
        }

        System.out.print("Digite o Nome: ");
        String nome = leitor.nextLine().trim();
        System.out.print("Digite o Telefone: ");
        String telefone = leitor.nextLine().trim();
        System.out.print("Digite o Email: ");
        String email = leitor.nextLine().trim();

        clientes.add(new Cliente(cpf, nome, telefone, email));
        repo.salvarTodos(clientes);

        System.out.println("\nCliente cadastrado com sucesso!");
        pausar();
    }

    // 2. READ — listar
    private void listar() {
        Screen.clear();
        List<Cliente> clientes = repo.lerTodos();

        System.out.println("====================================");
        System.out.println("         LISTA DE CLIENTES          ");
        System.out.println("====================================");

        if (clientes.isEmpty()) {
            System.out.println("(Ainda nao ha clientes cadastrados)");
        } else {
            for (Cliente c : clientes) {
                System.out.println(c);
            }
        }
        pausar();
    }

    // 3. READ — buscar por CPF
    private void buscar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          BUSCAR CLIENTE            ");
        System.out.println("====================================");
        System.out.print("Digite o CPF para busca: ");
        String cpf = leitor.nextLine().trim();

        List<Cliente> clientes = repo.lerTodos();
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("\nCliente encontrado:");
                System.out.println(c);
                pausar();
                return;
            }
        }
        System.out.println("\nCliente nao encontrado.");
        pausar();
    }

    // 4. UPDATE
    private void alterar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          ALTERAR CLIENTE           ");
        System.out.println("====================================");
        System.out.print("Digite o CPF do cliente a alterar: ");
        String cpf = leitor.nextLine().trim();

        List<Cliente> clientes = repo.lerTodos();
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("\nCliente atual: " + c.getNome());
                System.out.print("Novo Nome: ");
                c.setNome(leitor.nextLine().trim());
                System.out.print("Novo Telefone: ");
                c.setTelefone(leitor.nextLine().trim());
                System.out.print("Novo Email: ");
                c.setEmail(leitor.nextLine().trim());

                repo.salvarTodos(clientes);
                System.out.println("\nDados alterados com sucesso!");
                pausar();
                return;
            }
        }
        System.out.println("\nCPF nao localizado.");
        pausar();
    }

    // 5. DELETE
    private void remover() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          REMOVER CLIENTE           ");
        System.out.println("====================================");
        System.out.print("Digite o CPF do cliente a remover: ");
        String cpf = leitor.nextLine().trim();

        List<Cliente> clientes = repo.lerTodos();
        boolean removido = clientes.removeIf(c -> c.getCpf().equals(cpf));

        if (removido) {
            repo.salvarTodos(clientes);
            System.out.println("\nCliente removido com sucesso!");
        } else {
            System.out.println("\nCPF nao encontrado.");
        }
        pausar();
    }

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        leitor.nextLine();
    }
}