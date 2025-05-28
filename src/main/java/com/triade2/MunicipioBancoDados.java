package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public MunicipioBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Municipio\n" +
                    "(\n" +
                    "    cod_municipio integer primary key,\n" +
                    "    nome          text,\n" +
                    "    cod_estado    text references Estado (cod_estado)\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Municipio> listar() {
        List<Municipio> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Municipio")) {
            while (rs.next()) {
                lista.add(new Municipio(rs.getInt("cod_municipio"),
                        rs.getString("nome"),
                        rs.getString("cod_estado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Municipio municipio) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("INSERT INTO Municipio(nome, cod_estado) " +
                     "VALUES (?, ?)")) {
            ps.setString(1, municipio.getNome());
            ps.setString(2, municipio.getCodEstado());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Municipio municipio) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("UPDATE Municipio SET nome=?, cod_estado=? WHERE cod_municipio=?")) {
            ps.setString(1, municipio.getNome());
            ps.setString(2, municipio.getCodEstado());
            ps.setInt(3, municipio.getCodMunicipio());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM Municipio WHERE cod_municipio=?")) {
            ps.setInt(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Municipio buscarPorCodigo(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Municipio WHERE cod_municipio = ?")) {
            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Municipio(
                            rs.getInt("cod_municipio"),
                            rs.getString("nome"),
                            rs.getString("cod_estado")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Municipio> listarPorEstado(String codEstado) {
        List<Municipio> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Municipio WHERE cod_estado = ?")) {
            ps.setString(1, codEstado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Municipio(
                            rs.getInt("cod_municipio"),
                            rs.getString("nome"),
                            rs.getString("cod_estado")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int inserirRetornandoCodigo(Municipio municipio) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement(
                     "INSERT INTO Municipio(nome, cod_estado) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, municipio.getNome());
            ps.setString(2, municipio.getCodEstado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}