package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public EnderecoBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Endereco\n" +
                    "(\n" +
                    "    cod_endereco  integer primary key,\n" +
                    "    logradouro    text,\n" +
                    "    numero        integer,\n" +
                    "    complemento   text,\n" +
                    "    cep          integer,\n" +
                    "    cod_bairro   integer references Bairro (cod_bairro)\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Endereco> listar() {
        List<Endereco> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Endereco")) {
            while (rs.next()) {
                lista.add(new Endereco(rs.getInt("cod_endereco"),
                        rs.getString("logradouro"),
                        rs.getInt("numero"),
                        rs.getString("complemento"),
                        rs.getInt("cep"),
                        rs.getInt("cod_bairro")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Endereco endereco) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("INSERT INTO Endereco(logradouro, numero, complemento, cep, cod_bairro) " +
                     "VALUES (?, ?, ?, ?, ?)")) {
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setInt(4, endereco.getCep());
            ps.setInt(5, endereco.getCodBairro());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Endereco endereco) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("UPDATE Endereco SET logradouro=?, numero=?, complemento=?, cep=?, cod_bairro=? WHERE cod_endereco=?")) {
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setInt(4, endereco.getCep());
            ps.setInt(5, endereco.getCodBairro());
            ps.setInt(6, endereco.getCodEndereco());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM Endereco WHERE cod_endereco=?")) {
            ps.setInt(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Endereco buscarPorCodigo(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Endereco WHERE cod_endereco = ?")) {
        ps.setInt(1, codigo);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Endereco(
                    rs.getInt("cod_endereco"),
                    rs.getString("logradouro"),
                    rs.getInt("numero"),
                    rs.getString("complemento"),
                    rs.getInt("cep"),
                    rs.getInt("cod_bairro")
                );
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    public int inserirRetornandoCodigo(Endereco endereco) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement(
                     "INSERT INTO Endereco(logradouro, numero, complemento, cep, cod_bairro) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, endereco.getLogradouro());
            ps.setInt(2, endereco.getNumero());
            ps.setString(3, endereco.getComplemento());
            ps.setInt(4, endereco.getCep());
            ps.setInt(5, endereco.getCodBairro());
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