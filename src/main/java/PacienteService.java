import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class PacienteService {
    //Responsabilidade: Lidar com as operações relacionadas aos pacientes no banco de dados.

    public void listInfoPacientes() {
        try {
            Scanner scanner = new Scanner(System.in);
            Connection connection = getConnection();

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();

            String sql;

            sql = "SELECT * FROM Paciente";

            ResultSet resultSet = statement.executeQuery(sql);

            // Processamento dos resultados e contagem de registros
            System.out.println(" ");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-3s | %-20s | %-15s | %-12s | %-12s | %-50s | %15s |\n", "IDPaciente", "Nome", "CPF", "Data Nasc.", "Telefone", "Endereco","Região Moradia");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            int cont = 0;
            while (resultSet.next()) {

                // Recupere os valores das colunas do resultado
                int idPaciente = resultSet.getInt("idPaciente");
                String nome = resultSet.getString("nome");
                String CPF = resultSet.getString("CPF");
                Date dataNascimento = resultSet.getDate("dataNascimento");
                String endereco = resultSet.getString("endereco");
                String telefone = resultSet.getString("telefone");
                String regiaoMoradia = resultSet.getString("regiaoMoradia");

                System.out.printf("| %-10d | %-20s | %-15s | %-12s | %-12s | %-50s | %15s |\n", idPaciente, nome, CPF, dataNascimento, telefone,  endereco,regiaoMoradia);
                cont++;
            }
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| Quantidade de resultados: %-127d|\n",cont);
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println(" ");

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void listInfoVacinasPaciente() {
//        try {
//            Connection connection = getConnection();
//            Scanner scanner = new Scanner(System.in);
//
//            // Criação do Statement para executar a consulta SQL
//            Statement statement = connection.createStatement();
//
//            System.out.println("Digite o idPaciente que você deseja visualizar os registros de vacinação: ");
//            int idPaciente = Integer.parseInt(scanner.nextLine());
//
//            String sql = "SELECT p.nome AS v.idvacina AS nomePaciente, v.nome AS nomeVacina, v.descricao, r.datavacinacao " +
//                    "FROM paciente p " +
//                    "INNER JOIN registrovacina r ON p.idpaciente = r.idpaciente " +
//                    "INNER JOIN vacinas v ON v.idvacina = r.idvacina " +
//                    "WHERE p.idpaciente = " + idPaciente;
//
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            // Processamento dos resultados e contagem de registros
//            System.out.println(" ");
//            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
//            System.out.printf("| %-20s | %-4s | %-20s | %-80s | %-17s |\n", "Nome Paciente", "idVacina", "Nome da Vacina", "Descrição", "Data da Vacinação");
//            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
//            int cont = 0;
//            while (resultSet.next()) {
//                // Recupere os valores das colunas do resultado
//                String nomePaciente = resultSet.getString("nomePaciente");
//                int idvacina = resultSet.getInt("idVacina");
//                String nomeVacina = resultSet.getString("nomeVacina");
//                String descricao = resultSet.getString("descricao");
//                String dataVacinacao = resultSet.getString("datavacinacao");
//
//                System.out.printf("| %-20s | %-4s | %-20s | %-80s | %-17s |\n", nomePaciente, idvacina, nomeVacina, descricao, dataVacinacao);
//                cont++;
//            }
//            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
//            System.out.printf("| Quantidade de resultados: %-126d |\n", cont);
//            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
//            System.out.println(" ");
//
//            resultSet.close();
//            statement.close();
//            connection.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void listInfoVacinasPaciente() {
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();

            System.out.println("Digite o idPaciente que você deseja visualizar os registros de vacinação: ");
            int idPaciente = Integer.parseInt(scanner.nextLine());

            String sql = "SELECT p.nome AS nomePaciente, v.idvacina AS idVacina, v.nome AS nomeVacina, v.descricao, r.datavacinacao " +
                    "FROM paciente p " +
                    "INNER JOIN registrovacina r ON p.idpaciente = r.idpaciente " +
                    "INNER JOIN vacinas v ON v.idvacina = r.idvacina " +
                    "WHERE p.idpaciente = " + idPaciente;

            ResultSet resultSet = statement.executeQuery(sql);

            // Processamento dos resultados e contagem de registros
            System.out.println(" ");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-20s | %-8s | %-20s | %-80s | %-17s |\n", "Nome Paciente", "idVacina", "Nome da Vacina", "Descrição", "Data da Vacinação");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            int cont = 0;
            while (resultSet.next()) {
                // Recupere os valores das colunas do resultado
                String nomePaciente = resultSet.getString("nomePaciente");

                // TODO: ajustar formatacao da tabela
                if(nomePaciente.length() > 20) { nomePaciente = nomePaciente.substring(0, 20); }

                int idVacina = resultSet.getInt("idVacina");

                String nomeVacina = resultSet.getString("nomeVacina");
                if(nomeVacina.length() > 20) { nomeVacina = nomeVacina.substring(0, 20); }

                String descricao = resultSet.getString("descricao");
                if(descricao.length() > 80) { descricao = descricao.substring(0, 77) + "..."; }

                String dataVacinacao = resultSet.getString("datavacinacao");

                System.out.printf("| %-20s | %-8s | %-20s | %-80s | %-17s |\n", nomePaciente, idVacina, nomeVacina, descricao, dataVacinacao);
                cont++;
            }
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| Quantidade de resultados: %-126d |\n", cont);
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println(" ");

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void addPaciente() {
        try {
            //abrir conecao com o database
            Connection connection = getConnection();

            // ler informacoes do terminal
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o nome do paciente: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o CPF: ");
            String CPF = scanner.nextLine();

            System.out.print("Digite a data de nascimento (no formato yyyy-MM-dd): ");
            String dataNascimentoStr = scanner.nextLine();

            // Converter a string da data de nascimento para o tipo java.sql.Date
            java.sql.Date dataNascimento = null;
            boolean dataValida = false;
            while (!dataValida) {
                try {
                    dataNascimento = convertStringToDate(dataNascimentoStr);
                    dataValida = true;
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                    System.out.print("Digite novamente a data de nascimento: ");
                    dataNascimentoStr = scanner.nextLine();
                }
            }
            System.out.print("Digite o endereço: ");
            String endereco = scanner.nextLine();

            System.out.print("Digite um numero de telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Digite a região que o paciente mora: ");
            String regiaoMoradia = scanner.nextLine();


            String sql = "INSERT INTO paciente (nome, CPF, dataNascimento, endereco, telefone, regiaoMoradia) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = executePreparedStatement(connection, nome, CPF, dataNascimento, endereco, telefone, regiaoMoradia, sql);

            statement.executeUpdate();

            System.out.println("Paciente adicionado com sucesso!");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRegistroVacinaPaciente() {
        try {
            Connection connection = getConnection();

            System.out.println("Digite o idPaciente que você deseja adicionar vacina");
            Scanner scanner = new Scanner(System.in);
            int idPaciente = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o idVacina que deseja vincular ao paciente");
            int idVacina = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite a data que o paciente recebeu a vacina (no formato yyyy-MM-dd): ");
            String dataVacinacaoStr = scanner.nextLine();

            // Converter a string dataVacinacao para o tipo java.sql.Date
            java.sql.Date dataVacinacao = null;
            boolean dataValida = false;
            while (!dataValida) {
                try {
                    dataVacinacao = convertStringToDate(dataVacinacaoStr);
                    dataValida = true;
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                    System.out.print("Digite novamente a data que o paciente recebeu a vacina: ");
                    dataVacinacaoStr = scanner.nextLine();
                }
            }

            // Comando SQL para vincular vacina e paciente e acrescentar data da vacina
            String sql = "INSERT INTO registrovacina (idPaciente, idVacina, dataVacinacao) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPaciente);
            statement.setInt(2, idVacina);
            statement.setObject(3, dataVacinacao);
            statement.executeUpdate();

            System.out.println("Registro de vacina vinculado ao paciente com sucesso!");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Erro ao adicionar registro de vacina para o paciente " + e.getMessage());
        }
    }

    void editInfoPaciente() {
        try {
            Connection connection = getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id do paciente que você deseja alterar: ");
            int idPaciente = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite o novo nome do paciente: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o novo CPF do paciente: ");
            String CPF = scanner.nextLine();

            System.out.print("Digite o novo telefone do paciente: ");
            String telefone = scanner.nextLine();

            System.out.print("Digite a data de nascimento (no formato yyyy-MM-dd): ");
            String dataNascimentoStr = scanner.nextLine();

            java.sql.Date dataNascimento;
            try {
                dataNascimento = convertStringToDate(dataNascimentoStr);
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                return; // Encerra o método para evitar a execução do código com uma data inválida
            }

            System.out.print("Digite o novo endereço do paciente: ");
            String endereco = scanner.nextLine();

            System.out.println("Digite a nova região do paciente: ");
            String regiaoMoradia = scanner.nextLine();

            String sql = "UPDATE Paciente SET nome = ?, CPF = ?, dataNascimento = ?, telefone = ?, endereco = ?, regiaoMoradia = ? WHERE idPaciente = ?";

            PreparedStatement statement = executePreparedStatement(connection, nome, CPF, dataNascimento, telefone, endereco, regiaoMoradia, sql);
            statement.setInt(7, idPaciente);

            statement.executeUpdate();

            System.out.println("Dados do paciente editados com sucesso!");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void deletePaciente() {
        try {
            Connection connection = getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id correspondente ao paciente que deseja excluir: ");
            int idPaciente = scanner.nextInt();

            // Execução da consulta SQL para excluir registro na tabela paciente
            String sql = "DELETE FROM Paciente WHERE idPaciente = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPaciente);
            statement.executeUpdate();

            System.out.println("Paciente deletado com sucesso!");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRegistroVacinaPaciente() {
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o ID do paciente: ");
            int idPaciente = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite o ID do registro de vacina a ser excluído: ");
            int idVacina = scanner.nextInt();
            scanner.nextLine();

            // Execução da consulta SQL para excluir registro na tabela VacinaPaciente
            String sql = "DELETE FROM registrovacina WHERE idpaciente = ? AND idvacina = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idPaciente);
            statement.setInt(2, idVacina);
            statement.executeUpdate();

            System.out.println("Registro de vacina excluído com sucesso!");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Erro ao excluir registro de vacina, operação cancelada!");
        }
    }

    void deleteTodosPacientes() {
        try {
            Connection connection = getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Tem certeza que deseja excluir todos os registros de pacientes (s/n)? ");
            String resposta = scanner.nextLine();

            Statement statement = connection.createStatement();
            if (resposta.equalsIgnoreCase("s")) {

                // Execução da consulta SQL para excluir todos os registros na tabela paciente

                String sql = "DELETE FROM paciente";
                statement.executeUpdate(sql);

                System.out.println("Todos os pacientes foram excluídos com sucesso.");
            } else {
                System.out.println("Operação cancelada pelo usuário.");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static java.sql.Date convertStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(dateStr);
        return new java.sql.Date(parsedDate.getTime());
    }

    private PreparedStatement executePreparedStatement(Connection connection, String nome, String CPF, java.sql.Date dataNascimento, String telefone, String endereco, String regiaoMoradia, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setString(2, CPF);
        statement.setDate(3, dataNascimento);
        statement.setString(4, telefone);
        statement.setString(5, endereco);
        statement.setString(6, regiaoMoradia);
        return statement;
    }


    private Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/projetoIntegrador1";
        String username = "kemmyps";
        String password = "";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}