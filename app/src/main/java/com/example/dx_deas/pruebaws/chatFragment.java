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
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;


public class chatFragment extends Fragment {

    String id;
    String idViaje;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent get = getActivity().getIntent();
        idViaje = get.getStringExtra("idViaje");
        idUsuario = get.getStringExtra("idUsuario");

        CargaMensajesWS carga = new CargaMensajesWS();
        carga.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        lista2 = (ListView) view.findViewById(R.id.listachat);
        etcht = (EditText) view.findViewById(R.id.edittext_chatbox);
        btnenv = (Button) view.findViewById(R.id.button_chatbox_send);

        btnenv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mensaje = etcht.getText().toString();
                EnviarMensajesWS envia = new EnviarMensajesWS();
                envia.execute();

               CargaMensajesWS carga = new CargaMensajesWS();
                carga.execute();
            }
        });

        return view;
    }


    private class CargaMensajesWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "ChatSelect";
            String NAMESPACE = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";


            try {
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("idViaje", idViaje);

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
                String reusu = JsonMen.replace("{\"Chat\":[{", "[{");
                String reusu2 = reusu.replace("}] }", "}]");

                Type chatListType = new TypeToken<ArrayList<Chat>>(){}.getType();
                List<Chat> chats = new Gson().fromJson(reusu2,chatListType);

                ArrayAdapter adapter2 = new chatAdapter(getActivity(),chats);

                lista2.setAdapter(adapter2);

                String Veigar = "La R con 1000 de Ap :V";

            } catch (Exception e) {
                System.out.println("ERROR : " + e);
            }
        }

    }

    private class EnviarMensajesWS extends AsyncTask <Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "ChatSave";
            String NAMESPACE = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";

            try {
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("idViaje", idViaje);
                Request.addProperty("mensaje", mensaje);
                Request.addProperty("idUsuario", idUsuario);

                //SE EMPAQUETA EL OBJETO ,SE LE ASIGNA UNA VERSION (V11,V12) Y SE ESCRIBE EL LENGUAJE DONDE FUE CREADO EL WS
                SoapSerializationEnvelope soapEnvelope2 = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                soapEnvelope2.dotNet = true;
                soapEnvelope2.setOutputSoapObject(Request);

                HttpTransportSE transport2 = new HttpTransportSE(URL);
                transport2.call(SOAP_ACTION, soapEnvelope2);


                //SE OBTIENE LA RESPUESTA Y SE LE ASIGNA UNA VARIABLE

                resultString2 = (SoapObject) soapEnvelope2.getResponse();


                respuesta2 = "OK";

            } catch (Exception ex) {

                respuesta2 = "ERROR: " + ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            etcht.setText("");
            Toast.makeText(getActivity(), "Mensaje Enviado", Toast.LENGTH_SHORT).show();
        }
    }



}







