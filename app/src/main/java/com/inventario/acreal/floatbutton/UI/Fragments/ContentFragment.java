package com.inventario.acreal.floatbutton.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.inventario.acreal.floatbutton.UI.Adapters.Listviewadatper;
import com.inventario.acreal.floatbutton.Models.listas;
import com.inventario.acreal.floatbutton.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 04-06-2015.
 */
public class ContentFragment extends Fragment {
    ListView lista;
    Listviewadatper adapter ;
    Context context;
    List lista1 = new ArrayList<listas>();
    Boolean floa = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.content_fragment, container, false);

        return v;
    }
}
