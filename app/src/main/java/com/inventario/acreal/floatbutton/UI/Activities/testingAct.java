package com.inventario.acreal.floatbutton.UI.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.inventario.acreal.floatbutton.Models.Product;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.Adapters.ProductAdapter;
import com.inventario.acreal.floatbutton.UI.Adapters.TestAdapter;
import com.inventario.acreal.floatbutton.Utils.prodTest;

import java.util.ArrayList;

public class testingAct extends AppCompatActivity {

    RecyclerView recyclerView;
    prodTest testing = new prodTest();
    Toolbar toolbar;
    Bundle bundle;
    SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Recepción de devoluciones");

        bundle = getIntent().getExtras();
        ArrayList<Product> prod = testing.getProducts();

        recyclerView = (RecyclerView) findViewById(R.id.rv_test);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        TestAdapter adapter = new TestAdapter(getApplicationContext());
        adapter.setItems(prod);
        recyclerView.setAdapter(adapter);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshReturns);
        mRefresh.setColorSchemeResources(R.color.AccentColor);

     /*   mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefresh.setRefreshing(false);
                    }
                }, 5000);
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(this,MenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        return true;
    }
}
