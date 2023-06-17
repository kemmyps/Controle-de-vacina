package services;

import models.Vacina;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static services.SQL.*;

public class VacinaService {
    // Responsabilidade: Lidar com as operações relacionadas às vacinas no banco de dados.

    private Scanner scanner;

    public VacinaService() {
        this.scanner = new Scanner(System.in);
    }

    public void listInfoVacinas() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            // Criação do Statement para executar a consulta SQL
            Statement statement = connection.createStatement();

            String sql = listVacinas();

            ResultSet resultSet = statement.executeQuery(sql);

            // Processamento dos resultados e contagem de registros
            List<Vacina> listVacinas = new ArrayList<>();

            while (resultSet.next()) {
                Integer idVacina = resultSet.getInt("idVacina");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                Vacina vacina = new Vacina(idVacina, nome, descricao);
                listVacinas.add(vacina);
            }

            Printer.showListVacinas(listVacinas);

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
            Connection connection = DatabaseConnection.getConnection();

            System.out.print("Digite o nome da vacina: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a descrição da vacina: ");
            String descricao = scanner.nextLine();

            String sql = addVacinas();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);
            statement.setString(2, descricao);
            statement.executeUpdate();

            Printer.registroAdicionado();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editInfoVacina() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            System.out.println("Digite o id da vacina que você deseja alterar: ");
            int idVacina = scanner.nextInt();
            scanner.nextLine();

            Vacina vacina = getVacinaDetails(idVacina);
            if (vacina == null) {
                Printer.idInvalido();
                System.out.println("Por favor, tente novamente!");
            }

                System.out.println(" ");
                System.out.println("Você esta editando os dados de vacina. Atenção se você quiser manter a informação, apenas tecle Enter/Return");
                System.out.println(" ");

                // Nome
                System.out.printf("Digite o novo nome da vacina (%s): ", vacina.getNome());
                String nome = scanner.nextLine();
                nome = nome.isBlank() ? vacina.getNome() : nome;
                vacina.setNome(nome);

                // Descrição
                System.out.printf("Digite a nova descrição da vacina (%s): ", vacina.getDescricao());
                String descricao = scanner.nextLine();
                descricao = descricao.isBlank() ? vacina.getDescricao() : descricao;
                vacina.setDescricao(descricao);

                List<Vacina> listVacina = new ArrayList<>();
                listVacina.add(vacina);
                Printer.showListVacinas(listVacina);

                System.out.println("Deseja salvar alterações (s/n) ? ");
                String resposta = scanner.nextLine();

                if (resposta.equalsIgnoreCase("s")) {

                    PreparedStatement statement = executePreparedStatement(
                            connection,
                            vacina.getNome(),
                            vacina.getDescricao(),
                            SQL.editInfoVacina()
                    );
                    statement.setInt(3, idVacina);
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

    private PreparedStatement executePreparedStatement(Connection connection, String nome, String descricao, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setString(2, descricao);
        return statement;
    }

    public void deleteVacina() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            System.out.println("Digite o id correspondente a vacina que deseja excluir: ");
            int idVacina = scanner.nextInt();

            String sql = SQL.deleteVacina();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idVacina);
            statement.executeUpdate();

            Printer.registroDeletado();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTodasVacinas() {
        try {
            Connection connection = DatabaseConnection.getConnection();

            System.out.println("Tem certeza que deseja excluir todos os registros de vacinas (s/n)? ");
            String resposta = scanner.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                String sql = SQL.deleteTodasVacinas();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();

                Printer.registroDeletado();

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
            Connection connection = DatabaseConnection.getConnection();

            String sql = vacinaDetails();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idVacina);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idVacina");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                vacina = new Vacina(idVacina, nome, descricao);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Erro ao obter os detalhes da vacina: " + e.getMessage());
        }

        return vacina;
    }
}
