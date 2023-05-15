import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String[], String> {
    @Override
    public String handleRequest(String[] dna, Context context) {
        // Código de manejo de la solicitud
        MutanteHandler mutanteHandler = new MutanteHandler();
        return mutanteHandler.handleRequest(dna, context);
    }
}