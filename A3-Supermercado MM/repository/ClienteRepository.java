package repository;

import model.Cliente;
import util.CsvUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável pela persistência de Cliente em CSV.
 * Pessoa 4 — Estoque + CSV
 */
public class ClienteRepository {

    private final String caminhoArquivo = "data/clientes.csv";
    private final String cabecalho = "cpf;nome;telefone;email";

    public ClienteRepository() {
        // Cria a pasta 'data' caso ela não exista no computador do professor
        File pasta = new File("data");
        if (!pasta.exists()) {
            pasta.mkdir();
        }
    }

    /**
     * Salva a lista de objetos Cliente no arquivo CSV.
     */
    public void salvarTodos(List<Cliente> clientes) {
        List<String> linhas = new ArrayList<>();
        for (Cliente c : clientes) {
            linhas.add(c.toCSV());
        }
        CsvUtil.salvarArquivo(caminhoArquivo, cabecalho, linhas);
    }

    /**
     * Lê o arquivo CSV e converte as linhas de texto de volta para objetos Cliente.
     */
    public List<Cliente> lerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        List<String> linhas = CsvUtil.lerArquivo(caminhoArquivo);

        for (String linha : linhas) {
            String[] dados = linha.split(";");

            // Valida se a linha possui as 4 colunas corretas do Cliente
            if (dados.length == 4) {
                clientes.add(new Cliente(dados[0], dados[1], dados[2], dados[3]));
            }
        }

        return clientes;
    }
}