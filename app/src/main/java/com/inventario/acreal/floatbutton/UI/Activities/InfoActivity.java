package com.inventario.acreal.floatbutton.UI.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.inventario.acreal.floatbutton.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardarinfo);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.persona:
                //metodoSearch()
                mostrar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void mostrar()
    {

            Toast.makeText(this, "Se guardo la informacion", Toast.LENGTH_SHORT).show();

    }
    public void onBackPressed() {




        // do something on back.
        return;
    }
}
