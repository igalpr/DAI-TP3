package com.example.dai_tp3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MostrarPorRadio extends Activity {

    public ListView listView;
    public ArrayAdapter arrayAdapter;
    public ArrayList<String> ListaACargar;
    String PosicionX;
    String PosicionY;
    String Categoria;
    int Radio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_por_radio);
        Bundle DatosRecibidos=this.getIntent().getExtras();
        PosicionX=DatosRecibidos.getString("positionX");
        PosicionY=DatosRecibidos.getString("posicionY");
        Categoria=DatosRecibidos.getString("categoria");
        Radio=DatosRecibidos.getInt("radio");
        ListaACargar=new ArrayList<>();
        listView=findViewById(R.id.ListaMostrarPorRadio);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ListaACargar);
        tareaAsincronica miTarea=new tareaAsincronica();
        miTarea.execute();
    }
    private class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL miRuta= new URL("http://epok.buenosaires.gob.ar/reverseGeocoderLugares/?x="+PosicionX+"&y="+PosicionY+"&categorias="+Categoria+"&radio="+Radio);
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
            Log.d("procesar","termino");
            if(ListaACargar.size()==0)
            {
                ListaACargar.add("Su búsqueda no arrojó resultados");
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setAdapter(arrayAdapter);
            Log.d("mostrar",""+ListaACargar.size());
        }
    }
    public void procesarJSONLeido(InputStreamReader streamLeido)
    {
        Log.d("procesar","llego");
        JsonReader JSONLeido=new JsonReader(streamLeido);

        try {
            JSONLeido.beginObject();
            Log.d("mostrar","el momento de la verdad");
            while(JSONLeido.hasNext()){
                Log.d("mostrar","entro al while");
                String NombreElemtoActual=JSONLeido.nextName();
                Log.d("mostrar",NombreElemtoActual);
                if(NombreElemtoActual.equals("instancias"))
                {
                    Log.d("mostrar","entro");
                    JSONLeido.beginArray();
                    while(JSONLeido.hasNext()){
                        JSONLeido.beginObject();
                        while(JSONLeido.hasNext()){
                            NombreElemtoActual=JSONLeido.nextName();
                            if(NombreElemtoActual.equals("nombre")){

                                String valorElementoActual=JSONLeido.nextString();
                                Log.d("mostrar",valorElementoActual);
                                ListaACargar.add(valorElementoActual);
                            }
                            else{
                                JSONLeido.skipValue();
                            }
                        }
                        JSONLeido.endObject();
                    }
                    JSONLeido.endArray();
                }
                else{
                    JSONLeido.skipValue();
                }

            }

        }
        catch (Exception e)
        {
            Log.d("error",""+e.getLocalizedMessage());
        }
    }
}

