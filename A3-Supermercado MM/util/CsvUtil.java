package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    /**
     * Lê um arquivo CSV e retorna as linhas sem o cabeçalho.
     */
    public static List<String> lerArquivo(String caminho) {
        List<String> linhas = new ArrayList<>();

        File arquivo = new File(caminho);
        if (!arquivo.exists()) return linhas; // retorna lista vazia se não existir

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; // pula o cabeçalho
                    continue;
                }
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + caminho);
        }

        return linhas;
    }

    /**
     * Salva uma lista de linhas em um arquivo CSV com cabeçalho.
     */
    public static void salvarArquivo(String caminho, String cabecalho, List<String> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write(cabecalho);
            bw.newLine();

            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + caminho);
        }
    }
}