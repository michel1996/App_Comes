package com.apps.michelramirez.comes_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.himanshusoni.quantityview.QuantityView;

public class vender extends AppCompatActivity implements View.OnClickListener{

    Button btnPublicar;
    EditText titulo, descripcion,precio;
    String idusuario="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);
        titulo=(EditText)findViewById(R.id.titulo);
        descripcion=(EditText)findViewById(R.id.vendedor);
        precio=(EditText) findViewById(R.id.precio);
        btnPublicar=(Button)findViewById(R.id.btnPublicar);
        btnPublicar.setOnClickListener(this);

        idusuario=getIntent().getStringExtra("idusuario");
    }

    @Override
    public void onClick(View view) {

        String val_precio=precio.getText().toString();

        Thread tr=new Thread()
        {
            @Override
            public void run()
            {

                String val_titulo=titulo.getText().toString();
                String val_descripcion=descripcion.getText().toString();
                String val_precio=precio.getText().toString();

                final QuantityView cantidad = (QuantityView) findViewById(R.id.cantidad);
                int val_cantidad=cantidad.getQuantity();
                //String val_cantidad=String.valueOf(num_cantidad);

                final String resultado=enviarDatosGET(idusuario,val_titulo,val_descripcion,val_precio,val_cantidad);
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int r=obtDatosJSON(resultado);
                        if(r>0)
                        {
                            //Toast.makeText(getApplicationContext(),"Publicación exitosa",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),image.class);
                            i.putExtra("idProducto",r);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Error en la publicación",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };
        if(isNumeric(val_precio))
        {
            tr.start();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"El precio tiene que ser solo números",Toast.LENGTH_LONG).show();
        }

    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public String enviarDatosGET(String idusuario,String titulo,String descripcion,String precio,int cantidad)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://proyectocomes.000webhostapp.com/publicar.php?titulo="+titulo+"&descripcion="+descripcion+"&precio="+precio+"&cantidad="+cantidad+"&idusuario="+idusuario);
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
        String str="";
        try
        {
            JSONArray json=new JSONArray(response);
            if(json.length()>0)
            {
                str = response.replaceAll("\\D+","");
                res= Integer.parseInt(str);
            }

        }catch(Exception e){}
        return res;
    }
}
