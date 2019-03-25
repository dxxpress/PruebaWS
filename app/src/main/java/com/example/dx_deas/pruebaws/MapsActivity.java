package com.example.dx_deas.pruebaws;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity  implements OnMapReadyCallback {

    private GoogleMap mMap;

    String idUnidad;
    String idFlota;
    SoapPrimitive resultString2 ;
    String mensaje2;
    int radio = 30000;
    String tran  ;
    FusedLocationProviderClient mFusedLocationClient;
    double lon;
    double lat;
    String reusu;
    String reusu2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        idUnidad = getIntent().getStringExtra("idUnidad");
        idFlota = getIntent().getStringExtra("idFlota");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;





         @SuppressLint("MissingPermission") Task location = mFusedLocationClient.getLastLocation();
        location.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Location currentLocation = (Location) task.getResult();

                    lon = currentLocation.getLongitude();
                    lat = currentLocation.getLatitude();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
                    mMap.setTrafficEnabled(true);
                   // mMap.setMyLocationEnabled(true);
                    // latitude and longitude
                   // double latitude = 25.5991301;
                    //double longitude = -100.2641234;

                    // create marker
                    //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");

                    // Changing marker icon
                    //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_icon));

                    // adding marker
                    //mMap.addMarker(marker);
                    WS_CargaPosiciones ws_cargaPosiciones = new WS_CargaPosiciones();
                    ws_cargaPosiciones.execute();
                    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

                }else {
                    Toast.makeText(MapsActivity.this,"Error Al Cargar Mapa",Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(lat, lon);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }






    private class WS_CargaPosiciones extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
            String METHOD_NAME = "UnidadesCercanas";
            String NAMESPACE  = "http://dxxpress.net/wsInspeccion/";
            String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";



            try{
                //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
                SoapObject Request = new SoapObject(NAMESPACE,METHOD_NAME);
                Request.addProperty("idUnidad", idUnidad);
                Request.addProperty("idFlota", idFlota);
                Request.addProperty("radio", radio);

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


                    Gson gson = new GsonBuilder().serializeNulls().create();

                    reusu = tran.replace("{\"UnidadesCercanas\":[{", "[{");
                     reusu2 = reusu.replace("}] }", "}]");

                    //Se usa la libreria para traducir el json , el string obtenido se almacena en la libreria Usuario

                    // Tramos[] Tramos1 = gson.fromJson(reusu2, Tramos[].class);

                    Type marker = new TypeToken<ArrayList<UnidadesCercanas>>(){}.getType();
                     List<UnidadesCercanas> unidadesCercanas = new Gson().fromJson(reusu2,marker);


                for(int i =0;i<unidadesCercanas.size();i++) {
                    LatLng name = new LatLng(unidadesCercanas.get(i).getLatitud(), unidadesCercanas.get(i).getLongitud());


                    String unidad = unidadesCercanas.get(i).getUnidad();
                    String operador  = unidadesCercanas.get(i).getOperador();
                    String telefono = unidadesCercanas.get(i).getTelefono();
                    double ws_lat = unidadesCercanas.get(i).getLatitud();
                    double ws_lon = unidadesCercanas.get(i).getLongitud();


                   mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

                    mMap.addMarker(new MarkerOptions().position(name)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck_icon))
                            .title(unidad)
                            .snippet("Operador: " + operador+ "\n" + "Telefono: "+telefono))
                            .showInfoWindow();


                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            Type marker2 = new TypeToken<ArrayList<UnidadesCercanas>>(){}.getType();
                            List<UnidadesCercanas> unidadesCercanas2 = new Gson().fromJson(reusu2,marker2);



                            String uni = marker.getTitle();

                            //Cycle through places array
                            for(int i =0;i<unidadesCercanas2.size();i++){

                                String unidadNucle = (unidadesCercanas2.get(i).getUnidad());

                                if (uni.equals(unidadNucle)){

                                    //match found!  Do something....
                                    String match = unidadesCercanas2.get(i).getTelefono();
                                    if (match.length() <= 0 ){

                                        Toast.makeText(MapsActivity.this,"Sin telefono",Toast.LENGTH_SHORT).show();
                                    }else {
                                        //Toast.makeText(MapsActivity.this,"Telefono:" + match ,Toast.LENGTH_SHORT).show();
                                        Intent callIntent =new Intent(Intent.ACTION_DIAL);
                                        callIntent.setData(Uri.parse("tel:"+match));
                                        startActivity(callIntent);
                                    }





                                }

                            }

                        }
                    });



                }

                    String LeeSin = "La insec papu ";

            }catch (Exception ex){
                mensaje2 = "ERROR: " +ex.getMessage();
            }


            super.onPostExecute(aVoid);
        }
    }


}
