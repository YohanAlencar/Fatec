package br.com.tarefas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/gestao_tarefas";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin"; // <-- Altere aqui para a sua senha

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
