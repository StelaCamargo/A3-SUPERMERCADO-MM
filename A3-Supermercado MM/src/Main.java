import view.Clientes;
import view.Compras;
import view.MenuProduto;

import java.util.Scanner;

public class Main {
    private static Scanner leitor = new Scanner(System.in);
    public static void main(String[] args) {

        int opcao = -1;

        while (opcao != 0) {
            limparTela();
            System.out.println("====================================");
            System.out.println("       SISTEMA MINI MERCADO         ");
            System.out.println("====================================");
            System.out.println("1. Produtos");
            System.out.println("2. Clientes");
            System.out.println("3. Realizar Compra");
            System.out.println("4. Controle de Estoque");
            System.out.println("0. Sair");
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
                case 1:
                    new MenuProduto(leitor).exibirMenu();
                    break;
                case 2:
                    new Clientes(leitor).exibirMenu();
                    break;
                case 3:
                    new Compras(leitor).iniciar();
                    break;
                case 4:
                    limparTela();
                    System.out.println("====================================");
                    System.out.println("       CONTROLE DE ESTOQUE          ");
                    System.out.println("====================================");
                    System.out.println("O estoque é atualizado automaticamente");
                    System.out.println("a cada compra realizada.");
                    System.out.println("Para consultar, acesse:");
                    System.out.println("  1. Produtos > Listar Produtos");
                    System.out.println("  2. Produtos > Buscar Produto");
                    pausar();
                    break;
                case 0:
                    System.out.println("\nSistema encerrado. Ate logo!");
                    break;
                default:
                    System.out.println("Opcao invalida! Tente novamente.");
                    pausar();
            }
        }

        leitor.close();
    }

    private static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 30; i++) System.out.println();
        }
    }

    private static void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        leitor.nextLine();
    }
}