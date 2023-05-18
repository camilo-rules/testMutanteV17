package com.mutantes.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

public class MutanteHandler implements RequestHandler<String[], ApiResponse> {
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
            try (Connection connection = dbConnection.getConnection()) {
                // Verifica si el registro de ADN ya existe
                String selectQuery = "SELECT * FROM adn_resultado WHERE adn = ?";
                try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                    selectStatement.setString(1, requestBody);
                    try (ResultSet resultSet = selectStatement.executeQuery()) {
                        if (!resultSet.next()) {
                            // El registro de ADN no existe, se realiza la inserción
                            String insertQuery = "INSERT INTO adn_resultado (adn, es_mutante) VALUES (?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                insertStatement.setString(1, requestBody);
                                insertStatement.setBoolean(2, esMutante);
                                insertStatement.executeUpdate();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                // Manejar errores de conexión o inserción en la base de datos
                e.printStackTrace();
                return new ApiResponse(403, "Forbidden");
            } finally {
                dbConnection.closeConnection();
            }

            // Crea una instancia de ApiResponse con el statusCode y el response
            ApiResponse apiResponse = new ApiResponse(statusCode, response);

            return apiResponse;

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(403, "Forbidden");
        }
    }

    private boolean validarSecuenciaADN(String[] dna) {
        Pattern pattern = Pattern.compile("^[ATCG]+$");
        for (String sequence : dna) {
            if (!pattern.matcher(sequence).matches()) {
                return false;
            }
        }
        return true;
    }
}
