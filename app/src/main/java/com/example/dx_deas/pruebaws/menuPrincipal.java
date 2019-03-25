package com.example.dx_deas.pruebaws;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class menuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String idUnidad;
    String idFlota ;
    String nombreOperador;
    String idUsuario;
    String idViaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DX Xpress");


        String nombreOperadorO = getIntent().getStringExtra("nombreOperador");
        String idUnidadO = getIntent().getStringExtra("idUnidad");
        String idUsuarioO = getIntent().getStringExtra("idUsuario");
        String idViajeO = getIntent().getStringExtra("idViaje");
        String idFlotaO = getIntent().getStringExtra("idFlota");

        if (idUsuarioO == null) {
            nombreOperador = getIntent().getStringExtra("nombreOperadorS");
            idUnidad = getIntent().getStringExtra("idUnidadS");
            idUsuario = getIntent().getStringExtra("idUsuarioS");
            idViaje = getIntent().getStringExtra("idViajeS");
            idFlota = getIntent().getStringExtra("idFlotaS");
        }
            else {
            nombreOperador = getIntent().getStringExtra("nombreOperador");
            idUnidad = getIntent().getStringExtra("idUnidad");
            idUsuario = getIntent().getStringExtra("idUsuario");
            idViaje = getIntent().getStringExtra("idViaje");
            idFlota = getIntent().getStringExtra("idFlota");
            }


       Intent i = new Intent(menuPrincipal.this, notifi_WS.class);
        i.putExtra("idUnidad", idUnidad);
        startService(i);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedor,new viajeFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_viaje ){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new viajeFragment()).commit();
        } else if (id == R.id.nav_chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new chatFragment()).commit();
        } else if (id == R.id.nav_enviar) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new enviarFragment()).commit();
        } else if (id == R.id.nav_hist) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new histFragment()).commit();
        } else if (id == R.id.nav_mapa) {
            Intent i = new Intent(menuPrincipal.this, MapsActivity.class);
            i.putExtra("idFlota", idFlota);
            i.putExtra("idUnidad", idUnidad);
            startActivity(i);
           // getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,new mapaFragment()).commit();
        } else if (id == R.id.nav_cerrar) {

            SharedPreferences preferences = getSharedPreferences ("credenciales", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user", "");
            editor.putString("pass", "");
            editor.commit();


            Intent i = new Intent(menuPrincipal.this, Login.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
