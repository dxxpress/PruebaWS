package com.example.dx_deas.pruebaws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class splash extends Activity {

    private String usuario , contraseña , tran , login , password , mensaje;
    private SoapPrimitive resultString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences ("credenciales", Context.MODE_PRIVATE);

                usuario = preferences.getString("user","");
                contraseña = preferences.getString("pass","");

                if (usuario != "" && contraseña != ""){

                    login = usuario;
                    password = contraseña;

                    SegundoPlano segundoPlano = new SegundoPlano();
                    segundoPlano.execute();

                    }else if (usuario == "" && contraseña == ""){

                    Intent i = new Intent(splash.this, Login.class);
                    startActivity(i);

                }

            }
        },5000);
    }

    private class SegundoPlano extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "Login";
            String NAMESPACE  = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";


            try{

                SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
                Request.addProperty("login", login);
                Request.addProperty("password", password);

                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport= new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, soapEnvelope);


                resultString = (SoapPrimitive) soapEnvelope.getResponse();


                mensaje= "OK";

            }catch (Exception ex){

                mensaje = "ERROR: " +ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {

                //Respuesta de la llamada , es un json
                tran = resultString.toString();



                //Libreria gson se utliza para traducir de json a string y viceversa
                Gson gson = new Gson();

                //Retira la palabra usuario y los corchetes para el uso de la libreria gson
                String reusu = tran.replace("{\"Usuario\":[{", "{");
                String reusu2 = reusu.replace("}] }", "}");

                //Se usa la libreria para traducir el json , el string obtenido se almacena en la libreria CUsuario
                Usuario Usuario1 = gson.fromJson(reusu2, Usuario.class);

                String nombreOperador = Usuario1.getNombreOperador();
                String idEmpresa = Usuario1.getIdEmpresa();
                String idUsuario = Usuario1.getIdUsuario();
                String nombreUsuario = Usuario1.getNombreUsuario();
                String idUnidad = Usuario1.getIdUnidad();
                String claveUnidad = Usuario1.getClaveUnidad();
                String idOperador = Usuario1.getIdOperador();
                String idFlota = Usuario1.getIdFlota();
                String idViaje = Usuario1.getIdViaje();

                //Se llama a la siguiente actividad y se envian variables
                Intent i = new Intent(splash.this, menuPrincipal.class);
                i.putExtra("nombreOperadorS", nombreOperador);
                i.putExtra("nombreUsuarioS", nombreUsuario);
                i.putExtra("idUnidadS", idUnidad);
                i.putExtra("idUsuarioS", idUsuario);
                i.putExtra("idOperadorS", idOperador);
                i.putExtra("idViajeS", idViaje);
                startActivity(i);


            }catch (Exception ex){
                Toast.makeText(splash.this, "Error 404", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(aVoid);
        }
    }
}
