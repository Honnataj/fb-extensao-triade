package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadoBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    // O método construtor está aqui, mas a tabela já foi criada e populada anteriormente com todos os estados. Não deve ser alterada.
    public EstadoBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Estado\n" +
                    "(\n" +
                    "    cod_estado text primary key\n" +
                    "                    not null check (cod_estado like '__'),\n" +
                    "    nome       text not null\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Estado> listar() {
        List<Estado> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("select * from Estado")) {
            while (rs.next()) {
                lista.add(new Estado(rs.getString("cod_estado"),
                        rs.getString("nome")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Estado estado) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("insert into Estado(nome) values (?)")) {
            ps.setString(1, estado.getNome());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Estado estado) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("update Estado set nome=? where cod_estado=?")) {
            ps.setString(1, estado.getNome());
            ps.setString(2, estado.getCodEstado());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(String codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("delete from Estado where cod_estado=?")) {
            ps.setString(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Estado buscarPorCodigo(String codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("SELECT * FROM Estado WHERE cod_estado = ?")) {
            ps.setString(1, codigo);
        
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estado(
                        rs.getString("cod_estado"),
                        rs.getString("nome")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String inserirRetornandoCodigo(Estado estado) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement(
                     "INSERT INTO Estado(nome) VALUES (?)", 
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, estado.getNome());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}