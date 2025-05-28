package com.triade2;

public class Cliente {
    private int codCliente;
    private String razaoSocial;
    private String nomeFantasia;
    private int cnpj;
    private int codEndereco;
    private String telefone;
    private String celular;
    private String email;
    private String responsavel;

    public Cliente(String razaoSocial, String nomeFantasia, int cnpj, int codEndereco,
                   String telefone, String celular, String email, String responsavel) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.codEndereco = codEndereco;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.responsavel = responsavel;
    }

    public Cliente(int codCliente, String razaoSocial, String nomeFantasia, int cnpj,
                   int codEndereco, String telefone, String celular, String email, String responsavel) {
        this(razaoSocial, nomeFantasia, cnpj, codEndereco, telefone, celular, email, responsavel);
        this.codCliente = codCliente;
    }


    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}