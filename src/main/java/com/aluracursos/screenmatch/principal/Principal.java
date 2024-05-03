package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        //Mejoría, usando funciones LAMBDA
//        Función LAMBDA (argumento -> cuerpo de la Función)
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir todas las informaciones del tipo DatosEpisodio
        List<DatosEpisodio> datosEpisodios = temporadas.stream()    //Se va a trabajar con la lista de temporadas
                .flatMap(t -> t.episodios().stream())   //FlatMap, convierte cada uno de los elemento de temporada en una lista de episodios
                .collect(Collectors.toList()); //Se añaden los datos a una lista mutable


                                        //TOP 5 EPISODIOS
        //peek -> Se utiliza para "Espiar" los elementos del stream sin alterarlos
//        System.out.println("Top 5 Episodios.");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A) " + e))
//                //Ordena los datos por medio de la comparación de evaluaciones, y los trae de mayor a menor con el reversed
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo ordenación (M>m) " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercero Mapeo (m>M) " + e))
//                .limit(5) //Limitación a solo 5 elementos
//                .forEach(System.out::println);

        //              CONVIRTIENDO los datos a una lista del tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()    //Convertir c/elemento de esa temporada en una lista de Episodios
                        .map(d -> new Episodio(t.numeroTemporada(), d)))  //Transforma cada dato del tipo episodio en un nuevo episodio
                .collect(Collectors.toList());  //Añadir a la lista

//        episodios.forEach(System.out::println);

        //              Búsqueda de episodios a partir de X año

//        System.out.println("Por favor indica el año a partir del cual deseas ver los episodio: ");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd,MM,yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada " + e.getTemporada() +
//                                "Episodio " + e.getTitulo() +
//                                "Fecha de Lanzamiento " + e.getFechaDeLanzamiento().format(dtf) //Formatar la fecha de Lanzamiento
//                ));

        //      BUSCA EPISODIOS POR PEDAZO DE TITULO
        System.out.println("Por favor escribe el titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase())) //contains -> Devuelve el titulo si tiene alguna coincidencia con el parámetro pasado
                .findFirst();//findFirst -> Nos devuelve (un optional) la primera coincidencia que encuentre (Siempre será la misma)

        //SI el episodio buscado está, retornar los SOUT
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Los datos son: " + episodioBuscado.get()); //Si queremos TODOS los datos del episodio
                                                                //solo colocamos get() y si queremos algo más especifico colocamos get().get[ESPECIFICAR]()
        } else {
            System.out.println("Episodio NO encontrado");
        }

    }
}
