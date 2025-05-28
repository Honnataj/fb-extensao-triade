package com.triade2;

public class Endereco {
    private int codEndereco;
    private String logradouro;
    private int numero;
    private String complemento;
    private int cep;
    private int codBairro;

    public Endereco(String logradouro, int numero, String complemento,
                    int cep, int codBairro) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.codBairro = codBairro;
    }

    public Endereco(int codEndereco, String logradouro, int numero,
                    String complemento, int cep, int codBairro) {
        this(logradouro, numero, complemento, cep, codBairro);
        this.codEndereco = codEndereco;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public int getCodBairro() {
        return codBairro;
    }

    public void setCodBairro(int codBairro) {
        this.codBairro = codBairro;
    }
}
