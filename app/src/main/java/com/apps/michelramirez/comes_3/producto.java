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

public class producto extends AppCompatActivity {

    private Session session;//global variable

    String idusuario="";

    JSONArray array = null;

    TextView usuario, telefono, producto, descripcion, cantidad;

    EditText ubicacion;
    ImageView food_image;
    Button compra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        idusuario=getIntent().getStringExtra("idusuario");

        session = new Session(getApplicationContext()); //in oncreate

        usuario=(TextView)findViewById(R.id.usuario);
        telefono=(TextView)findViewById(R.id.text2);
        producto=(TextView)findViewById(R.id.comprador);
        descripcion=(TextView)findViewById(R.id.vendedor);
        cantidad=(TextView)findViewById(R.id.cantidad);
        food_image = (ImageView)findViewById(R.id.imagen);
        compra=(Button)findViewById(R.id.btncompra);

        ubicacion=(EditText)findViewById(R.id.ubicacion);

        compra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            array = new JSONArray(enviarDatosGET(ubicacion.toString()));
                        }
                        catch(Exception e){
                            Log.d("asdsa", "fuck:" + e);
                        }
                    }
                }.start();
                Toast.makeText(getApplicationContext(),"Orden confirmada", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),login.class);
                i.putExtra("idusuario",idusuario);
                startActivity(i);
            }
        });

        new Thread() {
            public void run() {
                try {
                    array = new JSONArray(getProducto());
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

            usuario.setText("Vendedor: " + obj.getString("usuario"));
            telefono.setText("Telefono: " + obj.getString("telefono"));
            producto.setText("Producto: " + obj.getString("producto"));
            descripcion.setText("Descripcion: " + obj.getString("descripcion"));
            cantidad.setText("Cantidad Disponible: " + obj.getString("cantidad"));

            Glide.with(getApplicationContext()).load(getIntent().getStringExtra("IMAGEN")).into(food_image);

        }
        catch(Exception e){
            Log.d("asdsa", "fuck:" + e);
        }
    }

    public String getProducto() {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/producto.php?idproducto=" + getIntent().getStringExtra("ID_PRODUCTO"));
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


    public String enviarDatosGET(String ubicacion)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/orden.php?ubicacion="+ubicacion+"&email="+idusuario+"&idproducto="+getIntent().getStringExtra("ID_PRODUCTO"));
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
