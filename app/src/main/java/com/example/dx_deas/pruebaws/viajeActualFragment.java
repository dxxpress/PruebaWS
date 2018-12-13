package com.example.dx_deas.pruebaws;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class viajeActualFragment extends Fragment {

    private TextView dest, folio,load,ruta,caja,unidad,direTramo,welcome;
    String tran ;
    String responseString = "";
    String direccionTramo;
    SoapPrimitive resultString;
    String mensaje;
    String mensaje2;
    String latitudDestino ;
    String longitudDestino ;
    //String folioViaje ;
    private String idUnidad;
    private String idUsuario;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        idUsuario = getArguments().getString("idUsuario");
        idUnidad = getArguments().getString("idUnidad");

        SegundoPlano llamadaWS = new SegundoPlano();
        llamadaWS.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_viajeactual, container, false);


        dest = (TextView) view.findViewById(R.id.Destinotxt);
        folio = (TextView) view.findViewById(R.id.Foliotxt);
        load = (TextView) view.findViewById(R.id.Loadtxt);
        ruta = (TextView) view.findViewById(R.id.Rutatxt);
        caja = (TextView) view.findViewById(R.id.Cajatxt);
        unidad = (TextView) view.findViewById(R.id.Unidadtxt);
        direTramo = (TextView) view.findViewById(R.id.direTramo);



        direTramo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        //google.navigation:q=
                       Uri.parse("google.navigation:q="+latitudDestino+" "+longitudDestino));
                startActivity(intent);
            }
        });

        return view;
    }



    private class SegundoPlano extends AsyncTask<Void, Void, Void> {

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

                    String idViaje = viaje.getIdViaje();
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
                    latitudDestino = viaje.getLatitudDestino();
                    longitudDestino = viaje.getLongitudDestino();


                    tramosFragment tramos = new tramosFragment ();
                    Bundle args = new Bundle();
                    args.putString("idViaje", idViaje);
                    tramos.setArguments(args);
                    getFragmentManager().beginTransaction().add(R.id.tramosContenedor, tramos).commit();


                    direTramo.setText(direccionTramo);
                    dest.setText(nombreDestino);
                    folio.setText(folioViaje );
                    load.setText(tracking);
                    ruta.setText(nombreRuta);
                    caja.setText(nombreRemolquePrincipal);
                    unidad.setText(nombreUnidad);

                }

            }catch (Exception ex){
                mensaje2 = "ERROR: " +ex.getMessage();
            }


            super.onPostExecute(aVoid);
        }
    }


}