import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import com.google.gson.Gson;

public class MutanteHandler implements RequestHandler<String[], String> {
    private static final String DYNAMO_TABLE_NAME = "adn_resultados";
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
                codeJson= 200;
            }

            //Configura el cliente de DynamoDB
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
            DynamoDB dynamoDB = new DynamoDB(client);

            //Obtener table adn_resultados
            Table table = dynamoDB.getTable(DYNAMO_TABLE_NAME);

            //Validar si ya existe registro en BD
            GetItemSpec getItemSpec = new GetItemSpec()
                    .withPrimaryKey("adn", requestBody);
            Item existingItem = table.getItem(getItemSpec);

            if (existingItem == null){
                //crear Item en la tabla
                Item newItem = new Item()
                        .withPrimaryKey("adn", requestBody)
                        .withBoolean("es_mutante", esMutante);

                //guardar Item
                table.putItem(newItem);
            }

            // Convierte la respuesta a JSON
            String jsonResponse = gson.toJson(response);

            return response;
    }
}