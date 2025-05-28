package com.triade2;

public class Contato {
    private int codCliente;
    private int numContato;
    private String dataContato;
    private int autorContato;
    // autorContato = 0 -> contato da empresa
    // autorContato = 1 -> contato do cliente

    public Contato(String dataContato, int autorContato) {
        this.dataContato = dataContato;
        this.autorContato = autorContato;
    }

    public Contato(int codCliente, int numContato, String dataContato, int autorContato) {
        this(dataContato, autorContato);
        this.codCliente = codCliente;
        this.numContato = numContato;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getNumContato() {
        return numContato;
    }

    public void setNumContato(int numContato) {
        this.numContato = numContato;
    }

    public String getDataContato() {
        return dataContato;
    }

    public void setDataContato(String dataContato) {
        this.dataContato = dataContato;
    }

    public int getAutorContato() {
        return autorContato;
    }

    public void setAutorContato(int autorContato) {
        this.autorContato = autorContato;
    }
}
