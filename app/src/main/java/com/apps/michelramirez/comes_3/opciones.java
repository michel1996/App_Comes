package com.apps.michelramirez.comes_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class opciones extends AppCompatActivity implements View.OnClickListener{

    String idusuario="";

    Button btnPerfil;
    Button btnVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        idusuario=getIntent().getStringExtra("idusuario");

        btnPerfil=(Button)findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(this);

        btnVentas=(Button)findViewById(R.id.btnVentas);
        btnVentas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnPerfil:
                Intent i=new Intent(getApplicationContext(),perfil.class);
                i.putExtra("idusuario",idusuario);
                startActivity(i);
                break;

            case R.id.btnVentas:
                Intent j=new Intent(getApplicationContext(),ventas.class);
                j.putExtra("idusuario",idusuario);
                startActivity(j);
                break;

        }


    }
}
