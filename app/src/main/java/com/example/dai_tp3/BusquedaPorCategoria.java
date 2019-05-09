package com.example.dai_tp3;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class BusquedaPorCategoria extends Activity {

    public ListView listaCatgorias;
    public ArrayAdapter arrayAdapter;
    public ArrayList<String> ListaCategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_por_categoria);
        ListaCategorias=new ArrayList<>();
        listaCatgorias=findViewById(R.id.ListaBuscarPorCategorias);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ListaCategorias);
        tareaAsincronica miTarea=new tareaAsincronica();
        miTarea.execute();
    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            Uri miRuta=new URL("http://epok.buenosaires.gob.ar/getCategorias");
            return null;
        }
        @Override
        protected void OnPostExecute(Void avoids)
        {
            super.onPostExecute(avoids);
        }
    }
}
