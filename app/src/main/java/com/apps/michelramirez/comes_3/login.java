package com.apps.michelramirez.comes_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class login extends AppCompatActivity  implements View.OnClickListener{

    Button btnVender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_login);

        btnVender=(Button)findViewById(R.id.btnVender);
        btnVender.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent(getApplicationContext(),vender.class);
        // i.putExtra("cod",usuario.getText().toString());
        startActivity(i);

    }
}
