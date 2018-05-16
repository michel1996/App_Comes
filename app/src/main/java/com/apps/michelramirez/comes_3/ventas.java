package com.apps.michelramirez.comes_3;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ventas extends AppCompatActivity {

    String idusuario="";


    TextView total_ventas;

    TextView mejor_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        idusuario=getIntent().getStringExtra("idusuario");
        total_ventas=(TextView)findViewById(R.id.total_ventas);
        mejor_producto=(TextView)findViewById(R.id.mejor_producto);

        Thread tr=new Thread()
        {
            @Override
            public void run()
            {
                final String total=enviarDatosGET(idusuario,"total");
                final String[] parts = total.split("\""); // String array, each element is text between dots
               final int total_ventas=Integer.parseInt(parts[3]);

                final String best=enviarDatosGET(idusuario,"mejor");
                final String[] mejores = best.split("\"");
                //final String cadena=total.replaceAll("/[^A-Za-z0-9]/", "");


                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mejor_producto.setText(mejores[3]);
                        startCountAnimation(total_ventas);

                        // Toast.makeText(getApplicationContext(),mejor[2],Toast.LENGTH_LONG).show();
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

    public String enviarDatosGET(String idusuario,String opcion)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;


                try
                {
                    url=new URL("http://proyectocomes.000webhostapp.com/ventas.php?idusuario="+idusuario+"&opcion="+opcion);
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

    private void startCountAnimation(int total) {
        ValueAnimator animator = ValueAnimator.ofInt(0, total);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                total_ventas.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

}
