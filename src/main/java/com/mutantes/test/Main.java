package com.mutantes.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Main implements RequestHandler<String, ApiResponse> {
    private MutanteHandlerInterface mutanteHandler;
    private StatusHandlerInterface statusHandler;

    public Main() {
        this.mutanteHandler = new MutanteHandler();
        this.statusHandler = new StatusHandler();
    }

    public Main(MutanteHandlerInterface mutanteHandler, StatusHandlerInterface statusHandler) {
        this.mutanteHandler = mutanteHandler;
        this.statusHandler = statusHandler;
    }

    @Override
    public ApiResponse handleRequest(String input, Context context) {
        if (input != null && input.equalsIgnoreCase("status")) {
            return statusHandler.handleRequest(null, context);
        } else {
            ApiResponse response = mutanteHandler.handleRequest(new String[]{input}, context);
            return response;
        }
    }
}
