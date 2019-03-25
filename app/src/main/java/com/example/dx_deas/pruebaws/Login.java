package com.example.dx_deas.pruebaws;



import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class Login extends AppCompatActivity {

    private EditText usu, pass;
    private Button ingre;
    String login;
    String password;
    String mensaje;
    String tran;
    SoapPrimitive resultString;
private static final int ERRO_DIALOG_REQUEST = 9001 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usu = (EditText) findViewById(R.id.editTextUser);
        pass = (EditText)findViewById(R.id.editTextPass);
        ingre = (Button)findViewById(R.id.btnIngresar);

        runtime_permissions();

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }


        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Login.this);
        if (code == ConnectionResult.SUCCESS) {
            // Do Your Stuff Here
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(code)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Login.this,code,ERRO_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this,"Wa can't make map requests", Toast.LENGTH_SHORT).show();
        }

        ingre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                login = usu.getText().toString();
                password = pass.getText().toString();


                //Filtros del login
                if (login.length()==0 && password.length()==0){
                    Toast.makeText(Login.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                }
                else if (login.length()==0 ){
                    Toast.makeText(Login.this, "Debes Ingresar un Usuario", Toast.LENGTH_SHORT).show();

                }
                else if (password.length()==0){
                    Toast.makeText(Login.this, "Debes Ingresar una Contra ", Toast.LENGTH_SHORT).show();

                }
                else if (login.length() !=0 && password.length() !=0) {
                    SegundoPlano tarea = new SegundoPlano();
                    tarea.execute();
                }



            }
        });
    }
    private boolean runtime_permissions(){
        if (Build.VERSION.SDK_INT>=23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        { requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }else {
            return false;
        }

    }
    //Se solicita permisos al usuario  y se Inicia el servicio
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode== 100){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager
                    .PERMISSION_GRANTED) {
            }
        }

    }

   //Tarea en segundo plano
    private class SegundoPlano extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(Void aVoid) {

            try {

            //Respuesta de la llamada , es un json
            tran = resultString.toString();


            //Si el usuario no existe el servicio regresara el string {"Usuario":[{}] }
            if (tran.length() <= 15) {
             Toast.makeText(Login.this, "Cuenta Equivocada", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences preferences = getSharedPreferences ("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user", login);
                editor.putString("pass", password);
                editor.commit();

            //Libreria gson se utliza para traducir de json a string y viceversa
             Gson gson = new Gson();

             //Retira la palabra usuario y los corchetes para el uso de la libreria gson
                String reusu = tran.replace("{\"Usuario\":[{", "{");
                String reusu2 = reusu.replace("}] }", "}");

                //Se usa la libreria para traducir el json , el string obtenido se almacena en la libreria Usuario
                 Usuario usuario1 = gson.fromJson(reusu2, Usuario.class);

                String nombreOperador = usuario1.getNombreOperador();
                String idEmpresa = usuario1.getIdEmpresa();
                String idUsuario = usuario1.getIdUsuario();
                String nombreUsuario = usuario1.getNombreUsuario();
                String idUnidad = usuario1.getIdUnidad();
                String claveUnidad = usuario1.getClaveUnidad();
                String idOperador = usuario1.getIdOperador();
                String idFlota = usuario1.getIdFlota();
                String idViaje = usuario1.getIdViaje();


                String nombre = "Pepe La Rana";


                //Se llama a la siguiente actividad y se envian variables
                Intent i = new Intent(Login.this, menuPrincipal.class);
                i.putExtra("nombreOperador", nombreOperador);
                i.putExtra("idFlota", idFlota);
                i.putExtra("idUnidad", idUnidad);
                i.putExtra("idUsuario", idUsuario);
                //i.putExtra("idOperador", idOperador);
                i.putExtra("idViaje", idViaje);
                startActivity(i);
    }

        }catch (Exception ex){
            Toast.makeText(Login.this, "Error 404", Toast.LENGTH_SHORT).show();
        }

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            convertir();
            return null;
        }
    }

    @Override
    public void onBackPressed() {
    }

    //Metodo para llamar al servicio soap
    private void convertir() {



        //CADENAS DE CONEXION CON EL WEB SERVICE QUE SE OBTIENE DEL WSDL

        String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
        String METHOD_NAME = "Login";
        String NAMESPACE  = "http://dxxpress.net/wsInspeccion/";
        String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";
        String responseString = "";


        try{
            //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
            SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
            Request.addProperty("login", login);
            Request.addProperty("password", password);

            //SE EMPAQUETA EL OBJETO ,SE LE ASIGNA UNA VERSION (V11,V12) Y SE ESCRIBE EL LENGUAJE DONDE FUE CREADO EL WS
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport= new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, soapEnvelope);


            //SE OBTIENE LA RESPUESTA Y SE LE ASIGNA UNA VARIABLE

            resultString = (SoapPrimitive) soapEnvelope.getResponse();


            mensaje= "OK";

        }catch (Exception ex){

            mensaje = "ERROR: " +ex.getMessage();
        }

    }





}











