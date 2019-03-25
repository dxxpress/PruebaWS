package com.example.dx_deas.pruebaws;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
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

import com.google.gson.Gson;

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

    private final static  String CHANNEL_ID_O = "NOTIFICATION_OREO" ;
    private final static  int NOTIFICATION_ID_O = 200;

    String idUnidad;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        idUnidad = (String) intent.getExtras().get("idUnidad");

        llamada llam =  new llamada();
        llam.execute();

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
           String METHOD_NAME = "SalaChatSelect";
           String NAMESPACE = "http://dxxpress.net/wsInspeccion/";
           String URL = "http://dxxpress.net/wsInspeccion/interfaceOperadores3.asmx";


           try {
               //SE CREA UN OBJETO SOAP Y SE LE AGREGAN LOS PARAMETROS DE ENTRADA
               SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
               Request.addProperty("idUnidad", idUnidad);

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

           Gson gson = new Gson();

           //Retira la palabra usuario y los corchetes para el uso de la libreria gson
           String reusu = tran.replace("{\"SalaChat\":[{", "{");
           String reusu2 = reusu.replace("}] }", "}");

           //Se usa la libreria para traducir el json , el string obtenido se almacena en la libreria Usuario
           SalaChat sala = gson.fromJson(reusu2, SalaChat.class);

           int idSalaChat = sala.getIdSalaChat();


          // System.out.println("RESPUESTA: " + idSalaChat );

           if (idSalaChat == 0){

            //   System.out.println("NO EXISTE MENSAJE NUEVO " + idSalaChat );

           }else {

               System.out.println("MENSAJE NUEVO !!!!!!!!!!!!" + idSalaChat);


               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                   System.out.println("NOTIFI EN OREO");
                   NotificationManager mNotificationManager =
                           (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);


                   NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID_O,
                           "Channel human readable title",
                           NotificationManager.IMPORTANCE_DEFAULT);
                   mNotificationManager.createNotificationChannel(mChannel);

                   mChannel.setDescription("AAAAAAAAAAAAAAA");
                   mChannel.setVibrationPattern(new long[]{1000,1000});

                   NotificationCompat.Builder mBuilder ;

                   mBuilder= new NotificationCompat.Builder(getApplicationContext() ,CHANNEL_ID_O);

                   mBuilder.setSmallIcon(R.drawable.logodx2);
                   mBuilder.setContentTitle("Mensaje Nuevo");
                   mBuilder.setContentText("Favor de abrir la aplicacion");
                   mBuilder.setVibrate(new long[]{1000, 1000});
                   mBuilder.setDefaults(Notification.DEFAULT_SOUND);

                   mNotificationManager.notify(NOTIFICATION_ID_O,mBuilder.build());



               } else{

                   System.out.println("NOTIFI EN OTRO");
                   NotificationCompat.Builder notificactionBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
               notificactionBuilder.setContentTitle("Mensaje Nuevo");
               notificactionBuilder.setContentText("Favor de abrir la aplicacion");
               notificactionBuilder.setVibrate(new long[]{1000, 1000});
               notificactionBuilder.setDefaults(Notification.DEFAULT_SOUND);
               notificactionBuilder.setSmallIcon(R.drawable.logodx2);

               NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
               notificationManagerCompat.notify(NOTIFICATION_ID, notificactionBuilder.build());

           }

           }



           Intent intent = new Intent(notifi_WS.this, notifi_WS.class);
           intent.putExtra("idUnidad", idUnidad);
           startService(intent);
           //startForegroundService(intent);

       }
   }


}
