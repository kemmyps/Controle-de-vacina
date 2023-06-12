package services;

import models.Vacina;

import java.sql.*;
import java.util.Scanner;

public class VacinaService {
    // Responsabilidade: Lidar com as operações relacionadas às vacinas no banco de dados.

    private Scanner scanner;

    public VacinaService() {
        this.scanner = new Scanner(System.in);
    }

    public void listInfoVacinas() {
        try {
            Connection connection = getConnection();

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM vacinas";

            ResultSet resultSet = statement.executeQuery(sql);

            // Processamento dos resultados e contagem de registros
            System.out.println(" ");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| %-10s | %-20s | %-116s | \n", "IDVacina", "Nome", "Descrição");
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            int cont = 0;
            while (resultSet.next()) {

                // Recupere os valores das colunas do resultado
                int idVacina = resultSet.getInt("idVacina");
                String nome = resultSet.getString("nome");
                if(nome.length() > 20) {
                    nome = nome.substring(0, 20);
                }

                String descricao = resultSet.getString("descricao");
                if(descricao.length() > 116) {
                    descricao = descricao.substring(0, 116);
                }

                System.out.printf("| %-10d | %-20s | %-116s |\n", idVacina, nome, descricao);
                cont++;
            }
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.printf("| Quantidade de resultados: %-127d|\n", cont);
            System.out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println(" ");

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addVacina() {
        try {
            // Abrir conexão com o banco de dados
            Connection connection = getConnection();

            System.out.print("Digite o nome da vacina: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a descrição da vacina: ");
            String descricao = scanner.nextLine();

            String sql = "INSERT INTO vacinas (nome, descricao) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, descricao);

            statement.executeUpdate();

            System.out.println("Vacina adicionada com sucesso!");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editInfoVacina() {
        try {
            Connection connection = getConnection();

            System.out.println("Digite o id da vacina que você deseja alterar: ");
            int idVacina = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Digite o novo nome da vacina: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a nova descrição da vacina: ");
            String descricao = scanner.nextLine();

            String sql = "UPDATE vacinas SET nome = ?, descricao = ? WHERE idvacina = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, descricao);
            statement.setInt(3, idVacina);

            statement.executeUpdate();

            System.out.println("Dados da vacina editados com sucesso!");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteVacina() {
        try {
            Connection connection = getConnection();

            System.out.println("Digite o id correspondente a vacina que deseja excluir: ");
            int idVacina = scanner.nextInt();

            String sql = "DELETE FROM vacinas WHERE idvacina = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idVacina);

            statement.executeUpdate();

            System.out.println("Vacina deletada com sucesso!");

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTodasVacinas() {
        try {
            Connection connection = getConnection();

            System.out.println("Tem certeza que deseja excluir todos os registros de vacinas (s/n)? ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                String sql = "DELETE FROM vacinas";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();

                System.out.println("Todos as vacinas foram excluídos com sucesso.");
                statement.close();
            } else {
                System.out.println("Operação cancelada pelo usuário.");
            }

            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Vacina getVacinaDetails(int idVacina) {
        Vacina vacina = null;

        try {
            Connection connection = getConnection();

            String sql = "SELECT * FROM vacinas WHERE idVacina = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idVacina);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idVacina");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                vacina = new Vacina(id, nome, descricao);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao obter os detalhes da vacina: " + e.getMessage());
        }

        return vacina;
    }


    private static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/projetoIntegrador1";
        String username = "kemmyps";
        String password = "";
        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
