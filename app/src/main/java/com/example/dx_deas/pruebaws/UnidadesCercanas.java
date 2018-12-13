package com.example.dx_deas.pruebaws;

public class UnidadesCercanas {

    private String idUnidad ;
    private String unidad ;
    private String idOperador;
    private String operador;
    private String telefono;
    private Double latitud;
    private Double longitud;

    public UnidadesCercanas(String idUnidad, String unidad, String idOperador, String operador, String telefono, Double latitud, Double longitud) {
        this.idUnidad = idUnidad;
        this.unidad = unidad;
        this.idOperador = idOperador;
        this.operador = operador;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public String getOperador() {
        return operador;
    }

    public String getTelefono() {
        return telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }
}
