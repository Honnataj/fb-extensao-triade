package com.triade2;

public class Bairro {
    private int codBairro;
    private String nome;
    private int codMunicipio;

    public Bairro(String nome, int codMunicipio) {
        this.nome = nome;
        this.codMunicipio = codMunicipio;
    }

    public Bairro(int codBairro, String nome, int codMunicipio) {
        this(nome, codMunicipio);
        this.codBairro = codBairro;
    }

    public int getCodBairro() {
        return codBairro;
    }

    public void setCodBairro(int codBairro) {
        this.codBairro = codBairro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public void setCodMunicipio(int codMunicipio) {
        this.codMunicipio = codMunicipio;
    }
}
