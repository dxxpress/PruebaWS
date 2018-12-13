package com.example.dx_deas.pruebaws;


public class Tramos {

    private String nombre ;
    private String horaCompromiso ;
    private String estatus ;
    private String secuencia ;
    private String fechaEntrada ;
    private String fechaSalida ;

    public Tramos(String nombre, String horaCompromiso, String estatus, String secuencia, String fechaEntrada, String fechaSalida) {
        this.nombre = nombre;
        this.horaCompromiso = horaCompromiso;
        this.estatus = estatus;
        this.secuencia = secuencia;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHoraCompromiso() {
        return horaCompromiso;
    }

    public String getEstatus() {
        return estatus;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    @Override
    public String toString() {
        return this. nombre + "\n" ;
    }



}
