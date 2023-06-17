package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private DatabaseConnection() {
    }

    /*
     * delegando a responsabilidade de config da conexão do banco aqui
     */
    public static Connection getConnection() {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/projetoIntegrador1";
            String username = "kemmyps";
            String password = "";

            return DriverManager.getConnection(jdbcUrl, username, password);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
