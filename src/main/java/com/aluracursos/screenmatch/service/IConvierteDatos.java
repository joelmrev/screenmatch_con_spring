package com.aluracursos.screenmatch.service;

public interface IConvierteDatos {

    //Tipos de Datos Genéricos
    <T> T obtenerDatos(String json, Class<T> clase);
}
