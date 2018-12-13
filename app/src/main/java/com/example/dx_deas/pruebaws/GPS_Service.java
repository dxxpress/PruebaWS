package com.example.dx_deas.pruebaws;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Date;
import java.util.concurrent.Executor;

public class GPS_Service extends Service {

    public static final int SERVICE_ID = 123;

    PowerManager.WakeLock wakeLock;
    private LocationManager locationManager;
    Double lat;
    Double lon;
    String latitud;
    String longitud;
    String mensaje = "";
    //LocationManager locationManager;
    //LocationListener listener;
    String idUnidad;
    String idOperador;
    String fecha;
    Date newfecha;
    String SOAP_ACTION;
    String METHOD_NAME ;
    String NAMESPACE ;
    String URL ;
    FusedLocationProviderClient mFusedLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
       // PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        //wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");

    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        idUnidad = (String) intent.getExtras().get("idUnidad");
        idOperador=(String) intent.getExtras().get("idOperador");

        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                5000, 0, listener);


        return START_NOT_STICKY;
    }

    private LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            latitud = lat.toString();
            longitud = lon.toString();

            GPS_WS llamada = new GPS_WS(latitud,longitud,idUnidad,idOperador);
            llamada.execute();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    @Override
    public void onDestroy() {
        locationManager.removeUpdates(listener);
        setAlarm(this);
        //wakeLock.release();
        System.out.println("EL SERVICIO TERMINO");
        super.onDestroy();
    }


    @SuppressLint("NewApi")
    public void setAlarm(Context context) {
        try {
            AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

            long interval = 1000 * 60 * 3;
            Intent intent = new Intent(context, GPS_Service.class);
            intent.putExtra("idUnidad", idUnidad);
            intent.putExtra("idOperador", idOperador);

            PendingIntent pendingIntent = PendingIntent.getService(context, 123, intent, PendingIntent.FLAG_ONE_SHOT);
            alarm.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP , System.currentTimeMillis() + interval, pendingIntent);

            return ;

        }catch (Exception e){
            System.out.println("Excepcion en Alarma Inicia" + e.getMessage() );
        }

    }

}


