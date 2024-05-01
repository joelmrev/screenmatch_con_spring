package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Jackson va a ignorar aquellos datos que no se han mapeado dentro de esta clase
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(

        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion) {
}
