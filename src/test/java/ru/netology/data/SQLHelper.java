package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var connection = getConnection()) {
            var result = runner.query(connection, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(result);
        }
    }

    @SneakyThrows
    public static void cleanDB() {
        var connection = getConnection();
        runner.update(connection, "DELETE FROM auth_codes");
        runner.update(connection, "DELETE FROM card_transactions");
        runner.update(connection, "DELETE FROM cards");
        runner.update(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConnection();
        runner.execute(connection, "DELETE FROM auth_codes");
    }
}
