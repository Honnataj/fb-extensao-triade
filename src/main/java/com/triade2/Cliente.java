package com.triade2;

public class Cliente {
    private int codigoCliente;
    private String nomeCliente;
    private String telefoneCliente;

    public Cliente(String nomeCliente, String telefoneCliente) {
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
    }

    public Cliente(int codigoCliente, String nomeCliente, String telefoneCliente) {
        this(nomeCliente, telefoneCliente);
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigo) {
        this.codigoCliente = codigo;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nome) {
        this.nomeCliente = nome;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefone) {
        this.telefoneCliente = telefone;
    }
}
