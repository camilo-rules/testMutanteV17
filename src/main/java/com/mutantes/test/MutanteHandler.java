package com.mutantes.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

public class MutanteHandler implements MutanteHandlerInterface {
    private DBConnection dbConnection;

    public MutanteHandler() {
        dbConnection = new DBConnection();
    }

    @Override
    public ApiResponse handleRequest(String[] dna, Context context) {
        try {
            // Convierte el JSON a un objeto Java usando Gson
            Gson gson = new Gson();
            String requestBody = gson.toJson(dna);
            System.out.println("requestBody: " + requestBody);
            String[] dnaArray = gson.fromJson(requestBody, String[].class);

            // Verifica si la secuencia de ADN contiene caracteres no permitidos
            if (!validarSecuenciaADN(dnaArray)) {
                return new ApiResponse(403, "Forbidden");
            }

            // Verifica si el humano es mutante
            boolean esMutante = AnalizaMutante.esMutante(dnaArray);
            System.out.println(esMutante);

            // Construye la respuesta
            String response;
            int statusCode;
            if (esMutante) {
                response = "El humano es mutante";
                statusCode = 200;
            } else {
                response = "El humano no es mutante";
                statusCode = 200;
            }

            // Realiza la inserción en la base de datos RDS
            try (Connection connection = getDatabaseConnection()) {
                if (!isADNRecordExists(connection, requestBody)) {
                    insertADNRecord(connection, requestBody, esMutante);
                }
            } catch (SQLException e) {
                // Manejar errores de conexión o inserción en la base de datos
                e.printStackTrace();
                return new ApiResponse(403, "Forbidden");
            } finally {
                closeDatabaseConnection();
            }

            // Crea una instancia de ApiResponse con el statusCode y el response
            ApiResponse apiResponse = new ApiResponse(statusCode, response);

            return apiResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(403, "Forbidden");
        }
    }

    protected Connection getDatabaseConnection() throws SQLException {
        return dbConnection.getConnection();
    }

    protected void closeDatabaseConnection() {
        dbConnection.closeConnection();
    }

    protected boolean isADNRecordExists(Connection connection, String adn) throws SQLException {
        String selectQuery = "SELECT * FROM adn_resultado WHERE adn = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setString(1, adn);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    protected void insertADNRecord(Connection connection, String adn, boolean esMutante) throws SQLException {
        String insertQuery = "INSERT INTO adn_resultado (adn, es_mutante) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, adn);
            insertStatement.setBoolean(2, esMutante);
            insertStatement.executeUpdate();
        }
    }

    protected boolean validarSecuenciaADN(String[] dna) {
        Pattern pattern = Pattern.compile("^[ATCG]+$");
        for (String sequence : dna) {
            if (!pattern.matcher(sequence).matches()) {
                return false;
            }
        }
        return true;
    }
}