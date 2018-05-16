package com.apps.michelramirez.comes_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity  implements View.OnClickListener{

    Button btnVender;

    Button btnOpciones,btnCatalogo;

    String idusuario="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_login);

        btnVender=(Button)findViewById(R.id.btnVender);
        btnVender.setOnClickListener(this);

        btnOpciones=(Button)findViewById(R.id.btnOpciones);
        btnOpciones.setOnClickListener(this);

        btnCatalogo=(Button)findViewById(R.id.btnCatalogo);
        btnCatalogo.setOnClickListener(this);

        idusuario=getIntent().getStringExtra("idusuario");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnVender:
                Intent i=new Intent(getApplicationContext(),vender.class);
                i.putExtra("idusuario",idusuario);
                startActivity(i);
                break;

            case R.id.btnOpciones:
                Intent j=new Intent(getApplicationContext(),opciones.class);
                j.putExtra("idusuario",idusuario);
                startActivity(j);
                break;

            case R.id.btnCatalogo:
                Intent k=new Intent(getApplicationContext(),catalogo.class);
                k.putExtra("idusuario",idusuario);
                startActivity(k);
                break;
        }


    }
}
