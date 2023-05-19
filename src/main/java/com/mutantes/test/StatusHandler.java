package com.mutantes.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusHandler implements StatusHandlerInterface {

    private DBConnection dbConnection;

    public StatusHandler() {
        dbConnection = new DBConnection();
    }
    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public ApiResponse handleRequest(Void input, Context context) {
        try (Connection connection = dbConnection.getConnection()) {
            int countMutantes = getCountMutantes(connection);
            int countNoMutantes = getCountNoMutantes(connection);
            double ratio = calculateRatio(countMutantes, countNoMutantes);
            String jsonResponse = createJsonResponse(countMutantes, countNoMutantes, ratio);

            return new ApiResponse(200, jsonResponse);
        } catch (SQLException e) {
            return new ApiResponse(500, "Internal Server Error");
        }
    }

    private int getCountMutantes(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM adn_resultado WHERE es_mutante = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1);
                }
            }
        }
        return 0;
    }

    private int getCountNoMutantes(Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM adn_resultado WHERE es_mutante = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, false);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt(1);
                }
            }
        }
        return 0;
    }

    protected double calculateRatio(int countMutantes, int countNoMutantes) {
        return countNoMutantes > 0 ? (double) countMutantes / countNoMutantes : countMutantes;
    }

    private String createJsonResponse(int countMutantes, int countNoMutantes, double ratio) {
        Gson gson = new Gson();
        JsonResponse jsonResponse = new JsonResponse(countMutantes, countNoMutantes, ratio);
        return gson.toJson(jsonResponse);
    }

    private class JsonResponse {
        private int count_mutant_dna;
        private int count_human_dna;
        private double ratio;

        public JsonResponse(int countMutantes, int countNoMutantes, double ratio) {
            this.count_mutant_dna = countMutantes;
            this.count_human_dna = countNoMutantes;
            this.ratio = ratio;
        }
    }
}
