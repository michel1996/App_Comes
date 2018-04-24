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
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class registro extends AppCompatActivity implements View.OnClickListener{

    Button btnRegistrar;
    EditText nombre, apellido,telefono,email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre=(EditText)findViewById(R.id.crearNombre);
        apellido=(EditText)findViewById(R.id.crearApellido);
        telefono=(EditText)findViewById(R.id.crearTelefono);
        email=(EditText)findViewById(R.id.crearCorreo);
        password=(EditText)findViewById(R.id.crearPassword);
        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Thread tr=new Thread()
        {
            @Override
            public void run()
            {
                final String resultado=enviarDatosGET(nombre.getText().toString(),apellido.getText().toString(),telefono.getText().toString(),email.getText().toString(),password.getText().toString());
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int r=obtDatosJSON(resultado);
                        switch(r)
                        {
                            case 1:
                                Intent i=new Intent(getApplicationContext(),login.class);
                                startActivity(i);
                                break;

                            case -1:
                                Toast.makeText(getApplicationContext(),"Ya hay una cuenta registrada con ese correo.",Toast.LENGTH_LONG).show();
                                break;

                            case 0:
                                Toast.makeText(getApplicationContext(),"Ha ocurrido un error. Intente de nuevo.",Toast.LENGTH_LONG).show();
                                break;

                        }

                    }
                });
            }
        };
        tr.start();
    }

    public String enviarDatosGET(String nombre,String apellido,String telefono,String email,String password)
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("http://192.168.1.69/Comes/registro.php?nombre="+nombre+"&apellido="+apellido+"&telefono="+telefono+"&email="+email+"&password="+password);
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

            if(cadena.toLowerCase().contains("existente".toLowerCase()))
                res=-1;
            if(cadena.toLowerCase().contains("exito".toLowerCase()))
                res=1;
            if(cadena.toLowerCase().contains("error".toLowerCase()))
                res=0;

        }catch(Exception e){}
        return res;
    }
}
