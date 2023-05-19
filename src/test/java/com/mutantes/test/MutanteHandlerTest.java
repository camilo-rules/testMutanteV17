package com.mutantes.test;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import com.amazonaws.services.lambda.runtime.Context;
import com.mutantes.test.MutanteHandler;

public class MutanteHandlerTest {

    @Test
    public void testEsMutante() {
        // Definir el ADN de prueba
        String[] dnaMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String[] dnaNoMutante = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaMissingElement = {"ATGCGG", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaWrongElement = {"ATGCYR", "CAGTGY", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaEmptyElement = {};

        // Crear una instancia de MutanteHandler
        MutanteHandler mutanteHandler = new MutanteHandler();

        // Crear un objeto Context simulado con Mockito
        Context context = Mockito.mock(Context.class);

        // Configurar el comportamiento esperado del objeto Context simulado
        Mockito.when(context.getAwsRequestId()).thenReturn("dummy-request-id");

        // Verificar que la secuencia de ADN mutante sea detectada como mutante
        ApiResponse mutanteResponse = mutanteHandler.handleRequest(dnaMutante, context);
        assertEquals(200, mutanteResponse.getStatusCode());
        assertEquals("El humano es mutante", mutanteResponse.getResponse());

        // Verificar que la secuencia de ADN no mutante no sea detectada como mutante
        ApiResponse noMutanteResponse = mutanteHandler.handleRequest(dnaNoMutante, context);
        assertEquals(200, noMutanteResponse.getStatusCode());
        assertEquals("El humano no es mutante", noMutanteResponse.getResponse());

        // Verificar que la secuencia de ADN genere error por cadena errada
        ApiResponse missingElementResponse = mutanteHandler.handleRequest(dnaMissingElement, context);
        assertEquals(200, missingElementResponse.getStatusCode());
        assertEquals("El humano no es mutante", missingElementResponse.getResponse());

        // Verificar que la secuencia de ADN genere error caracter no válido en la secuencia
        ApiResponse wrongElementResponse = mutanteHandler.handleRequest(dnaWrongElement, context);
        assertEquals(403, wrongElementResponse.getStatusCode());
        assertEquals("Forbidden", wrongElementResponse.getResponse());

        // Verificar que la secuencia de ADN genere error por cadena vacía
        ApiResponse emptyElementResponse = mutanteHandler.handleRequest(dnaEmptyElement, context);
        assertEquals(200, emptyElementResponse.getStatusCode());
        assertEquals("El humano no es mutante", emptyElementResponse.getResponse());
    }
}
