package com.example.dx_deas.pruebaws;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class histAdapter extends ArrayAdapter<Historial> {


    public histAdapter(FragmentActivity fragmentActivity, List<Historial> chat) {
        super(fragmentActivity, 0, chat);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Historial currentMen = getItem(position);

        final ViewHolder mholder;



        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.histo, parent, false);
            mholder = new ViewHolder();

            mholder.destino_txt = (TextView) convertView.findViewById(R.id.destino_txt);
            mholder.folio_txt = (TextView) convertView.findViewById(R.id.folio_txt);
            mholder.llegada_txt = (TextView) convertView.findViewById(R.id.llegada_txt);

            mholder.destino_txt.setText(currentMen.getNombreDestino());
            mholder.folio_txt.setText(currentMen.getFolioViaje());
            mholder.llegada_txt.setText(currentMen.getFechaEntradaDestino());

        }else {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.histo, parent, false);
            mholder = new ViewHolder();

            mholder.destino_txt = (TextView) convertView.findViewById(R.id.destino_txt);
            mholder.folio_txt = (TextView) convertView.findViewById(R.id.folio_txt);
            mholder.llegada_txt = (TextView) convertView.findViewById(R.id.llegada_txt);

            mholder.destino_txt.setText(currentMen.getNombreDestino());
            mholder.folio_txt.setText(currentMen.getFolioViaje());
            mholder.llegada_txt.setText(currentMen.getFechaEntradaDestino());

        }






        return convertView;
    }
    static class ViewHolder {


        TextView llegada_txt;
        TextView destino_txt;
        TextView folio_txt;
    }

}