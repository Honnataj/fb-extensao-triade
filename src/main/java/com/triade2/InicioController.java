package com.triade2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.util.Optional;

public class InicioController {
    // Campos de texto
    @FXML
    private TextField razaoSocialTF;
    @FXML
    private TextField nomeFantasiaTF;
    @FXML
    private TextField cnpjTF;
    @FXML
    private TextField telefoneTF;
    @FXML
    private TextField celularTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField responsavelTF;
    @FXML
    private TextField logradouroTF;
    @FXML
    private TextField numeroTF;
    @FXML
    private TextField complementoTF;
    @FXML
    private TextField cepTF;
    @FXML
    private TextField municipioTF;
    @FXML
    private TextField bairroTF;

    // ComboBox para o estado
    @FXML
    private ComboBox<Estado> estadoCB;

    // Elementos da tabela
    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML
    private TableColumn<Cliente, Integer> colCodCliente;
    @FXML
    private TableColumn<Cliente, String> colRazaoSocial;
    @FXML
    private TableColumn<Cliente, String> colNomeFantasia;
    @FXML
    private TableColumn<Cliente, Long> colCnpj;
    @FXML
    private TableColumn<Cliente, String> colTelefone;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colCelular;
    @FXML
    private TableColumn<Cliente, String> colResponsavel;
    @FXML
    private TableColumn<Cliente, Integer> colCodEndereco;

    // Objetos de acesso a dados
    private final ClienteBancoDados clienteBancoDados = new ClienteBancoDados();
    private final EstadoBancoDados estadoBancoDados = new EstadoBancoDados();
    private final MunicipioBancoDados municipioBancoDados = new MunicipioBancoDados();
    private final BairroBancoDados bairroBancoDados = new BairroBancoDados();
    private final EnderecoBancoDados enderecoBancoDados = new EnderecoBancoDados();

    // Listas observáveis
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private final ObservableList<Estado> listaEstados = FXCollections.observableArrayList();

    // Botões para configuração das colunas
    @FXML
    private HBox hboxBotoes;

    private Cliente clienteSelecionado = null;

    @FXML
    public void initialize() {
        // Configuração das colunas da tabela
        configurarColunas();
        colCodCliente.setVisible(false);

        // Configuração inicial
        camposEditaveis(false);
        configurarTabelaListener();
        carregarDados();

        estadoCB.setConverter(new StringConverter<>() {
            @Override
            public String toString(Estado estado) {
                return estado != null ? estado.getNome() : "";
            }

            @Override
            public Estado fromString(String string) {
                return null;
            }
        });

        estadoCB.setEditable(false);
        estadoCB.setItems(listaEstados);

    }

