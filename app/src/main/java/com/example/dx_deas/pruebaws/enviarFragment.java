package com.example.dx_deas.pruebaws;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class enviarFragment extends Fragment {

    String idViaje;
    String tran;
    String responseString = "";
    String direccionTramo;
    SoapPrimitive resultString;
    SoapPrimitive resultString2;
    String mensaje;
    String mensaje2;
    EditText facturatxt, sellotxt, bultostxt, pesotxt, porcentajetxt;
    TextView tramo ;
    Button btnenviar;
    String respuesta;
    String idUsuario,idUnidad ;
    ImageView imagenSelec ;
    String encodedImage;
    Spinner spinner;

    private static final int RESULT_LOAD_IMAGE = 1 ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent get = getActivity().getIntent();
        idUsuario = get.getStringExtra("idUsuario");
        idUnidad = get.getStringExtra("idUnidad");

        WS_ViajeActual viajeActual = new WS_ViajeActual();
        viajeActual.execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imagenSelec.setImageURI(selectedImage);
      }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enviar, container, false);

        String fechatxt  = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date());

        tramo = (TextView) view.findViewById(R.id.tramoActualtxt);
        facturatxt = (EditText) view.findViewById(R.id.facturatxt);
        sellotxt = (EditText) view.findViewById(R.id.sellotxt);
        bultostxt = (EditText) view.findViewById(R.id.bultostxt);
        pesotxt = (EditText) view.findViewById(R.id.pesotxt);
        porcentajetxt = (EditText) view.findViewById(R.id.porcentajetxt);
        btnenviar = (Button) view.findViewById(R.id.btnEnviar);
        spinner = (Spinner) view.findViewById(R.id.spinner1);
        imagenSelec = (ImageView) view.findViewById(R.id.imagen_select);

        String [] values = {"Llegada","Salida",};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);



        imagenSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
        });

     /*  btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable) imagenSelec.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                image.compress(Bitmap.CompressFormat.JPEG,10 ,byteArrayOutputStream);

                 encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

                byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);

                WS_Enviar ws_enviar = new WS_Enviar();
                ws_enviar.execute();

            }
        });*/








      /*  btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                factura = facturatxt.getText().toString();
                sello = sellotxt.getText().toString();
                bultos = bultostxt.getText().toString();
                peso = pesotxt.getText().toString();
                porcentaje = porcentajetxt.getText().toString();



            }
        });*/

        return view;
    }




    private class WS_Enviar extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion(TEST)/Version_20171221_1212";
            String METHOD_NAME = "img64";
            String NAMESPACE = "http://dxxpress.net/wsInspeccion(TEST)/";
            String URL = "http://dxxpress.net/wsInspeccion(TEST)/interfaceOperadores3.asmx";


            try {
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                Request.addProperty("imageBase64", encodedImage);
               /* Request.addProperty("porcLlenado", porcentaje);
                Request.addProperty("sello", sello);
                Request.addProperty("peso", peso);
                Request.addProperty("bultos", bultos);
                Request.addProperty("idDetalleViaje", idDetalleViaje);*/


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

    }

    private class WS_ViajeActual extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "ViajeActual";
            String NAMESPACE  = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";



            try{
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
                Request.addProperty("idUnidad", idUnidad);
                Request.addProperty("idUsuario", idUsuario);

                //SE EMPAQUETA EL OBJETO ,SE LE ASIGNA UNA VERSION (V11,V12) Y SE ESCRIBE EL LENGUAJE DONDE FUE CREADO EL WS
                SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
                soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);

                HttpTransportSE transport= new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, soapEnvelope);


                //SE OBTIENE LA RESPUESTA Y SE LE ASIGNA UNA VARIABLE

                resultString2 = (SoapPrimitive) soapEnvelope.getResponse();


                mensaje2= "OK";

            }catch (Exception ex){

                mensaje2 = "ERROR: " +ex.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {

                //Respuesta de la llamada , es un json
                tran = resultString2.toString();


                //Si el usuario no existe el servicio regresara el string {"Usuario":[{}] }
                if (tran.length() <= 15) {
                    Toast.makeText(getActivity(), "Viaje No Asignado", Toast.LENGTH_SHORT).show();
                } else {

                    //Libreria gson se utliza para traducir de json a string y viceversa
                    Gson gson = new Gson();

                    //Retira la palabra usuario y los corchetes para el uso de la libreria gson
                    String reusu = tran.replace("{\"Viaje\":[{", "{");
                    String reusu2 = reusu.replace("}] }", "}");

                    //Se usa la libreria para traducir el json , el string obtenido se almacena en la libreria Usuario
                    ViajeActual viaje = gson.fromJson(reusu2, ViajeActual.class);

                    idViaje = viaje.getIdViaje();
                    String folioViaje = viaje.getFolioViaje();
                    String tracking = viaje.getTracking();
                    String idCliente = viaje.getIdCliente();
                    String idRuta = viaje.getIdRuta();
                    String nombreRuta = viaje.getNombreRuta();
                    String secuencia = viaje.getSecuencia();
                    String idDetalleViaje = viaje.getIdDetalleViaje();
                    String idDetalleViajeDestino = viaje.getIdDetalleViajeDestino();
                    String idOperador = viaje.getIdOperador();
                    String idOperador2 = viaje.getIdOperador2();
                    String idRemolquePrincipal = viaje.getIdRemolquePrincipal();
                    String nombreTramo = viaje.getNombreTramo();
                    direccionTramo = viaje.getDireccionTramo();
                    String descripcionCarga = viaje.getDescripcionCarga();
                    String descripcionTipoMovimiento = viaje.getDescripcionTipoMovimiento();
                    String nombreEstatus = viaje.getNombreEstatus();
                    String nombreUnidad = viaje.getNombreUnidad();
                    String nombreOperador = viaje.getNombreOperador();
                    String nombreOperador2 = viaje.getNombreOperador2();
                    String nombreRemolquePrincipal = viaje.getNombreRemolquePrincipal();
                    String nombreDestino = viaje.getNombreDestino();
                    String latitudDestino = viaje.getLatitudDestino();
                    String longitudDestino = viaje.getLongitudDestino();

                    tramo.setText(nombreTramo);


                }

            }catch (Exception ex){
                mensaje2 = "ERROR: " +ex.getMessage();
            }


            super.onPostExecute(aVoid);
        }
    }


}