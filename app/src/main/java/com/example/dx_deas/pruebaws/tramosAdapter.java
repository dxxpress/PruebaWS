package com.example.dx_deas.pruebaws;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class tramosAdapter extends ArrayAdapter<Tramos> {

    String idDetViaje;



    public tramosAdapter(FragmentActivity fragmentActivity, List<Tramos> tramos, String idDetalleViaje) {
        super(fragmentActivity,0,tramos);


        idDetViaje = idDetalleViaje;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tramo, parent, false);
        }


        final Tramos currentTramo = getItem(position);
        TextView hora = (TextView) convertView.findViewById(R.id.hora);
        TextView colo = (TextView) convertView.findViewById(R.id.colo);
        TextView nombre = (TextView) convertView.findViewById(R.id.nombreTramo);


        hora.setText(currentTramo.getHoraCompromiso());
        nombre.setText(currentTramo.getNombre());
        colo.setText("");

        Button btncerrar = (Button) convertView.findViewById(R.id.btnCerrarTramo);


        btncerrar.setVisibility(View.INVISIBLE);
        int secu = Integer.parseInt(currentTramo.getSecuencia());
        int vali = Integer.parseInt(currentTramo.getEstatus());
        String fechaEntrada = currentTramo.getFechaEntrada();

        if (vali == 2 ){
            colo.setBackgroundColor(Color.parseColor("#074EAB"));
            colo.setText("Terminado");
            btncerrar.setVisibility(View.INVISIBLE);
        }
        if (vali == 1 ){
            colo.setBackgroundColor(Color.parseColor("#298A08"));
            colo.setText("Proximo");
             btncerrar.setVisibility(View.VISIBLE);
        }
        if (vali == 3 ){
            colo.setBackgroundColor(Color.parseColor("#FE9A2E"));
            colo.setText("Cancelado");
            btncerrar.setVisibility(View.INVISIBLE);
        }
        if (secu == 1 ){
            colo.setBackgroundColor(Color.parseColor("#074EAB"));
            colo.setText("Terminado");
            btncerrar.setVisibility(View.INVISIBLE);
        }
        if (vali == 1 && (fechaEntrada.length() >= 5)){
            colo.setBackgroundColor(Color.parseColor("#FFFF00"));
            colo.setText("Ultima\n"+"Salida");
            btncerrar.setVisibility(View.INVISIBLE);
        }

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = currentTramo.getNombre();

                Intent intent = new Intent(v.getContext(),enviarActivity   .class);
                v.getContext().startActivity(intent);


            }
        });


        return convertView;
    }


}
