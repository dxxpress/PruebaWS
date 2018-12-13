package com.example.dx_deas.pruebaws;

import java.util.Date;

public class Mensajes {

    private String id ;
    private String idViaje;
    private String idDetalleViaje;


    public String getId() { return id; }

    public String getIdViaje() { return idViaje; }

    public String getIdDetalleViaje() { return idDetalleViaje; }

    public Date getGmt() {return gmt; }

    private Date gmt ;



    public void setId(String id) {
        this.id = id;
    }

    public void setIdViaje(String idViaje) {
        this.idViaje = idViaje;
    }

    public void setIdDetalleViaje(String idDetalleViaje) {
        this.idDetalleViaje = idDetalleViaje;
    }

    public void setGmt(Date gmt) {
        this.gmt = gmt;
    }
}
