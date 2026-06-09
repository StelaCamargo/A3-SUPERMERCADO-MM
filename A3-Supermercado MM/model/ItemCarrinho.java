package model;

public class ItemCarrinho {
    private Produto produto;
    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto()        { return produto; }
    public int getQuantidade()         { return quantidade; }
    public void setQuantidade(int qtd) { this.quantidade = qtd; }

    @Override
    public String toString() {
        double subtotal = produto.getPreco() * quantidade;
        return produto.getNome() + " x" + quantidade +
               " = R$ " + String.format("%.2f", subtotal);
    }
}