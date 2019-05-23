package com.example.dai_tp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class BusquedaPorNombre extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_por_nombre);

    }
    public void RecaudarInformacion(View vista)
    {
        EditText editText;
        editText=findViewById(R.id.NombreIngresado);

        String nombre=editText.getText().toString();
        Intent llamar=new Intent(this,MostrarPorCategoria.class);
        Bundle paqueteDeDatos=new Bundle();
        paqueteDeDatos.putString("InformacionElegida",nombre);
        llamar.putExtras(paqueteDeDatos);
        startActivity(llamar);
    }
}
