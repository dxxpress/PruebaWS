package com.example.dx_deas.pruebaws;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class viajeFragment extends Fragment {


    private Button btnTramos,btnViaje ;
    private String nombreOperador;
    private String idUnidad;
    private String idUsuario;
    private View v1,v2;
    private TextView desttxt, foliotxt,loadtxt,rutatxt,cajatxt,unidadtxt,direTramotxt,ventanatxt,estimadotxt;
    private TextView dest, folio,load,ruta,caja,unidad,ventana,estimado,welcome;
    String tran ;
    String responseString = "";
    String direccionTramo;
    SoapPrimitive resultString;
    String mensaje;
    String mensaje2;
    String latitudDestino ;
    String longitudDestino ;
    String idViaje;
    final Fragment tramos = new tramosFragment();
    final Fragment viajeActual = new viajeActualFragment();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent get = getActivity().getIntent();
        nombreOperador = get.getStringExtra("nombreOperador");
        idUsuario = get.getStringExtra("idUsuario");
        idUnidad = get.getStringExtra("idUnidad");

        SegundoPlano llamadaWS = new SegundoPlano();
        llamadaWS.execute();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_viaje, container, false);

        welcome = (TextView) view.findViewById(R.id.welcome);
        desttxt = (TextView) view.findViewById(R.id.Destinotxt);
        foliotxt = (TextView) view.findViewById(R.id.Foliotxt);
        loadtxt = (TextView) view.findViewById(R.id.Loadtxt);
        rutatxt = (TextView) view.findViewById(R.id.Rutatxt);
        cajatxt = (TextView) view.findViewById(R.id.Cajatxt);
        unidadtxt = (TextView) view.findViewById(R.id.Unidadtxt);
        direTramotxt = (TextView) view.findViewById(R.id.direTramo);
        ventanatxt = (TextView) view.findViewById(R.id.ventanatxt);
        estimadotxt = (TextView) view.findViewById(R.id.estimadotxt);

        v1= (View) view.findViewById(R.id.vista1);
        v2= (View) view.findViewById(R.id.vista2);

        dest = (TextView) view.findViewById(R.id.Destino);
        folio = (TextView) view.findViewById(R.id.Folio);
        load = (TextView) view.findViewById(R.id.Load);
        ruta = (TextView) view.findViewById(R.id.Ruta);
        caja = (TextView) view.findViewById(R.id.Caja);
        unidad = (TextView) view.findViewById(R.id.Unidad);
        ventana = (TextView) view.findViewById(R.id.ventana);
        estimado = (TextView) view.findViewById(R.id.estimado);

        btnViaje = (Button) view.findViewById(R.id.btnViaje);
        btnTramos = (Button) view.findViewById(R.id.btnTramos);


        direTramotxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        //google.navigation:q=
                        Uri.parse("google.navigation:q="+latitudDestino+" "+longitudDestino));
                startActivity(intent);
            }
        });





        btnViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.hide(tramos);
                transaction2.commit();
                desttxt.setVisibility(View.VISIBLE);
                foliotxt.setVisibility(View.VISIBLE);
                loadtxt.setVisibility(View.VISIBLE);
                rutatxt.setVisibility(View.VISIBLE);
                cajatxt.setVisibility(View.VISIBLE);
                unidadtxt.setVisibility(View.VISIBLE);
                direTramotxt.setVisibility(View.VISIBLE);
                ventanatxt.setVisibility(View.VISIBLE);
                estimadotxt.setVisibility(View.VISIBLE);

                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.VISIBLE);

                dest.setVisibility(View.VISIBLE);
                folio.setVisibility(View.VISIBLE);
                load.setVisibility(View.VISIBLE);
                ruta.setVisibility(View.VISIBLE);
                caja.setVisibility(View.VISIBLE);
                unidad.setVisibility(View.VISIBLE);
                ventana.setVisibility(View.VISIBLE);
                estimado.setVisibility(View.VISIBLE);

            }
        });

        btnTramos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.tramosContenedor, tramos);
                transaction.show(tramos);
                transaction.commit();
                desttxt.setVisibility(View.INVISIBLE);
                foliotxt.setVisibility(View.INVISIBLE);
                loadtxt.setVisibility(View.INVISIBLE);
                rutatxt.setVisibility(View.INVISIBLE);
                cajatxt.setVisibility(View.INVISIBLE);
                unidadtxt.setVisibility(View.INVISIBLE);
                direTramotxt.setVisibility(View.INVISIBLE);
                ventanatxt.setVisibility(View.INVISIBLE);
                estimadotxt.setVisibility(View.INVISIBLE);

                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.INVISIBLE);

                dest.setVisibility(View.INVISIBLE);
                folio.setVisibility(View.INVISIBLE);
                load.setVisibility(View.INVISIBLE);
                ruta.setVisibility(View.INVISIBLE);
                caja.setVisibility(View.INVISIBLE);
                unidad.setVisibility(View.INVISIBLE);
                ventana.setVisibility(View.INVISIBLE);
                estimado.setVisibility(View.INVISIBLE);

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
                    latitudDestino = viaje.getLatitudDestino();
                    longitudDestino = viaje.getLongitudDestino();

                    Bundle args = new Bundle();
                    args.putString("idViaje", idViaje);
                    args.putString("idDetalleViaje", idDetalleViaje);


                    tramos.setArguments(args);
                    getFragmentManager().beginTransaction().add(R.id.tramosContenedor, tramos).commit();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    transaction2.hide(tramos);
                    transaction2.commit();



                    direTramotxt.setText(direccionTramo);
                    desttxt.setText(nombreDestino);
                    foliotxt.setText(folioViaje );
                    loadtxt.setText(tracking);
                    rutatxt.setText(nombreRuta);
                    cajatxt.setText(nombreRemolquePrincipal);
                    unidadtxt.setText(nombreUnidad);
                    welcome.setText("Bienvenido \n" + nombreOperador);

                }

            }catch (Exception ex){
                mensaje2 = "ERROR: " +ex.getMessage();
            }


            super.onPostExecute(aVoid);
        }
    }




}