package com.mutantes.test;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MainTest {

    @Test
    public void testHandleRequest_Status() {
        // Configuración de la prueba
        StatusHandlerInterface mockStatusHandler = mock(StatusHandlerInterface.class);
        Context mockContext = mock(Context.class);
        Main main = new Main(null, mockStatusHandler);

        // Ejecución de la prueba
        main.handleRequest("status", mockContext);

        // Verificación de que se llame al método correcto en el StatusHandler
        verify(mockStatusHandler, times(1)).handleRequest(eq(null), eq(mockContext));
    }

    @Test
    public void testHandleRequest_Mutante() {
        // Configuración de la prueba
        MutanteHandlerInterface mockMutanteHandler = mock(MutanteHandlerInterface.class);
        Context mockContext = mock(Context.class);
        Main main = new Main(mockMutanteHandler, null);
        String input = "ATCG";

        // Ejecución de la prueba
        main.handleRequest(input, mockContext);

        // Verificación de que se llame al método correcto en el MutanteHandler
        verify(mockMutanteHandler, times(1)).handleRequest(eq(new String[]{input}), eq(mockContext));
    }
}
