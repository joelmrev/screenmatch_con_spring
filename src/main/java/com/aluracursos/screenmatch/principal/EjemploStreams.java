package com.aluracursos.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo() {
        List<String> nombres = Arrays.asList("Brenda", "Luis", "María Fernanda", "Eric", "Genesys");

        nombres.stream()
                .sorted()
                .limit(4)
                .filter(n -> n.startsWith("L")) //Filtro que Nos traerá los nombres que comiencen con la letra L
                .map(n -> n.toUpperCase()) //Map convierte/Transforma el n=nombre a letras mayúsculas
                .forEach(System.out::println);
    }
}
