package com.mutantes.test;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public interface StatusHandlerInterface {

    ApiResponse handleRequest(Void input, Context context);
}