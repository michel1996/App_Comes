package com.apps.michelramirez.comes_3;

/**
 * Created by emmesis on 01/05/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.bumptech.glide.Glide;

public class catalogo_adapter extends BaseAdapter {

    private List<Item> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public catalogo_adapter(Context aContext,  List<Item> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_catalogo, null);
            holder = new ViewHolder();
            holder.food_image = (ImageView) convertView.findViewById(R.id.imageView_food);
            holder.title = (TextView) convertView.findViewById(R.id.textView_1);
            holder.description = (TextView) convertView.findViewById(R.id.textView_2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item producto = this.listData.get(position);
        holder.title.setText(producto.getTitle());
        holder.description.setText("Precio: $" + producto.getPrecio());

        if (producto.getImage() == "") {
            int imageId = this.getMipmapResIdByName("no_photo");
            holder.food_image.setImageResource(imageId);
        }
        else{
            Glide.with(context).load(producto.getImage()).into(holder.food_image);
        }
        return convertView;
    }

    public Bitmap getBitmapfromUrl(String imgUrl) {
        try {
            URL url = new URL("http://proyectocomes.000webhostapp.com/productos/foto.png");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "drawable", pkgName);
        return resID;
    }

    static class ViewHolder {
        ImageView food_image;
        TextView title;
        TextView description;
    }

}
