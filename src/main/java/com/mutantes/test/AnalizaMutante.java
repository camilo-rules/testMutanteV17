package com.mutantes.test;

/**
 * Clase que se encarga de analizar si un humano es mutante basado en su secuencia de ADN.
 */
public class AnalizaMutante {
    /**
     * Verifica si un humano es mutante según su secuencia de ADN.
     *
     * @param dna la secuencia de ADN representada como un array de Strings.
     * @return true si el humano es mutante, false en caso contrario.
     * @author Juan Camilo Gomez
     */
    public static boolean esMutante(String[] dna) {
        /* Metodo estatico puede ser llamado sin instanciar la clase
           Recibe un array de string
        */
        int n = dna.length; //var int con longitud de array para determinar tamaño de matriz

        // Verificar horizontalmente con el metodo "validarSecuencia" si es mutante
        for (int i = 0; i < n; i++) {
            if (validarSecuencia(dna[i])) {
                return true;
            }
        }

        /* Verificar verticalmente usando stringBuilder para construir las secuencias
           usando Append para concantenar al final(eficiencia).
           Se llama al metodo validarSecuencia para verificar patrón vertical
           usando el método toString para obtener la representación de la cadena construida
         */
        for (int j = 0; j < n; j++) {
            StringBuilder verticalSequence = new StringBuilder();
            for (int i = 0; i < n; i++) {
                verticalSequence.append(dna[i].charAt(j));
            }
            if (validarSecuencia(verticalSequence.toString())) {
                return true;
            }
        }

        /* Logica igual al anterior pero validando diagonalmente
           de izquierda a derecha
         */
        for (int i = 0; i < n; i++) {
            StringBuilder diagonalSequence = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                diagonalSequence.append(dna[i + j].charAt(j));
            }
            if (validarSecuencia(diagonalSequence.toString())) {
                return true;
            }
        }

        /* Logica igual al anterior pero validando diagonalmente
           de derecha a izquierda
         */
        for (int i = 1; i < n; i++) {
            StringBuilder diagonalSequence = new StringBuilder();
            for (int j = 0; j < n - i; j++) {
                diagonalSequence.append(dna[j].charAt(i + j));
            }
            if (validarSecuencia(diagonalSequence.toString())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Verifica si una secuencia de ADN tiene una secuencia mutante.
     *
     * @param sequence la secuencia de ADN a verificar.
     * @return true si la secuencia de ADN es mutante, false en caso contrario.
     */
    private static boolean validarSecuencia(String sequence) {
        int count = 0;
        //Se declara una variable prevChar de tipo char e inicializada con el carácter nulo ('\0').
        // Esta variable almacena el carácter anterior mientras se recorre la secuencia.
        char prevChar = '\0';
        for (int i = 0; i < sequence.length(); i++) {
            char currentChar = sequence.charAt(i);

            if (currentChar == prevChar) {
                count++;
                if (count >= 4) {
                    return true;
                }
            } else {
                count = 1;
                prevChar = currentChar;
            }
        }

        return false;
    }
}
