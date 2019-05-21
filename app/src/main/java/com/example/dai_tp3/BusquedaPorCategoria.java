package com.example.dai_tp3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

public class BusquedaPorCategoria extends Activity {

    public ListView MiListaCatgorias;
    public ArrayAdapter arrayAdapter;
    public ArrayList<String> ListaCategorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_por_categoria);
        ListaCategorias=new ArrayList<>();
        MiListaCatgorias=findViewById(R.id.ListaBuscarPorCategorias);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ListaCategorias);
        tareaAsincronica miTarea=new tareaAsincronica();
        miTarea.execute();
        MiListaCatgorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Cat=ListaCategorias.get(position);
                IrAPantalla(Cat);

            }
        });
        {

        }
    }
    private void IrAPantalla(String categoria)
    {
        Intent llamar=new Intent(this,MostrarPorCategoria.class);
        Bundle paqueteDeDatos=new Bundle();
        Log.d("valor",categoria);
        paqueteDeDatos.putString("InformacionElegida",categoria);
        llamar.putExtras(paqueteDeDatos);
        startActivity(llamar);
    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL miRuta= new URL("http://epok.buenosaires.gob.ar/getCategorias");
                HttpURLConnection MiConexion=(HttpURLConnection) miRuta.openConnection();

                if(MiConexion.getResponseCode()==200)
                {
                    Log.d("Conexion", "Exitosa");
                    InputStream cuerpoRespuesta=MiConexion.getInputStream();
                    InputStreamReader lectorRespuesta= new InputStreamReader(cuerpoRespuesta, "UTf-8");
                    procesarJSONLeido(lectorRespuesta);
                }
                else
                {
                    Log.d("Conexion", "Error en la conexion");
                }
                MiConexion.disconnect();

            } catch (Exception e) {
                Log.d("TryCatch1", "Error en el primer try catch   " +e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);

            MiListaCatgorias.setAdapter(arrayAdapter);
        }
    }
    public void procesarJSONLeido(InputStreamReader streamLeido)
    {
        JsonReader JSONLeido=new JsonReader(streamLeido);
        try {
            JSONLeido.beginObject();
            while(JSONLeido.hasNext()){
                String NombreElemtoActual=JSONLeido.nextName();

                if(NombreElemtoActual.equals("cantidad_de_categorias"))
                {
                    int cantidadCategorias=JSONLeido.nextInt();
                }
                else
                {
                    JSONLeido.beginArray();
                    while(JSONLeido.hasNext()){
                        JSONLeido.beginObject();
                        while(JSONLeido.hasNext()){
                            NombreElemtoActual=JSONLeido.nextName();
                            if(NombreElemtoActual.equals("nombre")){
                                String valorElementoActual=JSONLeido.nextString();
                                ListaCategorias.add(valorElementoActual);
                            }
                            else{
                                JSONLeido.skipValue();
                            }
                        }
                        JSONLeido.endObject();
                    }
                    JSONLeido.endArray();
                }
            }

        }
        catch (Exception e)
        {

        }
    }

}
