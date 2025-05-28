package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KitBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public KitBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Kit\n" +
                    "(\n" +
                    "    cod_kit       integer primary key,\n" +
                    "    data_montagem text,\n" +
                    "    data_entrega  text,\n" +
                    "    cod_cliente   integer references Cliente (cod_cliente)\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Kit> listar() {
        List<Kit> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Kit")) {
            while (rs.next()) {
                lista.add(new Kit(rs.getInt("cod_kit"),
                        rs.getString("data_montagem"),
                        rs.getString("data_entrega"),
                        rs.getInt("cod_cliente")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Kit kit) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("INSERT INTO Kit(data_montagem, data_entrega, cod_cliente) " +
                     "VALUES (?, ?, ?)")) {
            ps.setString(1, kit.getDataMontagem());
            ps.setString(2, kit.getDataEntrega());
            ps.setInt(3, kit.getCodCliente());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Kit kit) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("UPDATE Kit SET data_montagem=?, data_entrega=?, cod_cliente=? WHERE cod_kit=?")) {
            ps.setString(1, kit.getDataMontagem());
            ps.setString(2, kit.getDataEntrega());
            ps.setInt(3, kit.getCodCliente());
            ps.setInt(4, kit.getCodKit());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM Kit WHERE cod_kit=?")) {
            ps.setInt(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}