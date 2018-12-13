package com.example.dx_deas.pruebaws;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.dx_deas.pruebaws.R.*;
import static com.example.dx_deas.pruebaws.R.layout.*;


public class Menu extends AppCompatActivity {

    private TextView resuOp;
    private ListView lista;

    private TextView resuPrueba;
    String viaje;
    SoapPrimitive resultString;
    String mensaje;
    String nombreOperador;
    String nombreUsuario;
    String idUnidad;
    String idOperador;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(menu);





        //Obtiene las variables de la actividad pasada
        nombreOperador = getIntent().getStringExtra("nombreOperador");
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        idUnidad = getIntent().getStringExtra("idUnidad");
        idOperador = getIntent().getStringExtra("idOperador");


        resuOp = (TextView) findViewById(id.textViewUsu);
        resuOp.setText("Bienvenido :  \n" + nombreUsuario + " " + nombreOperador);




        //SegundoPlano tarea = new SegundoPlano();
        //tarea.execute();
        runtime_permissions();
    }

    @Override
    public void onBackPressed() {
    }


    private class SegundoPlano extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            viaje = resultString.toString();
            if (viaje.length() <= 15 )
                Toast.makeText(Menu.this, "Error 404", Toast.LENGTH_SHORT).show();
            else {
                try {



                    Gson gson = new Gson();
                    String reusu= viaje.replace("{\"Viaje\":[{", "[{");
                    String reusu2= reusu.replace("}] }", "}]");

                    ViajeActual [] viajeActual1 = gson.fromJson(reusu2, ViajeActual[].class);







                }catch (Exception e){
                    System.out.println("Error : " +  e );
                }
            }

           // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, viajeActual1.);


            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            convertir();
            return null;
        }
    }

    private void convertir() {


        String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
        String METHOD_NAME = "ViajeActual";
        String NAMESPACE  = "http://dxxpress.net/wsInspeccion/";
        String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";
        String idUsuario = getIntent().getStringExtra("idUsuario");
        String idUnidad = getIntent().getStringExtra("idUnidad");


        try{


            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("idUnidad", idUnidad);
            Request.addProperty("idUsuario", idUsuario);


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


    }






    //Si la API es mayor a la 23 pide permisos
    private boolean runtime_permissions(){
        if (Build.VERSION.SDK_INT>=23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        { requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }else {
            //Se Inicia el servicio
            Intent i = new Intent(Menu.this, GPS_Service.class);
            i.putExtra("idUnidad", idUnidad);
            i.putExtra("idOperador", idOperador);
            startService(i);
            return false;

        }
    }
    //Se solicita permisos al usuario  y se Inicia el servicio
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode== 100){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager
                    .PERMISSION_GRANTED) {
                //Se vuelve a revisar los permisos
                runtime_permissions();
            }
        }

    }

    @Override
    protected void onDestroy() {
        //Cuando se destruye la actividad el servicio en segundo plano se destruye
       stopService(new Intent(this, GPS_Service.class));
        super.onDestroy();
    }

}
