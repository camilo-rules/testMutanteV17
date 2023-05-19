package com.mutantes.test;

import org.junit.Test;
import static org.junit.Assert.*;
import com.mutantes.test.AnalizaMutante;

public class AnalizaMutanteTest {

    @Test
    public void testEsMutante() {
        // Definir el ADN de prueba
        String[] dnaMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String[] dnaNoMutante = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

        // Verificar que la secuencia de ADN mutante sea detectada como mutante
        boolean mutanteResult = AnalizaMutante.esMutante(dnaMutante);
        assertTrue(mutanteResult);

        // Verificar que la secuencia de ADN no mutante no sea detectada como mutante
        boolean noMutanteResult = AnalizaMutante.esMutante(dnaNoMutante);
        assertFalse(noMutanteResult);
    }

    @Test
    public void testValidarSecuencia() {
        // Definir secuencias de ADN de prueba
        String sequenceMutante = "AAAA";
        String sequenceNoMutante = "ATCG";

        // Verificar que la secuencia mutante sea detectada como mutante
        boolean mutanteResult = AnalizaMutante.validarSecuencia(sequenceMutante);
        assertTrue(mutanteResult);

        // Verificar que la secuencia no mutante no sea detectada como mutante
        boolean noMutanteResult = AnalizaMutante.validarSecuencia(sequenceNoMutante);
        assertFalse(noMutanteResult);
    }
}
