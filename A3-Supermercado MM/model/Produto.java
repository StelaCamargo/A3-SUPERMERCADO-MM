package model;

public class Produto {
    private String codigo;
    private String nome;
    private double preco;
    private int estoque;

    public Produto(String codigo, String nome, double preco, int estoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    // Getters
    public String getCodigo()  { return codigo; }
    public String getNome()    { return nome; }
    public double getPreco()   { return preco; }
    public int getEstoque()    { return estoque; }

    // Setters
    public void setCodigo(String codigo)   { this.codigo = codigo; }
    public void setNome(String nome)       { this.nome = nome; }
    public void setPreco(double preco)     { this.preco = preco; }
    public void setEstoque(int estoque)    { this.estoque = estoque; }

    public String toCSV() {
        return codigo + ";" + nome + ";" + preco + ";" + estoque;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo +
               " | Nome: " + nome +
               " | Preco: R$ " + String.format("%.2f", preco) +
               " | Estoque: " + estoque;
    }
}