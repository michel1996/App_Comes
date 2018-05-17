package com.apps.michelramirez.comes_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.SystemClock.sleep;

public class detalles extends AppCompatActivity implements View.OnClickListener{

    private Session session;//global variable

    String idusuario,tipo="";

    JSONArray array = null;

    TextView comprador, vendedor, estado;

    //EditText ubicacion;
    //ImageView food_image;
    Button btnDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        comprador=(TextView)findViewById(R.id.comprador);
        vendedor=(TextView)findViewById(R.id.vendedor);
        estado=(TextView)findViewById(R.id.estado);

        btnDetalle=(Button)findViewById(R.id.btnDetalle);
        btnDetalle.setOnClickListener(this);

        idusuario=getIntent().getStringExtra("idusuario");

        session = new Session(getApplicationContext()); //in oncreate

        new Thread() {
            public void run() {
                try {
                    array = new JSONArray(getOrden(getIntent().getStringExtra("ID_ORDEN")));
                }
                catch(Exception e){
                    Log.d("asdsa", "fuck:" + e);
                }
            }
        }.start();

        while (array == null)
            sleep(200);

        try{
            JSONObject obj = array.getJSONObject(0);

            comprador.setText("Comprador: " + obj.getString("idComprador"));
            vendedor.setText("Vendedor: " + obj.getString("idVendedor"));
            estado.setText("Estado: " + obj.getString("estado"));
            tipo=obj.getString("tipo");

            if(tipo=="cliente" && obj.getString("estado")=="0")
            {
                btnDetalle.setText("Finalizar orden");
            }
            if(tipo=="cliente" && obj.getString("estado")=="1")
            {
                btnDetalle.setText("La orden ha sido finalizada");
                btnDetalle.setEnabled(false);
            }
            if(tipo=="vendedor" && obj.getString("estado")=="0")
            {
                btnDetalle.setText("Orden pendiente de finalizar");
                btnDetalle.setEnabled(false);
            }
            if(tipo=="vendedor" && obj.getString("estado")=="1")
            {
                btnDetalle.setText("La orden ha sido finalizada");
                btnDetalle.setEnabled(false);
            }
        }
        catch(Exception e){
            Log.d("asdsa", "fuck:" + e);
        }
    }

    @Override
    public void onClick(View v) {

        Thread tr=new Thread()
        {
            @Override
            public void run()
            {
                final String resultado=enviarDatosGET(getIntent().getStringExtra("ID_ORDEN"));

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int r=obtDatosJSON(resultado);
                        switch(r)
                        {
                            case 1:

                                Toast.makeText(getApplicationContext(),"Orden finalizada",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(getApplicationContext(),orden.class);
                                i.putExtra("idusuario",idusuario);
                                startActivity(i);

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

        public String getOrden(String idorden) {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/detalles.php?idorden=" + idorden+"&idusuario="+idusuario);
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
                Log.d("asdasd", "HTTP OK");
            }
            else
            {
                Log.d("asdasd", "HTTP NOT OK");
            }
        }
        catch(Exception e){
            Log.d("asdsa", ""+e);
        }

        return result.toString();
    }


    public String enviarDatosGET(String idorden)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/terminar.php?idorden="+idorden);
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
           else
                res=0;

        }catch(Exception e){}
        return res;
    }

}
