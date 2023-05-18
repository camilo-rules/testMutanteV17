import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusHandler implements RequestHandler<Void, ApiResponse> {

    private DBConnection dbConnection;

    public StatusHandler() {
        dbConnection = new DBConnection();
    }

    @Override
    public ApiResponse handleRequest(Void input, Context context) {
        try (Connection connection = dbConnection.getConnection()) {
            // Consulta la cantidad de mutantes
            String mutantesQuery = "SELECT COUNT(*) FROM adn_resultado WHERE es_mutante = ?";
            try (PreparedStatement mutantesStatement = connection.prepareStatement(mutantesQuery)) {
                mutantesStatement.setBoolean(1, true);
                try (ResultSet mutantesResult = mutantesStatement.executeQuery()) {
                    if (mutantesResult.next()) {
                        int mutantesCount = mutantesResult.getInt(1);
                        // Consulta la cantidad de no mutantes
                        String noMutantesQuery = "SELECT COUNT(*) FROM adn_resultado WHERE es_mutante = ?";
                        try (PreparedStatement noMutantesStatement = connection.prepareStatement(noMutantesQuery)) {
                            noMutantesStatement.setBoolean(1, false);
                            try (ResultSet noMutantesResult = noMutantesStatement.executeQuery()) {
                                if (noMutantesResult.next()) {
                                    int noMutantesCount = noMutantesResult.getInt(1);
                                    // Calcula el ratio entre mutantes y no mutantes
                                    int ratio = (int) ((double) mutantesCount / (mutantesCount + noMutantesCount) * 100);
                                    // Construye la respuesta
                                    String response = "Estado de la base de datos:\n"
                                            + "Mutantes: " + mutantesCount + "\n"
                                            + "No mutantes: " + noMutantesCount + "\n"
                                            + "Ratio: " + ratio + "%";
                                    return new ApiResponse(200, response);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ApiResponse(500, "Internal Server Error");
    }
}
