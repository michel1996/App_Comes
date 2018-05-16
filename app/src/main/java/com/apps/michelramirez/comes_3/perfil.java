package com.apps.michelramirez.comes_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class perfil extends AppCompatActivity {

    String idusuario="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        idusuario=getIntent().getStringExtra("idusuario");

        Thread tr=new Thread()
        {
            @Override
            public void run()
            {
                final String usuario=enviarDatosGET(idusuario);
                //final String[] parts = total.split("\""); // String array, each element is text between dots
               // final int total_ventas=Integer.parseInt(parts[3]);

                //final String mejor_producto=enviarDatosGET(idusuario,"mejor");



                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplicationContext(),usuario,Toast.LENGTH_LONG).show();
                       /* int r=obtDatosJSON(resultado);
                        if(r>0)
                        {
                            Intent i=new Intent(getApplicationContext(),login.class);
                            i.putExtra("idusuario",resultado.substring(15,16));
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Usuario o password incorrectos",Toast.LENGTH_LONG).show();
                        }*/
                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosGET(String idusuario)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;


        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/perfil.php?idusuario="+idusuario);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            respuesta=connection.getResponseCode();

            result=new StringBuilder();

            if(respuesta==HttpURLConnection.HTTP_OK)
            {
                InputStream in=new BufferedInputStream(connection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));

                while((linea=reader.readLine())!=null)
                {
                    result.append(linea);
                }
            }
            else
            {
                Log.d("myTag", "HTTP NOT OK");
            }
        }
        catch(Exception e){
            Log.d("edaeda", ""+e);
        }

        return result.toString();
    }
}
