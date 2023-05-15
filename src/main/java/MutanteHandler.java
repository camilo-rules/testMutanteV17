
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.google.gson.Gson;


public class MutanteHandler implements RequestHandler<String[], String> {
    @Override
    public String handleRequest(String[] dna, Context context)  {

            // Convierte el JSON a un objeto Java usando Gson
            Gson gson = new Gson();
            String requestBody = gson.toJson(dna);
            String[] dnaArray = gson.fromJson(requestBody, String[].class);

            // Verifica si el humano es mutante
            boolean esMutante = AnalizaMutante.esMutante(dnaArray);
            System.out.println(esMutante);

            // Construye la respuesta
            String response;
            int codeJson;
            if (esMutante) {
                response = "El humano es mutante";
                codeJson= 200;

            } else {
                response = "El humano no es mutante";
                codeJson= 403;
            }

            // Convierte la respuesta a JSON
            String jsonResponse = gson.toJson(response);

            return response;
    }
}