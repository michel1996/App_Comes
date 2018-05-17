package com.apps.michelramirez.comes_3;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.daasuu.ei.Ease;
import com.daasuu.ei.EasingInterpolator;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity  implements View.OnClickListener{

    Button btnVender;

    Button btnOpciones,btnCatalogo,btnOrdenes;

    String idusuario="";

    ImageView img;

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

        btnOrdenes=(Button)findViewById(R.id.btnOrdenes);
        btnOrdenes.setOnClickListener(this);

        img=(ImageView) findViewById(R.id.logo);

        idusuario=getIntent().getStringExtra("idusuario");

        Thread tr=new Thread()
        {
            @Override
            public void run()
            {
                final String resultado=enviarDatosGET(idusuario);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int r=obtDatosJSON(resultado);
                        switch(r)
                        {
                            case 1:
                                btnOrdenes.setBackgroundColor(Color.GREEN);
                                img.setImageResource(R.drawable.cart);
                                doBounceAnimation(img);



                                break;


                            case 0:
                                break;

                        }

                    }
                });
            }
        };
        tr.start();
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
            case R.id.btnOrdenes:
                Intent l=new Intent(getApplicationContext(),orden.class);
                l.putExtra("idusuario",idusuario);
                startActivity(l);
                break;
        }


    }

    public String enviarDatosGET(String idusuario)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/notificaciones.php?idusuario="+idusuario);
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
    public int obtDatosJSON(String response)
    {
        int res=0;
        try
        {
            JSONArray array=new JSONArray();
            array.put(response);
            // String str1 = array.optString(0).replaceAll("[]", "");

            String cadena=response.replaceAll("/[^A-Za-z0-9]/", "");
            // Toast.makeText(getApplicationContext(),""+cadena,Toast.LENGTH_LONG).show();

            if(cadena.toLowerCase().contains("true".toLowerCase()))
                res=1;

            if(cadena.toLowerCase().contains("false".toLowerCase()))
                res=0;

        }catch(Exception e){}
        return res;
    }


    private void doBounceAnimation(ImageView img) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(img, "translationX", 0, 25, 0);
        animator.setInterpolator(new EasingInterpolator(Ease.ELASTIC_IN_OUT));
        animator.setStartDelay(500);
        animator.setDuration(1500);
        animator.start();
    }
}
