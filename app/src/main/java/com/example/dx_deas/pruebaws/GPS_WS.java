package com.example.dx_deas.pruebaws;

import android.content.Intent;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Date;

public class GPS_WS extends AsyncTask <Void,Void,Void>{

    String fecha;
    String SOAP_ACTION;
    String METHOD_NAME ;
    String NAMESPACE ;
    String URL ;
    String latitud;
    String longitud;
    String mensaje = "";
    String idUnidad;
    String idOperador;


    public GPS_WS(String latitud, String longitud, String idUnidad, String idOperador) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.idUnidad = idUnidad;
        this.idOperador = idOperador;
    }

    @Override
    protected Void doInBackground(Void... voids) {
            SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212" ;
            METHOD_NAME = "PosicionSave";
            NAMESPACE = "http://dxxpress.net/wsInspeccion/";
            URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";
            fecha = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());


            try {

                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("latitud", latitud);
                Request.addProperty("longitud", longitud);
                Request.addProperty("fecha", fecha);
                Request.addProperty("idUnidad", idUnidad);
                Request.addProperty("idOperador", idOperador);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);


                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, soapEnvelope);

                SoapPrimitive ResultString = (SoapPrimitive) soapEnvelope.getResponse();

                mensaje = ResultString.toString();

                System.out.println("SE INSERTO A LAS : " + fecha + "  " + latitud + " " + longitud );



            } catch (Exception ex) {

                mensaje = ex.getMessage();
                System.out.println("Excepcion en Servicio SOAP : " + mensaje );
            }

            return null;
        }
}
