package model;

public class Cliente {
    private String cpf;
    private String nome;
    private String telefone;
    private String email;

    public Cliente(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters
    public String getCpf()                   { return cpf; }
    public void setCpf(String cpf)           { this.cpf = cpf; }

    public String getNome()                  { return nome; }
    public void setNome(String nome)         { this.nome = nome; }

    public String getTelefone()              { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail()                 { return email; }
    public void setEmail(String email)       { this.email = email; }

    public String toCSV() {
        return cpf + ";" + nome + ";" + telefone + ";" + email;
    }

    @Override
    public String toString() {
        return "CPF: " + cpf +
               " | Nome: " + nome +
               " | Telefone: " + telefone +
               " | Email: " + email;
    }
}