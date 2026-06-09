package repository;

import model.Produto;
import util.CsvUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProdutoRepository {

    private final String caminhoArquivo = "data/produtos.csv";
    private final String cabecalho = "codigo;nome;preco;estoque";

    public ProdutoRepository() {
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }
    }

    /**
     * Salva a lista de produtos no arquivo CSV.
     */
    public void salvarTodos(List<Produto> produtos) {
        List<String> linhas = new ArrayList<>();
        for (Produto p : produtos) {
            linhas.add(p.toCSV());
        }
        CsvUtil.salvarArquivo(caminhoArquivo, cabecalho, linhas);
    }

    /**
     * Lê o arquivo CSV e retorna a lista de produtos.
     */
    public List<Produto> lerTodos() {
        List<Produto> produtos = new ArrayList<>();
        List<String> linhas = CsvUtil.lerArquivo(caminhoArquivo);

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            if (dados.length == 4) {
                try {
                    String codigo  = dados[0];
                    String nome    = dados[1];
                    double preco   = Double.parseDouble(dados[2].replace(",", "."));
                    int estoque    = Integer.parseInt(dados[3]);
                    produtos.add(new Produto(codigo, nome, preco, estoque));
                } catch (NumberFormatException e) {
                    System.out.println("Linha ignorada (formato inválido): " + linha);
                }
            }
        }

        return produtos;
    }
}