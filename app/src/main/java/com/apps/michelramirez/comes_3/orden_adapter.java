package com.apps.michelramirez.comes_3;

/**
 * Created by emmesis on 01/05/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class orden_adapter extends BaseAdapter {

    private List<Item_orden> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public orden_adapter(Context aContext,  List<Item_orden> listData) {
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
            convertView = layoutInflater.inflate(R.layout.item_orden, null);
            holder = new ViewHolder();
            holder.fecha = (TextView) convertView.findViewById(R.id.fecha);
            holder.ubicacion = (TextView) convertView.findViewById(R.id.ubicacion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item_orden producto = this.listData.get(position);
        holder.fecha.setText(producto.getFecha());
        holder.ubicacion.setText(producto.getUbicacion());

        return convertView;
    }


    static class ViewHolder {
        TextView fecha;
        TextView ubicacion;
    }

}
