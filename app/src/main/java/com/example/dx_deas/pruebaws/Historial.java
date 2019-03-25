package com.example.dx_deas.pruebaws;

public class Historial {

    private String idViaje ;
    private  String folioViaje ;
    private  String tracking ;
    private  String idCliente;
    private String  idRuta;
    private String  nombreRuta;
    private String  secuencia;
    private String  idDetalleViaje;
    private String  idDetalleViajeDestino;
    private String  idOperador ;
    private String  idOperador2;
    private String  idRemolquePrincipal;
    private String  nombreOrigen;
    private String  fechaVentanaOrigen;
    private String   fechaEntradaOrigen;
    private String   fechaSalidaOrigen;
    private String   nombreDestino;
    private String   fechaVentanaDestino;
    private String   fechaEntradaDestino;
    private String   fechaSalidaDestino;
    private String   direccionDestino;
    private String   descripcionCarga;
    private String   descripcionTipoMovimiento;
    private String   nombreEstatus;
    private String   nombreUnidad;
    private String   nombreOperador;
    private  String   nombreOperador2;
    private String   nombreRemolquePrincipal;
    private String   tipoEmpresaOrigen;
    private String   tipoEmpresaDestino;

    public Historial(String idViaje, String folioViaje, String tracking, String idCliente, String idRuta, String nombreRuta, String secuencia, String idDetalleViaje, String idDetalleViajeDestino, String idOperador, String idOperador2, String idRemolquePrincipal, String nombreOrigen, String fechaVentanaOrigen, String fechaEntradaOrigen, String fechaSalidaOrigen, String nombreDestino, String fechaVentanaDestino, String fechaEntradaDestino, String fechaSalidaDestino, String direccionDestino, String descripcionCarga, String descripcionTipoMovimiento, String nombreEstatus, String nombreUnidad, String nombreOperador, String nombreOperador2, String nombreRemolquePrincipal, String tipoEmpresaOrigen, String tipoEmpresaDestino) {
        this.idViaje = idViaje;
        this.folioViaje = folioViaje;
        this.tracking = tracking;
        this.idCliente = idCliente;
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.secuencia = secuencia;
        this.idDetalleViaje = idDetalleViaje;
        this.idDetalleViajeDestino = idDetalleViajeDestino;
        this.idOperador = idOperador;
        this.idOperador2 = idOperador2;
        this.idRemolquePrincipal = idRemolquePrincipal;
        this.nombreOrigen = nombreOrigen;
        this.fechaVentanaOrigen = fechaVentanaOrigen;
        this.fechaEntradaOrigen = fechaEntradaOrigen;
        this.fechaSalidaOrigen = fechaSalidaOrigen;
        this.nombreDestino = nombreDestino;
        this.fechaVentanaDestino = fechaVentanaDestino;
        this.fechaEntradaDestino = fechaEntradaDestino;
        this.fechaSalidaDestino = fechaSalidaDestino;
        this.direccionDestino = direccionDestino;
        this.descripcionCarga = descripcionCarga;
        this.descripcionTipoMovimiento = descripcionTipoMovimiento;
        this.nombreEstatus = nombreEstatus;
        this.nombreUnidad = nombreUnidad;
        this.nombreOperador = nombreOperador;
        this.nombreOperador2 = nombreOperador2;
        this.nombreRemolquePrincipal = nombreRemolquePrincipal;
        this.tipoEmpresaOrigen = tipoEmpresaOrigen;
        this.tipoEmpresaDestino = tipoEmpresaDestino;
    }



    public String getIdViaje() {
        return idViaje;
    }

    public String getFolioViaje() {
        return folioViaje;
    }

    public String getTracking() {
        return tracking;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public String getSecuencia() {
        return secuencia;
    }

    public String getIdDetalleViaje() {
        return idDetalleViaje;
    }

    public String getIdDetalleViajeDestino() {
        return idDetalleViajeDestino;
    }

    public String getIdOperador() {
        return idOperador;
    }

    public String getIdOperador2() {
        return idOperador2;
    }

    public String getIdRemolquePrincipal() {
        return idRemolquePrincipal;
    }

    public String getNombreOrigen() {
        return nombreOrigen;
    }

    public String getFechaVentanaOrigen() {
        return fechaVentanaOrigen;
    }

    public String getFechaEntradaOrigen() {
        return fechaEntradaOrigen;
    }

    public String getFechaSalidaOrigen() {
        return fechaSalidaOrigen;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public String getFechaVentanaDestino() {
        return fechaVentanaDestino;
    }

    public String getFechaEntradaDestino() {
        return fechaEntradaDestino;
    }

    public String getFechaSalidaDestino() {
        return fechaSalidaDestino;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public String getDescripcionCarga() {
        return descripcionCarga;
    }

    public String getDescripcionTipoMovimiento() {
        return descripcionTipoMovimiento;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public String getNombreOperador() {
        return nombreOperador;
    }

    public String getNombreOperador2() {
        return nombreOperador2;
    }

    public String getNombreRemolquePrincipal() {
        return nombreRemolquePrincipal;
    }

    public String getTipoEmpresaOrigen() {
        return tipoEmpresaOrigen;
    }

    public String getTipoEmpresaDestino() {
        return tipoEmpresaDestino;
    }


}
