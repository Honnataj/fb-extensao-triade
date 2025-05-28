package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatoBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public ContatoBancoDados() {

        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Contato\n" +
                    "(\n" +
                    "    cod_cliente   integer not null references Cliente (cod_cliente) on delete cascade,\n" +
                    "    num_contato   integer not null,\n" +
                    "    data_contato  text,\n" +
                    "    autor_contato integer,\n" +
                    "    -- autor_contato = 0 -> contato da empresa\n" +
                    "    -- autor_contato = 1 -> contato do cliente\n" +
                    "\n" +
                    "    primary key (cod_cliente, num_contato)\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Contato> listar() {
        List<Contato> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Contato")) {
            while (rs.next()) {
                lista.add(new Contato(rs.getInt("cod_cliente"),
                        rs.getInt("num_contato"),
                        rs.getString("data_contato"),
                        rs.getInt("autor_contato")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Contato contato) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("INSERT INTO Contato(cod_cliente, num_contato, data_contato, autor_contato) " +
                     "VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, contato.getCodCliente());
            ps.setInt(2, contato.getNumContato());
            ps.setString(3, contato.getDataContato());
            ps.setInt(4, contato.getAutorContato());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
