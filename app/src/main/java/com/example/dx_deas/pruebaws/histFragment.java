package com.example.dx_deas.pruebaws;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class histFragment extends Fragment {

    String id;
    String idViaje;
    String idUnidad;
    String responseString = "";
    EditText etcht;
    Button btnenv;
    String mensaje;
    String idUsuario;
    SoapPrimitive resultString;
    String respuesta;
    String JsonMen;
    SoapObject resultString2;
    String respuesta2;
    String JsonMen2;
    ListView lista2 ;
    SoapPrimitive resultString3;
    String respuesta3;
    String mensaje3;
    String tran3 ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent get = getActivity().getIntent();
        idUsuario = get.getStringExtra("idUsuario");
        idUnidad = get.getStringExtra("idUnidad");




    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historial, container, false);


        lista2 = (ListView) view.findViewById(R.id.listHistorial);

        CargaMensajesWS cargaMensajesWS = new CargaMensajesWS();
        cargaMensajesWS.execute();



        return view;
    }


    private class CargaMensajesWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "ViajeHistorial";
            String NAMESPACE = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";



            try {
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("idUsuario", idUsuario);
                Request.addProperty("idUnidad", idUnidad);
                Request.addProperty("fechaIni", "2019-01-01T01:00:00.000");
                Request.addProperty("fechaFin", "2019-02-13T11:50:00.000");
                Request.addProperty("pagado", "");


                //SE EMPAQUETA EL OBJETO ,SE LE ASIGNA UNA VERSION (V11,V12) Y SE ESCRIBE EL LENGUAJE DONDE FUE CREADO EL WS
                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, soapEnvelope);


                //SE OBTIENE LA RESPUESTA Y SE LE ASIGNA UNA VARIABLE

                resultString = (SoapPrimitive) soapEnvelope.getResponse();


                respuesta = "OK";

            } catch (Exception ex) {

                respuesta = "ERROR: " + ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JsonMen = resultString.toString();
            try {


                Gson gson = new Gson();
                String reusu = JsonMen.replace("{\"Viaje\":[{", "[{");
                String reusu2 = reusu.replace("}] }", "}]");

                Type histListType = new TypeToken<ArrayList<Historial>>() {}.getType();
                List<Historial> historial = new Gson().fromJson(reusu2, histListType);

                ArrayAdapter adapter2 = new histAdapter(getActivity(), historial);

                lista2.setAdapter(adapter2);


            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error 404C", Toast.LENGTH_SHORT).show();
                System.out.println("ERROR : " + e);
            }
        }

    }



}







