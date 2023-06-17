package services;

import models.Paciente;
import models.RegistroVacinacao;
import models.Vacina;
import utils.DatabaseConnection;

import java.sql.*;
import java.text.ParseException;
import java.util.*;

public class PacienteService {
    //Responsabilidade: Lidar com as operações relacionadas aos pacientes no banco de dados.

    public void listInfoPacientes() {
        try {
            Scanner scanner = new Scanner(System.in);
            Connection connection = DatabaseConnection.getConnection();

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL.listInfoPacientes());

            List<Paciente> listPacientes = new ArrayList<Paciente>();

            while (resultSet.next()) {
                // Recupere os valores das colunas do resultado
                Integer idPaciente = resultSet.getInt("idPaciente");
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("cpf");
                java.sql.Date dataNascimento = resultSet.getDate("dataNascimento");
                String endereco = resultSet.getString("endereco");
                String telefone = resultSet.getString("telefone");
                String regiaoMoradia = resultSet.getString("regiaoMoradia");

                Paciente paciente = new Paciente(idPaciente, nome, cpf, endereco, dataNascimento, regiaoMoradia, telefone);
                listPacientes.add(paciente);
            }

            resultSet.close();
            statement.close();
            connection.close();

            Printer.showPacienteList(listPacientes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Paciente getPacienteDetails(Integer idPaciente) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DatabaseConnection.getConnection();

        // Criação do Statement para executar a consulta SQL
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL.pacientesDetails(idPaciente));

        List<Vacina> vacinas = new ArrayList<Vacina>();

        String nome;
        String cpf;
        java.sql.Date dataNascimento;
        String endereco;
        String telefone;
        String regiaoMoradia;
        Paciente paciente = null;

        if (resultSet.next()) {
            // Recupere os valores das colunas do resultado
            nome = resultSet.getString("nome");
            cpf = resultSet.getString("cpf");
            dataNascimento = resultSet.getDate("dataNascimento");
            endereco = resultSet.getString("endereco");
            telefone = resultSet.getString("telefone");
            regiaoMoradia = resultSet.getString("regiaoMoradia");

            List<RegistroVacinacao> registrosVacinacao = getVacinasDoPaciente(idPaciente);
            paciente = new Paciente(idPaciente, nome, cpf, endereco, dataNascimento, regiaoMoradia, telefone, registrosVacinacao);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return paciente;
    }

    public List<RegistroVacinacao> getVacinasDoPaciente(Integer idPaciente) {
        List<RegistroVacinacao> registrosVacinacao = new ArrayList<RegistroVacinacao>();

        try {
            Scanner scanner = new Scanner(System.in);
            Connection connection = DatabaseConnection.getConnection();

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();

            String sql = SQL.registroVacinacao(idPaciente);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Recupere os valores das colunas do resultado
                Integer idVacina = resultSet.getInt("idVacina");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                java.sql.Date dataVacinacao = resultSet.getDate("dataVacinacao");

                Vacina vacina = new Vacina(idVacina, nome, descricao);
                RegistroVacinacao registroVacinacao = new RegistroVacinacao(vacina, dataVacinacao);
                registrosVacinacao.add(registroVacinacao);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrosVacinacao;
    }

    public void listInfoVacinasPaciente() {
        Scanner scanner = new Scanner(System.in);
        Boolean visualizarVacinaPaciente = true;

        try {
            while (visualizarVacinaPaciente) {
                System.out.print("Digite o idPaciente que você deseja visualizar os registros de vacinação: ");
                Integer idPaciente = scanner.nextInt();
                Paciente paciente = getPacienteDetails(idPaciente);

                if (paciente == null) {
                    Printer.idInvalido();
                    continue;
                }

                Printer.showPacienteDetail(paciente);

                System.out.print("Deseja ver informações de mais algum paciente? (s/n): ");
                String resposta = scanner.next();
                visualizarVacinaPaciente = !resposta.equalsIgnoreCase("n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addPaciente() {
        Stack<Paciente> pacientes = new Stack<>();
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = DatabaseConnection.getConnection();

            Scanner scanner = new Scanner(System.in);

            boolean cadastrarNovoPaciente = true;

            while (cadastrarNovoPaciente) {
                System.out.print("Digite o nome do paciente: ");
                String nome = scanner.nextLine();

                System.out.print("Digite o cpf: ");
                String cpf = scanner.nextLine();

                while (cpf.length() !=11) {
                    System.out.println("O cpf dete ter exatamente 11 digitos, somente numeros");
                    System.out.println("Digite o cpf novamente: ");
                    cpf = scanner.nextLine();
                }

                System.out.print("Digite a data de nascimento (no formato yyyy-MM-dd): ");
                String dataNascimentoStr = scanner.nextLine();

                java.sql.Date dataNascimento = null;
                boolean dataValida = false;
                while (!dataValida) {
                    try {
                        if (dataNascimentoStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            dataNascimento = convertStringToDate(dataNascimentoStr);
                            dataValida = true;
                        } else {
                            System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                            System.out.print("Digite novamente a data de nascimento: ");
                            dataNascimentoStr = scanner.nextLine();
                        }
                    } catch (IllegalArgumentException | ParseException e) {
                        System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                        System.out.print("Digite novamente a data de nascimento: ");
                        dataNascimentoStr = scanner.nextLine();
                    }
                }
                System.out.print("Digite o endereço: ");
                String endereco = scanner.nextLine();

                System.out.print("Digite um numero de telefone: ");
                String telefone = scanner.nextLine();
                while (telefone.length() !=11) {
                    System.out.println("O numero do telefone dete ter exatamente 11 digitos, somente numeros com DDD. exemplo: 62123456789");
                    System.out.println("Digite o numero de telefone novamente: ");
                    telefone = scanner.nextLine();
                }

                System.out.print("Digite a região que o paciente mora: ");
                String regiaoMoradia = scanner.nextLine();


                //Cria um objeto models.Paciente com as informações
                Paciente paciente = new Paciente(nome, cpf, endereco, dataNascimento, regiaoMoradia, telefone);

                //Adiciona o paciente a fila
                pacientes.push(paciente);

                //Pergunta ao usuário se deseja cadastrar mais um paciente ou salvar os que já estão na pilha
                System.out.print("Deseja cadastrar mais um paciente? (S/N): ");
                String resposta = scanner.nextLine();

                if (resposta.equalsIgnoreCase("N")) {
                    cadastrarNovoPaciente = false;
                }
            }
            while (!pacientes.isEmpty()) {
                Paciente p = pacientes.pop();
                String sql = SQL.updateSqlAddPaciente();
                statement = executePreparedStatement(connection, p.getNome(), p.getCpf(), p.getDataNascimento(), p.getEndereco(), p.getTelefone(), p.getRegiaoMoradia(), sql);
                statement.executeUpdate();
            }
            Printer.registroAdicionado();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRegistroVacinaPaciente() {

        try {
            Connection connection = DatabaseConnection.getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o idPaciente que você deseja adicionar vacina: ");
            Integer idPaciente = scanner.nextInt();
            scanner.nextLine();

            Paciente paciente = null;

            while (paciente == null) {
                paciente = getPacienteDetails(idPaciente);

                if (paciente == null) {
                    Printer.idInvalido();

                    System.out.print("Digite um idPaciente valido para adicionar vacina: ");
                    idPaciente = scanner.nextInt();
                    scanner.nextLine();
                }
            }

            boolean addRegistVacina = true;


            while (addRegistVacina) {
                System.out.print("Digite o idVacina que deseja vincular ao paciente: ");
                Integer idVacina = scanner.nextInt();
                scanner.nextLine();

                Vacina vacina = null;

                while (vacina == null) {
                    vacina = VacinaService.getVacinaDetails(idVacina);

                    if (vacina == null) {
                        Printer.idInvalido();

                        System.out.print("Digite o idVacina que você deseja adicionar ao paciente: ");
                        idVacina = scanner.nextInt();
                        scanner.nextLine();
                    }
                }

                System.out.print("Digite a data que o paciente recebeu a vacina (no formato yyyy-MM-dd): ");
                String dataVacinacaoStr = scanner.nextLine();

                // Converter a string dataVacinacao para o tipo java.sql.Date
                java.sql.Date dataVacinacao = null;
                boolean dataValida = false;
                while (!dataValida) {
                    try {
                        if (dataVacinacaoStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            dataVacinacao = convertStringToDate(dataVacinacaoStr);
                            dataValida = true;
                        } else {
                            System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                            System.out.print("Digite novamente a data que o paciente recebeu a vacina: ");
                            dataVacinacaoStr = scanner.nextLine();
                        }
                    } catch (IllegalArgumentException | ParseException e ) {
                        System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                        System.out.print("Digite novamente a data que o paciente recebeu a vacina: ");
                        dataVacinacaoStr = scanner.nextLine();
                    }
                }

                // Comando SQL para vincular vacina e paciente e acrescentar data da vacina
                String sql = SQL.updateSqlAddRegistroVacinaPaciente();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, idPaciente);
                statement.setInt(2, idVacina);
                statement.setObject(3, dataVacinacao);
                statement.executeUpdate();

                System.out.printf("Deseja adicionar mais um registro de vacina ao paciente %s (s/n)? ", paciente.getNome());
                String resposta = scanner.nextLine();
                if (!resposta.equalsIgnoreCase("s")) {
                    addRegistVacina = false;
                }

                statement.close();

            }
            Printer.registroAdicionado();
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao adicionar registro de vacina para o paciente. Operação cancelada! " + e.getMessage());
        }
    }

    public void editInfoPaciente() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id do paciente que você deseja alterar: ");
            int idPaciente = scanner.nextInt();
            scanner.nextLine();

            Paciente paciente = getPacienteDetails(idPaciente);
            if (paciente == null) {
                Printer.idInvalido();
                System.out.println("Por favor, tente novamente!");
            }

            System.out.println(" ");
            System.out.println("Você esta editando os dados do paciente. Atenção se você quiser manter a informação, apenas tecle Enter/Return");
            System.out.println(" ");

            // Nome
            System.out.printf("Digite o novo nome do paciente (%s): ", paciente.getNome());
            String nome = scanner.nextLine();
            nome = nome.isBlank() ? paciente.getNome() : nome;
            paciente.setNome(nome);

            // cpf
            System.out.printf("Digite o novo cpf do paciente (%s): ", paciente.getCpf());
            String cpf = scanner.nextLine();
            cpf = cpf.isBlank() ? paciente.getCpf() : cpf;
            paciente.setCpf(cpf);

            // Telefone
            System.out.printf("Digite o novo telefone do paciente (%s): ", paciente.getTelefone());
            String telefone = scanner.nextLine();
            String newTelefone = telefone.isBlank() ? paciente.getTelefone() : telefone;
            paciente.setTelefone(newTelefone);

            // Data de nascimento
            System.out.printf("Digite a data de nascimento (no formato yyyy-MM-dd) (%s): ", paciente.getDataNascimento());
            String dataNascimentoStr = scanner.nextLine();
            dataNascimentoStr = dataNascimentoStr.isBlank() ? paciente.getDataNascimento().toString() : dataNascimentoStr;

            java.sql.Date dataNascimento = null;
            Boolean dataValida = false;
            while (!dataValida) {
                try {
                    if (dataNascimentoStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        dataNascimento = convertStringToDate(dataNascimentoStr);
                        dataValida = true;
                    } else {
                        System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                        System.out.print("Digite novamente a data de nascimento: ");
                        dataNascimentoStr = scanner.nextLine();
                    }
                } catch (IllegalArgumentException | ParseException e) {
                    System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                    System.out.print("Digite novamente a data de nascimento: ");
                    dataNascimentoStr = scanner.nextLine();
                }
                paciente.setDataNascimento(dataNascimento);
            }

            // Endereço
            System.out.printf("Digite o novo endereço do paciente (%s): ", paciente.getEndereco());
            String endereco = scanner.nextLine();
            endereco = endereco.isBlank() ? paciente.getEndereco() : endereco;
            paciente.setEndereco(endereco);

            // Regiao de moradia
            System.out.printf("Digite a nova região do paciente: (%s) ", paciente.getRegiaoMoradia());
            String regiaoMoradia = scanner.nextLine();
            regiaoMoradia = regiaoMoradia.isBlank() ? paciente.getRegiaoMoradia() : regiaoMoradia;
            paciente.setRegiaoMoradia(regiaoMoradia);

            List<Paciente> listPaciente = new ArrayList<>();
            listPaciente.add(paciente);
            Printer.showPacienteList(listPaciente);

            System.out.println("Deseja salvar alterações (s/n) ? ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {

                PreparedStatement statement = executePreparedStatement(
                        connection,
                        paciente.getNome(),
                        paciente.getCpf(),
                        paciente.getDataNascimento(),
                        paciente.getTelefone(),
                        paciente.getEndereco(),
                        paciente.getRegiaoMoradia(),
                        SQL.updateSqlEditInfoPaciente()
                );
                statement.setInt(7, idPaciente);

                statement.executeUpdate();

                Printer.registroAdicionado();

                statement.close();
                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("Operação cancelada");
            throw new RuntimeException(e);
        }
    }

    public void deletePaciente() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id correspondente ao paciente que deseja excluir: ");
            int idPaciente = scanner.nextInt();


            System.out.printf("Você tem certeza que deseja deletar todas as informações do paciente %s (s/n)? ", getPacienteDetails(idPaciente).getNome());
            String resposta = scanner.next();

            if (resposta.equalsIgnoreCase("s")) {

            // Execução da consulta SQL para excluir registro na tabela paciente
            String sql = SQL.deletePaciente();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPaciente);
            statement.executeUpdate();

            Printer.registroDeletado();

            statement.close();
            connection.close();
            } else {
                System.out.println("Operação cancelada pelo usuário!");
            }

        } catch (SQLException e) {
            System.out.println("Erro, operação cancelada automaticamente!");
            throw new RuntimeException(e);
        }
    }

    public void deleteRegistroVacinaPaciente() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o ID do paciente: ");
            int idPaciente = scanner.nextInt();
            scanner.nextLine();

            Paciente paciente = null;

            while (paciente == null) {
                paciente = getPacienteDetails(idPaciente);

                if (paciente == null) {
                    Printer.idInvalido();

                    System.out.print("Digite um idPaciente valido para excluir registro de vacina: ");
                    idPaciente = scanner.nextInt();
                    scanner.nextLine();
                }
            }
            if (paciente.getRegistrosVacinacao().size() == 0) {
                System.out.println("Paciente não possui registros de vacinação para serem excluidos!");
                return;
            }

            System.out.println(" ");
            System.out.println("O paciente possui esses registros de vacina:");
            Printer.showPacienteDetail(paciente);

            System.out.print("Digite o ID do registro de vacina a ser excluído: ");
            int idVacina = scanner.nextInt();
            scanner.nextLine();


            // Execução da consulta SQL para excluir registro na tabela VacinaPaciente
            String sql = SQL.deleteRegistroVacinaPaciente();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPaciente);
            statement.setInt(2, idVacina);
            statement.executeUpdate();

            Printer.registroDeletado();

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Erro ao excluir registro de vacina, operação cancelada!");
        }
    }

    private static java.sql.Date convertStringToDate(String dateStr) throws ParseException {
        return java.sql.Date.valueOf(dateStr);
    }

    private PreparedStatement executePreparedStatement(Connection connection, String nome, String cpf, java.sql.Date dataNascimento, String telefone, String endereco, String regiaoMoradia, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setString(2, cpf);
        statement.setDate(3, dataNascimento);
        statement.setString(4, telefone);
        statement.setString(5, endereco);
        statement.setString(6, regiaoMoradia);
        return statement;
    }
}