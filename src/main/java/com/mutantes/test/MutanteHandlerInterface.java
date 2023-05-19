package com.mutantes.test;

import com.amazonaws.services.lambda.runtime.Context;

public interface MutanteHandlerInterface {
    ApiResponse handleRequest(String[] dna, Context context);

}