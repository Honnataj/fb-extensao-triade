package com.triade2;

public class Municipio {
    private int codMunicipio;
    private String nome;
    private String codEstado;

    public Municipio(String nome, String codEstado) {
        this.nome = nome;
        this.codEstado = codEstado;
    }

    public Municipio(int codMunicipio, String nome, String codEstado) {
        this(nome, codEstado);
        this.codMunicipio = codMunicipio;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }
}
