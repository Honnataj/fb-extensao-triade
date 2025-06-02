package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BairroBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public BairroBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Bairro\n" +
                    "(\n" +
                    "    cod_bairro    integer primary key,\n" +
                    "    nome          text,\n" +
                    "    cod_municipio integer not null references Municipio (cod_municipio)\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
    public List<Bairro> listar() {
        List<Bairro> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("select * from Bairro")) {
            while (rs.next()) {
                lista.add(new Bairro(rs.getInt("codBairro"),
                        rs.getString("nome"),
                        rs.getInt("codMunicipio")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    */

    public void inserir(Bairro bairro) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("insert into Bairro(nome, cod_municipio)" +
                     "values (?, ?)")) {
            ps.setString(1, bairro.getNome());
            ps.setInt(2, bairro.getCodMunicipio());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Bairro bairro) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("update Bairro set nome=?, cod_municipio=? where cod_bairro=?")) {
            ps.setString(1, bairro.getNome());
            ps.setInt(2, bairro.getCodMunicipio());
            ps.setInt(3, bairro.getCodBairro());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("delete from Bairro where cod_bairro=?")) {
            ps.setInt(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bairro buscarPorCodigo(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Bairro WHERE cod_bairro = ?")) {
            ps.setInt(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bairro(
                            rs.getInt("cod_bairro"),
                            rs.getString("nome"),
                            rs.getInt("cod_municipio")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int buscarCodigoPorNome(String nome) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("select cod_bairro from Bairro where nome = ?")) {
            ps.setString(1, nome);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cod_bairro");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
        // retorna -1 se não conseguir encontrar o código
    }

    /*
    public List<Bairro> listarPorMunicipio(int codMunicipio) {
        List<Bairro> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Bairro WHERE cod_municipio = ?")) {
            ps.setInt(1, codMunicipio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Bairro(
                            rs.getInt("cod_bairro"),
                            rs.getString("nome"),
                            rs.getInt("cod_municipio")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
     */

    public int inserirRetornandoCodigo(Bairro bairro) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement(
                     "insert into Bairro(nome, cod_municipio) values (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bairro.getNome());
            ps.setInt(2, bairro.getCodMunicipio());
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
        // retorna -1 se não conseguir inserir
    }
}