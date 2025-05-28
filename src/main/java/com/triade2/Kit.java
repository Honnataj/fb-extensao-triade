package com.triade2;

public class Kit {
    private int codKit;
    private String dataMontagem;
    private String dataEntrega;
    private int codCliente;

    public Kit(String dataMontagem, String dataEntrega, int codCliente) {
        this.dataMontagem = dataMontagem;
        this.dataEntrega = dataEntrega;
        this.codCliente = codCliente;
    }

    public Kit(int codKit, String dataMontagem, String dataEntrega, int codCliente) {
        this(dataMontagem, dataEntrega, codCliente);
        this.codKit = codKit;
    }

    public int getCodKit() {
        return codKit;
    }

    public void setCodKit(int codKit) {
        this.codKit = codKit;
    }

    public String getDataMontagem() {
        return dataMontagem;
    }

    public void setDataMontagem(String dataMontagem) {
        this.dataMontagem = dataMontagem;
    }

    public String getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(String dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }
}
