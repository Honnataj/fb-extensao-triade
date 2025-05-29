package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteBancoDados {
    private final String url = "jdbc:sqlite:triade.db";

    public ClienteBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement()) {
            String criacaoTabela = "create table if not exists Cliente\n" +
                    "(\n" +
                    "    cod_cliente   integer primary key,\n" +
                    "    razao_social  text,\n" +
                    "    nome_fantasia text,\n" +
                    "    cnpj          integer,\n" +
                    "    cod_endereco  integer references Endereco (cod_endereco),\n" +
                    "    telefone      text,\n" +
                    "    celular       text,\n" +
                    "    email         text,\n" +
                    "    responsavel   text\n" +
                    ");";
            st.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement st = conexao.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Cliente")) {
            while (rs.next()) {
                lista.add(new Cliente(rs.getInt("cod_cliente"),
                        rs.getString("razao_social"),
                        rs.getString("nome_fantasia"),
                        rs.getLong("cnpj"),
                        rs.getInt("cod_endereco"),
                        rs.getString("telefone"),
                        rs.getString("celular"),
                        rs.getString("email"),
                        rs.getString("responsavel")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Cliente cliente) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("INSERT INTO Cliente(razao_social, nome_fantasia, cnpj, cod_endereco, telefone, celular, email, responsavel) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, cliente.getRazaoSocial());
            ps.setString(2, cliente.getNomeFantasia());
            ps.setLong(3, cliente.getCnpj());
            ps.setInt(4, cliente.getCodEndereco());
            ps.setString(5, cliente.getTelefone());
            ps.setString(6, cliente.getCelular());
            ps.setString(7, cliente.getEmail());
            ps.setString(8, cliente.getResponsavel());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Cliente cliente) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("UPDATE Cliente SET razao_social=?, nome_fantasia=?, cnpj=?, cod_endereco=?, telefone=?, celular=?, email=?, responsavel=? WHERE cod_cliente=?")) {
            ps.setString(1, cliente.getRazaoSocial());
            ps.setString(2, cliente.getNomeFantasia());
            ps.setLong(3, cliente.getCnpj());
            ps.setInt(4, cliente.getCodEndereco());
            ps.setString(5, cliente.getTelefone());
            ps.setString(6, cliente.getCelular());
            ps.setString(7, cliente.getEmail());
            ps.setString(8, cliente.getResponsavel());
            ps.setInt(9, cliente.getCodCliente());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement ps = conexao.prepareStatement("DELETE FROM Cliente WHERE cod_cliente=?")) {
            ps.setInt(1, codigo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
