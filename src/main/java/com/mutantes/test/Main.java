package com.mutantes.test;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String, ApiResponse> {
    private MutanteHandler mutanteHandler = new MutanteHandler();

    @Override
    public ApiResponse handleRequest(String input, Context context) {

        // Invocar el MutanteHandler
        ApiResponse response = mutanteHandler.handleRequest(new String[]{input}, context);
        return response;
    }
}
