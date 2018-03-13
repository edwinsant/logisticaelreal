package com.inventario.acreal.floatbutton.UI.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.inventario.acreal.floatbutton.Utils.DividerItemDecoration;
import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.UI.Adapters.LineAdapter;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class LineActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private Toolbar toolbar;
    private SQLiteHelper sqLiteHelper;
    List<Picking> lista1,listadata;
    Bundle bundle;
    RecyclerView recyclerView;
    LineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sqLiteHelper = new SQLiteHelper(this);
        bundle = getIntent().getExtras();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        lista1 = sqLiteHelper.SelectLineas(bundle.getString("NCARGA"));
        getSupportActionBar().setTitle("Cuadro " + bundle.getString("NCARGA"));
        rellenarlista();
    }

    public void rellenarlista(){
        listadata= lista1;
        adapter = new LineAdapter(lista1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
               Intent intent = new Intent(LineActivity.this, ProductActivity.class);
                intent.putExtra("NLINEA", listadata.get(position).getNLINEA());
                intent.putExtra("BODEGA", listadata.get(position).getBODEGA());
                intent.putExtra("NCARGA",bundle.getString("NCARGA"));
                intent.putExtra("usuario",  bundle.getString("usuario") );
                intent.putExtra("LINEA",listadata.get(position).getLINEA());
                startActivity(intent);
                finish();
                //Toast.makeText(MoveActivity.this, "selecciono la tarea numero: "+listadata.get(position).getNo_Tarea() ,Toast.LENGTH_LONG).show();

            }
        });

        recyclerView.setAdapter(adapter);
    }

    public boolean onSupportNavigateUp() {

        Intent intent = new Intent(this,PickingActivity.class);
        intent.putExtra("usuario",bundle.getString("usuario"));
        startActivity(intent);
        finish();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
//        Cursor c = (Cursor) lista1;

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(lista1);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        Snackbar.make(findViewById(R.id.drawer), "Query: " + query, Snackbar.LENGTH_LONG)
                .show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Picking> filteredModelList = filter(lista1, newText);
        adapter.setFilter(filteredModelList);

        return true;
    }
    private List<Picking> filter(List<Picking> models, String query) {
        query = query.toLowerCase();

        final List<Picking> filteredModelList = new ArrayList<>();
        for (Picking model : models) {
            final String text = model.getFECHA().toLowerCase();
            final String text1 = model.getLINEA().toLowerCase();

            if (text.contains(query) ||text1.contains(query)) {
                filteredModelList.add(model);
            }
        }
        listadata = filteredModelList;
        return filteredModelList;
    }
}