    private void configurarTabelaListener() {
        tabelaClientes.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            clienteSelecionado = novo;
            if (novo != null) {
                preencherFormulario(novo);
            } else {
                limparFormulario();
            }
        });
        tabelaClientes.setItems(listaClientes);
    }

    @FXML
    private void configurarColunas() {
        // Configurar cell value factories para todas as colunas
        colCodCliente.setCellValueFactory(new PropertyValueFactory<>("codCliente"));
        colRazaoSocial.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));
        colNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
        colCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        colResponsavel.setCellValueFactory(new PropertyValueFactory<>("responsavel"));
        colCodEndereco.setCellValueFactory(new PropertyValueFactory<>("codEndereco"));

        // Configurar o menu de contexto para mostrar/ocultar colunas
        ContextMenu contextMenu = new ContextMenu();
        for (TableColumn<Cliente, ?> coluna : tabelaClientes.getColumns()) {
            CheckMenuItem item = new CheckMenuItem(coluna.getText());
            item.setSelected(true);
            item.selectedProperty().bindBidirectional(coluna.visibleProperty());
            contextMenu.getItems().add(item);
        }

        // Adicionar botão para mostrar o menu de colunas
        Button btnColunas = new Button("Colunas");
        btnColunas.getStyleClass().add("campo-botao");
        btnColunas.setOnAction(e -> contextMenu.show(btnColunas, Side.BOTTOM, 0, 0));

        // Adicionar o botão ao HBox
        hboxBotoes.getChildren().add(btnColunas);
    }


    private void preencherFormulario(Cliente cliente) {
        razaoSocialTF.setText(cliente.getRazaoSocial());
        nomeFantasiaTF.setText(cliente.getNomeFantasia());
        cnpjTF.setText(String.valueOf(cliente.getCnpj()));
        telefoneTF.setText(cliente.getTelefone());
        celularTF.setText(cliente.getCelular());
        emailTF.setText(cliente.getEmail());
        responsavelTF.setText(cliente.getResponsavel());

        // Carregar endereço se existir
        if (cliente.getCodEndereco() > 0) {
            Endereco endereco = enderecoBancoDados.buscarPorCodigo(cliente.getCodEndereco());
            if (endereco != null) {
                logradouroTF.setText(endereco.getLogradouro());
                numeroTF.setText(String.valueOf(endereco.getNumero()));
                complementoTF.setText(endereco.getComplemento());
                cepTF.setText(String.valueOf(endereco.getCep()));

                // Carregar estado, município e bairro
                Bairro bairro = bairroBancoDados.buscarPorCodigo(endereco.getCodBairro());
                if (bairro != null) {
                    Municipio municipio = municipioBancoDados.buscarPorCodigo(bairro.getCodMunicipio());
                    if (municipio != null) {
                        Estado estado = estadoBancoDados.buscarPorCodigo(municipio.getCodEstado());
                        if (estado != null) {
                            estadoCB.setValue(estado);
                            municipioTF.setText(municipio.getNome());
                            bairroTF.setText(bairro.getNome());
                        }
                    }
                }
            }
        }
    }

    private void carregarDados() {
        listaClientes.setAll(clienteBancoDados.listar());
        listaEstados.setAll(estadoBancoDados.listar());
    }

    @FXML
    public void salvarCliente() {
        try {
            // Validação dos campos obrigatórios
            if (razaoSocialTF.getText().isBlank() || nomeFantasiaTF.getText().isBlank() || cnpjTF.getText().isBlank() ||
                    telefoneTF.getText().isBlank() || celularTF.getText().isBlank() ||
                    emailTF.getText().isBlank() || responsavelTF.getText().isBlank() ||
                    logradouroTF.getText().isBlank() || numeroTF.getText().isBlank() ||
                    cepTF.getText().isBlank() || estadoCB.getValue() == null ||
                    municipioTF.getText().isBlank() || bairroTF.getText().isBlank()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Preencha todos os campos obrigatórios.", "Erro");
                return;
            }
            // TODO: Validar valores numéricos

            // Preparar dados do cliente
            String razaoSocial = razaoSocialTF.getText();
            String nomeFantasia = nomeFantasiaTF.getText();
            long cnpj = Long.parseLong(cnpjTF.getText());
            String telefone = telefoneTF.getText();
            String celular = celularTF.getText();
            String email = emailTF.getText();
            String responsavel = responsavelTF.getText();

            // Preparar dados do endereço
            Estado estadoOb = estadoCB.getValue();
            String estado = estadoOb.getCodEstado();
            String municipioNome = municipioTF.getText();
            int municipio = municipioBancoDados.buscarCodigoPorNome(municipioNome);
            String bairroNome = bairroTF.getText();
            int bairro = bairroBancoDados.buscarCodigoPorNome(bairroNome);
            String logradouro = logradouroTF.getText();
            int numero = Integer.parseInt(numeroTF.getText());
            String complemento = complementoTF.getText();
            int cep = Integer.parseInt(cepTF.getText());


            // Salvar endereço primeiro, se houver
            Integer codEndereco = null;

            // Se bairro e município não existirem, criar novos
            if (bairro < 0) {
                if (municipio < 0) {
                    Municipio novoMunicipio = new Municipio(municipioNome, estado);
                    municipio = municipioBancoDados.inserirRetornandoCodigo(novoMunicipio);
                }
                Bairro novoBairro = new Bairro(bairroNome, municipio);
                bairro = bairroBancoDados.inserirRetornandoCodigo(novoBairro);
            }

            // Criar ou atualizar o endereço
            Endereco endereco = new Endereco(logradouro, numero, complemento, cep, bairro);

            if (clienteSelecionado != null && clienteSelecionado.getCodEndereco() > 0) {
                endereco.setCodEndereco(clienteSelecionado.getCodEndereco());
                enderecoBancoDados.atualizar(endereco);
                codEndereco = clienteSelecionado.getCodEndereco();
            } else {
                codEndereco = enderecoBancoDados.inserirRetornandoCodigo(endereco);
            }

            // Salvar ou atualizar cliente
            if (clienteSelecionado == null) {
                Cliente novoCliente = new Cliente(razaoSocial, nomeFantasia, cnpj, codEndereco,
                        telefone, celular, email, responsavel);
                clienteBancoDados.inserir(novoCliente);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente inserido com sucesso!", "Inserção");
            } else {
                clienteSelecionado.setRazaoSocial(razaoSocial);
                clienteSelecionado.setNomeFantasia(nomeFantasia);
                clienteSelecionado.setCnpj(cnpj);
                clienteSelecionado.setCodEndereco(codEndereco);
                clienteSelecionado.setTelefone(telefone);
                clienteSelecionado.setCelular(celular);
                clienteSelecionado.setEmail(email);
                clienteSelecionado.setResponsavel(responsavel);

                clienteBancoDados.atualizar(clienteSelecionado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente alterado com sucesso!", "Alteração");
            }

            camposEditaveis(false);
            limparFormulario();
            carregarDados();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "CNPJ e número devem ser valores numéricos válidos.", "Erro");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao salvar cliente: " + e.getMessage(), "Erro");
        }
    }

    @FXML
    public void alterarCliente() {
        if (clienteSelecionado != null) {
            camposEditaveis(true);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um cliente para alterar!", "Erro");
        }
    }

    @FXML
    public void novoCliente() {
        limparFormulario();
        camposEditaveis(true);
    }

    @FXML
    public void excluirCliente() {
        if (clienteSelecionado != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Deseja realmente excluir este cliente?", ButtonType.YES, ButtonType.NO);
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.YES) {
                clienteBancoDados.deletar(clienteSelecionado.getCodCliente());
                carregarDados();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente excluído com sucesso!", "Exclusão");
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um cliente para excluir.", "Erro");
        }
    }

    private void camposEditaveis(boolean editavel) {
        // Campos de texto
        TextField[] campos = {
                razaoSocialTF, nomeFantasiaTF, cnpjTF, telefoneTF, celularTF,
                emailTF, responsavelTF, logradouroTF, numeroTF, complementoTF,
                cepTF, municipioTF, bairroTF,
        };

        for (TextField campo : campos) {
            campo.setEditable(editavel);
            campo.setDisable(!editavel);
            if (editavel) {
                campo.getStyleClass().add("campo-texto");
            } else {
                campo.getStyleClass().remove("campo-texto");
            }
        }

        // ComboBoxes
        estadoCB.setDisable(!editavel);
    }

    @FXML
    public void limparFormulario() {
        // Limpar campos de texto
        razaoSocialTF.clear();
        nomeFantasiaTF.clear();
        cnpjTF.clear();
        telefoneTF.clear();
        celularTF.clear();
        emailTF.clear();
        responsavelTF.clear();
        logradouroTF.clear();
        numeroTF.clear();
        complementoTF.clear();
        cepTF.clear();
        municipioTF.clear();
        bairroTF.clear();

        // Limpar combobox
        estadoCB.setValue(null);

        clienteSelecionado = null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensagem, String titulo) {
        Alert alert = new Alert(tipo, mensagem);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}