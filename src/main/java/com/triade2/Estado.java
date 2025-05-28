package com.triade2;

public class Estado {
    private String codEstado;
    private String nome;

    public Estado(String nome) {
        this.nome = nome;
    }

    public Estado(String codEstado, String nome) {
        this.codEstado = codEstado;
        this.nome = nome;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
