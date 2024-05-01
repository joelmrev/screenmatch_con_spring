package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosEpisodio(
        @JsonAlias("Title") String titulo,
        @JsonAlias("Season") String temporada,
        @JsonAlias("Episode") String episodio,
        @JsonAlias("Runtime") String duracion,
        @JsonAlias("Released") String fechaDeLanzamiento,
        @JsonAlias("imdbRating") String evaluacion) {
}
