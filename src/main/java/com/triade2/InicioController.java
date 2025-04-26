package com.triade2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InicioController {
    @FXML
    private TextField nomeClienteTF;
    @FXML
    private TextField telefoneClienteTF;
    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML
    private TableColumn<Cliente, Integer> colCodigoCliente;
    @FXML
    private TableColumn<Cliente, String> colNomeCliente;
    @FXML
    private TableColumn<Cliente, String> colTelefoneCliente;

    private final ClienteBancoDados clienteBancoDados = new ClienteBancoDados();
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private Cliente clienteSelecionado = null;

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigoCliente"));
        colNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colTelefoneCliente.setCellValueFactory(new PropertyValueFactory<>("telefoneCliente"));

        // Torna os campos não editáveis
        camposEditaveis(false);

        // Listener para atualizar os campos ao selecionar uma linha
        tabelaClientes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            clienteSelecionado = novo;
            if (novo != null) {
                nomeClienteTF.setText(novo.getNomeCliente());
                telefoneClienteTF.setText(novo.getTelefoneCliente());
            } else {
                limparFormulario();
            }
        });

        tabelaClientes.setItems(listaClientes);
        carregarClientes();
    }

    // Salva novo cliente ou atualiza um existente
    public void salvarCliente() {
        String nome = nomeClienteTF.getText();
        String telefone = telefoneClienteTF.getText();

        if (nome.isBlank() || telefone.isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Preencha todos os campos.", "Erro");
            return;
        }

        if (clienteSelecionado == null) {
            clienteBancoDados.inserir(new Cliente(nome, telefone));
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente inserido com sucesso!", "Inserção");
        } else {
            clienteSelecionado.setNomeCliente(nome);
            clienteSelecionado.setTelefoneCliente(telefone);
            clienteBancoDados.atualizar(clienteSelecionado);
            clienteSelecionado = null;
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente alterado com sucesso!", "Alteração");
        }

        camposEditaveis(false);
        limparFormulario();
        carregarClientes();
    }

    // Alterar cliente
    public void alterarCliente() {
        if (clienteSelecionado != null) {
            camposEditaveis(true);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um cliente para alterar!", "Erro");
        }
    }

    // Novo cliente
    public void novoCliente() {
        limparFormulario();
        camposEditaveis(true);
    }

    private void camposEditaveis(boolean editavel) {
        nomeClienteTF.setEditable(editavel);
        telefoneClienteTF.setEditable(editavel);
        if (!editavel) {
            nomeClienteTF.getStyleClass().remove("campo-texto");
            telefoneClienteTF.getStyleClass().remove("campo-texto");
            nomeClienteTF.setDisable(true);
            telefoneClienteTF.setDisable(true);
        } else {
            nomeClienteTF.getStyleClass().add("campo-texto");
            telefoneClienteTF.getStyleClass().add("campo-texto");
            nomeClienteTF.setDisable(false);
            telefoneClienteTF.setDisable(false);
        }
    }

    public void excluirCliente() {
        if (clienteSelecionado != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir este cliente?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.YES) {
                clienteBancoDados.deletar(clienteSelecionado.getCodigoCliente());
                carregarClientes();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente excluído com sucesso!", "Exclusão");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um cliente para excluir.", "Erro");
        }
    }

    // Limpa os campos do formulário e reseta seleção
    @FXML
    public void limparFormulario() {
        nomeClienteTF.clear();
        telefoneClienteTF.clear();
        clienteSelecionado = null;
    }

    private void carregarClientes() {
        listaClientes.setAll(clienteBancoDados.listar());
    }

    // Alertas
    private void mostrarAlerta(Alert.AlertType tipo, String mensagem, String titulo) {
        Alert alert = new Alert(tipo, mensagem);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}
