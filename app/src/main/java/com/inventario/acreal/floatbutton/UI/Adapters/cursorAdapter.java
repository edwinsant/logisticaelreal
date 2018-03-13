package com.inventario.acreal.floatbutton.UI.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.inventario.acreal.floatbutton.Models.listas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crodriguez on 22/2/2017.
 */


public class cursorAdapter extends CursorAdapter {
    private List<listas> data;
    public cursorAdapter(Context context, Cursor c) {
        super(context, c);
       data = new ArrayList<listas>();
        while(!c.isAfterLast()) {

            data.add( new listas(c.getString(0),c.getString(1),c.getString(2)));
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
