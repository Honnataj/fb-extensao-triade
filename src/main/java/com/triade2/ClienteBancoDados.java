package com.triade2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteBancoDados {
    private final String url = "jdbc:sqlite:clientes.db";

    public ClienteBancoDados() {
        try (Connection conexao = DriverManager.getConnection(url);
             Statement statement = conexao.createStatement()) {
            String criacaoTabela = "CREATE TABLE IF NOT EXISTS Clientes " +
                    "(codigo INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, telefone TEXT NOT NULL)";
            statement.execute(criacaoTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conexao = DriverManager.getConnection(url);
             Statement statement = conexao.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Clientes")) {
            while (resultSet.next()) {
                lista.add(new Cliente(resultSet.getInt("codigo"),
                        resultSet.getString("nome"),
                        resultSet.getString("telefone")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void inserir(Cliente cliente) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO Clientes(nome, telefone) VALUES (?, ?)")) {
            preparedStatement.setString(1, cliente.getNomeCliente());
            preparedStatement.setString(2, cliente.getTelefoneCliente());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Cliente cliente) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conexao.prepareStatement("UPDATE Clientes SET nome=?, telefone=? WHERE codigo=?")) {
            preparedStatement.setString(1, cliente.getNomeCliente());
            preparedStatement.setString(2, cliente.getTelefoneCliente());
            preparedStatement.setInt(3, cliente.getCodigoCliente());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int codigo) {
        try (Connection conexao = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Clientes WHERE codigo=?")) {
            preparedStatement.setInt(1, codigo);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
