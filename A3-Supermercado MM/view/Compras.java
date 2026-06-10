package view;

import model.Cliente;
import model.Produto;
import model.ItemCarrinho;
import repository.ClienteRepository;
import repository.ProdutoRepository;
import util.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Compras {

    private Scanner leitor;
    private ClienteRepository clienteRepo = new ClienteRepository();
    private ProdutoRepository produtoRepo  = new ProdutoRepository();

    public Compras(Scanner leitor) {
        this.leitor = leitor;
    }

    // ─────────────────────────────────────────────────────────────
    // Ponto de entrada chamado pelo menu principal
    // ─────────────────────────────────────────────────────────────
    public void iniciar() {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("          REALIZAR COMPRA           ");
        System.out.println("====================================");

        // 1. Identificar cliente
        Cliente cliente = identificarCliente();
        if (cliente == null) return; // fluxo cancelado

        // 2. Montar carrinho
        List<ItemCarrinho> carrinho = montarCarrinho();
        if (carrinho.isEmpty()) {
            System.out.println("\nNenhum produto adicionado. Compra cancelada.");
            pausar();
            return;
        }

        // 3. Exibir recibo e confirmar
        exibirRecibo(cliente, carrinho);

        System.out.print("\nConfirmar compra? (Sim/Não): ");
        String confirmacao = leitor.nextLine().trim().toUpperCase();
        if (!confirmacao.equals("SIM") && !confirmacao.equals("S") ) {
            System.out.println("\nCompra cancelada.");
            pausar();
            return;
        }

        // 4. Atualizar estoque e salvar CSV
        atualizarEstoque(carrinho);
        System.out.println("\nCompra realizada com sucesso!");
        pausar();
    }

    // ─────────────────────────────────────────────────────────────
    // Passo 1 — Identificar cliente (cadastrado ou anônimo)
    // ─────────────────────────────────────────────────────────────
    private Cliente identificarCliente() {
        System.out.print("\nVocê é um cliente cadastrado? (Sim/Não): ");
        String resposta = leitor.nextLine().trim().toUpperCase();

        if (resposta.equals("S")) {
            System.out.print("Digite o CPF: ");
            String cpf = leitor.nextLine().trim();

            List<Cliente> clientes = clienteRepo.lerTodos();
            for (Cliente c : clientes) {
                if (c.getCpf().equals(cpf)) {
                    System.out.println("Cliente identificado: " + c.getNome());
                    return c;
                }
            }
            System.out.println("CPF não encontrado. Prosseguindo como anônimo.");
        }

        // Cliente anônimo — cria objeto temporário apenas para o recibo
        return new Cliente("(Anônimo)", "", "", "");
    }

    // ─────────────────────────────────────────────────────────────
    // Passo 2 — Montar carrinho adicionando produtos por código
    // ─────────────────────────────────────────────────────────────
    private List<ItemCarrinho> montarCarrinho() {
        List<ItemCarrinho> carrinho = new ArrayList<>();
        List<Produto> produtos = produtoRepo.lerTodos();

        while (true) {
            Screen.clear();
            System.out.println("====================================");
            System.out.println("            CARRINHO                ");
            System.out.println("====================================");

            // Exibe itens já no carrinho
            if (carrinho.isEmpty()) {
                System.out.println("(carrinho vazio)");
            } else {
                for (ItemCarrinho item : carrinho) {
                    System.out.println("  " + item);
                }
            }

            System.out.println("------------------------------------");
            System.out.println("Digite o CÓDIGO do produto para adicionar");
            System.out.println("ou 0 para finalizar:");
            System.out.print("Código: ");

            String entrada = leitor.nextLine().trim();

            if (entrada.equals("0")) break;

            // Localiza o produto pelo código
            Produto produtoEscolhido = null;
            for (Produto p : produtos) {
                if (p.getCodigo().equals(entrada)) {
                    produtoEscolhido = p;
                    break;
                }
            }

            if (produtoEscolhido == null) {
                System.out.println("Produto não encontrado.");
                pausar();
                continue;
            }

            // Lê quantidade desejada
            int qtdDesejada = lerInteiro("Quantidade desejada: ");
            if (qtdDesejada <= 0) {
                System.out.println("Quantidade inválida.");
                pausar();
                continue;
            }

            // Valida estoque
            if (qtdDesejada > produtoEscolhido.getEstoque()) {
                System.out.println("Estoque insuficiente! Disponível: "
                        + produtoEscolhido.getEstoque());
                pausar();
                continue;
            }

            // Verifica se o produto já está no carrinho (agrupa)
            boolean jaNoCarrinho = false;
            for (ItemCarrinho item : carrinho) {
                if (item.getProduto().getCodigo().equals(produtoEscolhido.getCodigo())) {
                    int novaQtd = item.getQuantidade() + qtdDesejada;
                    if (novaQtd > produtoEscolhido.getEstoque()) {
                        System.out.println("Estoque insuficiente para essa quantidade total.");
                        pausar();
                    } else {
                        item.setQuantidade(novaQtd);
                        System.out.println("Quantidade atualizada no carrinho.");
                        pausar();
                    }
                    jaNoCarrinho = true;
                    break;
                }
            }

            if (!jaNoCarrinho) {
                carrinho.add(new ItemCarrinho(produtoEscolhido, qtdDesejada));
                System.out.println(produtoEscolhido.getNome() + " adicionado ao carrinho!");
                pausar();
            }
        }

        return carrinho;
    }

    // ─────────────────────────────────────────────────────────────
    // Passo 3 — Exibir recibo detalhado
    // ─────────────────────────────────────────────────────────────
    private void exibirRecibo(Cliente cliente, List<ItemCarrinho> carrinho) {
        Screen.clear();
        System.out.println("====================================");
        System.out.println("             RECIBO                 ");
        System.out.println("====================================");
        System.out.println("Cliente : " + cliente.getNome());
        System.out.println("------------------------------------");
        System.out.printf("%-20s %5s %10s %12s%n",
                "Produto", "Qtd", "Unit.", "Subtotal");
        System.out.println("------------------------------------");

        double total = 0;
        for (ItemCarrinho item : carrinho) {
            double subtotal = item.getProduto().getPreco() * item.getQuantidade();
            total += subtotal;
            System.out.printf("%-20s %5d %10s %12s%n",
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    "R$ " + String.format("%.2f", item.getProduto().getPreco()),
                    "R$ " + String.format("%.2f", subtotal));
        }

        System.out.println("====================================");
        System.out.printf("%-38s %s%n", "TOTAL:",
                "R$ " + String.format("%.2f", total));
        System.out.println("====================================");
    }

    // ─────────────────────────────────────────────────────────────
    // Passo 4 — Debitar estoque e persistir no CSV
    // ─────────────────────────────────────────────────────────────
    private void atualizarEstoque(List<ItemCarrinho> carrinho) {
        List<Produto> produtos = produtoRepo.lerTodos();

        for (ItemCarrinho item : carrinho) {
            for (Produto p : produtos) {
                if (p.getCodigo().equals(item.getProduto().getCodigo())) {
                    p.setEstoque(p.getEstoque() - item.getQuantidade());
                    break;
                }
            }
        }

        produtoRepo.salvarTodos(produtos); // persiste no CSV
    }

    // ─────────────────────────────────────────────────────────────
    // Utilitários
    // ─────────────────────────────────────────────────────────────
    private int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(leitor.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros.");
            }
        }
    }

    private void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        leitor.nextLine();
    }
}