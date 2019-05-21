package com.example.dai_tp3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class BusquedaPorNombre extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_por_nombre);
        editText.findViewById(R.id.NombreIngresado);
    }
    public void RecaudarInformacion()
    {
        String nombre=editText.getText().toString();
        Intent llamar=new Intent(this,MostrarPorCategoria.class);
        Bundle paqueteDeDatos=new Bundle();
        paqueteDeDatos.putString("InformacionElegida",nombre);
        llamar.putExtras(paqueteDeDatos);
        startActivity(llamar);
    }
}
