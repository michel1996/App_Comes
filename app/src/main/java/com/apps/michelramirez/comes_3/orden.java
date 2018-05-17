package com.apps.michelramirez.comes_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;

public class orden extends AppCompatActivity {

    JSONArray array = null;
    String idusuario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden);

        idusuario=getIntent().getStringExtra("idusuario");

        List<Item_orden> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.ordenes);
        listView.setAdapter(new orden_adapter(this, image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Item_orden itemclicked = (Item_orden) o;
//                Toast.makeText(getApplicationContext(),String.valueOf(itemclicked.getId()),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), detalles.class);
                i.putExtra("ID_ORDEN", Integer.toString(itemclicked.getId()));
                i.putExtra("idusuario",idusuario);
                startActivity(i);
            }
        });
    }
    private  List<Item_orden> getListData() {
               // Toast.makeText(getApplicationContext(),getOrdenes(),Toast.LENGTH_SHORT).show();

        new Thread() {
            public void run() {
                try {
                    array = new JSONArray(getOrdenes());
                    Log.d("asdsa", "fuck:" + array);
                }
                catch(Exception e){
                    Log.d("asdsa", "fuck:" + e);
                }
            }
        }.start();

        while (array == null)
            sleep(200);

        List<Item_orden> list = new ArrayList<Item_orden>();

        for (int i = 0; i < array.length(); i++){
            try{
                JSONObject obj = array.getJSONObject(i);
                String ubicacion = obj.getString("ubicacion");
                String fecha = obj.getString("fecha");
                String id = obj.getString("idOrden");

                list.add(new Item_orden(fecha, ubicacion, Integer.parseInt(id)));
            }
            catch(Exception e){
                Log.d("asdsa", "fuck:" + e);
            }
        }

        return list;
    }

    public String getOrdenes()
    {
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder result=null;

        try
        {
            url=new URL("https://proyectocomes.000webhostapp.com/obtener_ordenes.php?idusuario="+idusuario);
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
}
