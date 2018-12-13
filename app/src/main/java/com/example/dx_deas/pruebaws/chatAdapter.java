package com.example.dx_deas.pruebaws;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class chatAdapter extends ArrayAdapter<Chat> {


    public chatAdapter(FragmentActivity fragmentActivity, List<Chat> chat) {
        super(fragmentActivity, 0,chat);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Chat currentMen = getItem(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_sent, parent, false);

            String str1 = currentMen.getUsuario();
            String str2 = "d-";

        if (str1.toLowerCase().contains(str2.toLowerCase())){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_sent, parent, false);
            TextView mensaje = (TextView) convertView.findViewById(R.id.mensajeEnviadoTxt);
            TextView hora = (TextView) convertView.findViewById(R.id.mensajeEnviadoTime);
            mensaje.setText(currentMen.getMensaje());
            hora.setText(currentMen.getFecha());

        }
            else {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message_received, parent, false);
                TextView mensaje2 = (TextView) convertView.findViewById(R.id.mensajeRecibidotxt);
                TextView hora2 = (TextView) convertView.findViewById(R.id.mensajeRecibidoTime);
                mensaje2.setText(currentMen.getMensaje());
                hora2.setText(currentMen.getFecha());
            }
        }


        return convertView;
    }

   @Override
    public int getItemViewType(int position) {
        Chat currentMen = getItem(position);
        String str1 = currentMen.getUsuario();
        String str2 = "d-";
        if (str1.toLowerCase().contains(str2.toLowerCase())){
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
