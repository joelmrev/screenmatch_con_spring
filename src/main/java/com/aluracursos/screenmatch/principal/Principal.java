package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE ="https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=3eb9c1a2"; // FINAL: Determina que va a ser un CONSTANTE, y debe ser escrita en MAYÚSCULAS
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        // Busca los datos generales de las Series
        var nombreSerie = teclado.nextLine().toLowerCase();
        var json = consumoApi.obtenerDatos( URL_BASE + nombreSerie.replace(" ","+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i= 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoApi.obtenerDatos( URL_BASE + nombreSerie.replace(" ","+") + "&Season="+ i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporadas);
        }

//        temporadas.forEach(System.out::println);

        //Mostrar sólo el título de los Episodios para las temporadas
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {  //Itera la lista de temporadas para conseguir traer todos los episodios
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) { //Itera la lista de episodios para traer el título
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
//        Función LAMBDA (argumento -> cuerpo de la Función)
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));


    }
}
