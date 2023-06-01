import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ServiceCadastroVacina {

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


            // Fechamento dos recursos
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void insertInfoPaciente() {
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
                System.out.print("Data em formato incorreto!\nDigite novamente a data de nascimento (no formato ano-mes-dia por exemplo 1995-02-04): ");
                dataNascimentoStr = scanner.nextLine();

                // Converter a string da data de nascimento para o tipo java.sql.Date
                try {
                    dataNascimento = getDataNascimento(dataNascimentoStr);
                    dataValida = true;
                } catch (ParseException e) {
                    System.out.println("Formato de data inválido. Certifique-se de usar o formato yyyy-MM-dd.");
                }
            }


            System.out.print("Digite o endereço: ");
            String endereco = scanner.nextLine();

            System.out.print("Digite um numero de telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Digite a região que o paciente mora: ");
            String regiaoMoradia = scanner.nextLine();

            // Execução da consulta SQL para inserir novo registro na tabela paciente
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

    void editInfoPaciente() {
        try {
            Connection connection = getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id do paciente que você deseja alterar: ");
            int idPaciente = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

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
                dataNascimento = getDataNascimento(dataNascimentoStr);
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


    void deleteInfoPaciente() {
        try {
            Connection connection = getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o id correspondente ao paciente que deseja excluir: ");
            int idPaciente = scanner.nextInt();

            // Execução da consulta SQL para excluir registro na tabela paciente
            String sql = "DELETE FROM Pacinte WHERE idpaciente =" + idPaciente;

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            System.out.println("Paciente deletado com sucesso!");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    private static java.sql.Date getDataNascimento(String dataNascimentoStr) throws ParseException {
        // Converter a string da data de nascimento para o tipo java.sql.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(dataNascimentoStr);
        java.sql.Date dataNascimento = new java.sql.Date(parsedDate.getTime());
        return dataNascimento;
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