package com.example.dai_tp3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActividadPrincipal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
    }
    public void ProximaVista(View vista)
    {
        int id=vista.getId();
        if(id==R.id.BotonNombre)
        {
            Intent IrABuscarPorNombre=new Intent(this,BusquedaPorNombre.class);
            startActivity(IrABuscarPorNombre);
        }
        if(id==R.id.BotonCategoria)
        {
            Intent IrABuscarPorCategoria=new Intent(this,BusquedaPorCategoria.class);
            startActivity(IrABuscarPorCategoria);
        }
        if(id==R.id.BotonRadio)
        {
            Intent IrABuscarPorRadio=new Intent(this,BusquedaPorRadio.class);
            startActivity(IrABuscarPorRadio);
        }
    }
}
