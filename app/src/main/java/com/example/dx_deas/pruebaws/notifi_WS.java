package com.example.dx_deas.pruebaws;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static com.example.dx_deas.pruebaws.notifiMen.CHANNEL_ID;

public class notifi_WS extends Service {
    SoapPrimitive resultString ;
    String respuesta ;
    private final static  String CHANNEL_ID = "NOTIFICATION" ;
    private final static  int NOTIFICATION_ID = 100;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


   private class llamada extends AsyncTask<Void,Void ,Void>{

       @Override
       protected Void doInBackground(Void... voids) {
           String SOAP_ACTION = "http://dxxpress.net/wsInspeccion/Version_20171221_1212";
           String METHOD_NAME = "ChatSelect";
           String NAMESPACE = "http://dxxpress.net/wsInspeccion/";
           String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";


           try {
               //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
               SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
              // Request.addProperty("idViaje", idViaje);

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

       @SuppressLint("NewApi")
       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);

            String tran ;


            tran = resultString.toString();


           if (tran=="mensaje nuevo"){


               NotificationCompat.Builder notificactionBuilder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
                       notificactionBuilder.setContentTitle("Mensaje Nuevo");
                       notificactionBuilder.setContentText("Favor de abrir la aplicacion");
                       notificactionBuilder.setVibrate(new long[]{1000,1000});
                       notificactionBuilder.setDefaults(Notification.DEFAULT_SOUND);
                       notificactionBuilder.setSmallIcon(R.drawable.logodx2);

               NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(getApplicationContext());
               notificationManagerCompat.notify(NOTIFICATION_ID,notificactionBuilder.build());
           }

       }
   }


}
