package com.mutantes.test;

import org.junit.Test;
import static org.junit.Assert.*;
import com.mutantes.test.AnalizaMutante;

public class MutanteHandlerTest {

    @Test
    public void testEsMutante() {
        // Definir el ADN de prueba
        String[] dnaMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        String[] dnaNoMutante = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaMissingElement = {"ATGCG", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaWrongElement = {"ATGCY", "CAGTGY", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        String[] dnaEmptyElement = {};

        // Verificar que la secuencia de ADN mutante sea detectada como mutante
        boolean resultadoMutante = AnalizaMutante.esMutante(dnaMutante);
        assertTrue(resultadoMutante);

        // Verificar que la secuencia de ADN no mutante no sea detectada como mutante
        boolean resultadoNoMutante = AnalizaMutante.esMutante(dnaNoMutante);
        assertFalse(resultadoNoMutante);

        // Verificar que la secuencia de ADN genere error por cadena errada
        boolean resultadoMissingElement = AnalizaMutante.esMutante(dnaMissingElement);
        assertFalse(resultadoMissingElement);

        // Verificar que la secuencia de ADN genere error caracter no válido en la secuencia
        boolean resultadoWrongElement = AnalizaMutante.esMutante(dnaWrongElement);
        assertFalse(resultadoWrongElement);

        // Verificar que la secuencia de ADN genere error por cadena vacía
        boolean resultadoEmptyElement = AnalizaMutante.esMutante(dnaEmptyElement);
        assertFalse(resultadoEmptyElement);
    }
}
