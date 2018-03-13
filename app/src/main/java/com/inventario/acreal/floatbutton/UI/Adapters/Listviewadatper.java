package com.inventario.acreal.floatbutton.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Models.listas;
import com.inventario.acreal.floatbutton.R;

import java.util.List;

/**
 * Created by crodriguez on 15/2/2017.
 */

public class Listviewadatper extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    private List<listas> mProductList;
    private LayoutInflater mInflater;
    private boolean mShowQuantity;
    private boolean checked = false;
    String posicion, Codigo, Cantidad;
    public Listviewadatper (Context context, List<listas> list) {
        mProductList = list;
        //mInflater = inflater;
        this.context = context;

    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.listview, null,true);
        listas lista = (listas)getItem(position);

        View circleView = (View) single_row.findViewById(R.id.circleView);
        TextView tex1 = (TextView) single_row.findViewById(R.id.txt_posicion);
        TextView tex2 = (TextView) single_row.findViewById(R.id.txt_codigo);
        TextView tex3 = (TextView) single_row.findViewById(R.id.stxtcajas);
        TextView tex4 = (TextView) single_row.findViewById(R.id.stxtunidades);
        circleView.setBackgroundColor(Color.parseColor(lista.getColor()));
        tex1.setText(lista.Getnombre());
        tex2.setText(lista.getpuntos());


        return single_row;
    }
}
