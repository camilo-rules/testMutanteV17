import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public interface CustomRequestHandler extends RequestHandler<String[], String> {
    @Override
    String handleRequest(String[] dna, Context context);
}