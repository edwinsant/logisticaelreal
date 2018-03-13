package com.inventario.acreal.floatbutton.UI.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.inventario.acreal.floatbutton.Utils.DividerItemDecoration;
import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.UI.Adapters.ProductAdapter;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private SQLiteHelper sqLiteHelper;
    List<Picking> lista1,listadata;
    Bundle bundle;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    HttpTransportSE transporte;
    Dialog customDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sqLiteHelper = new SQLiteHelper(this);
        bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Línea " + bundle.getString("LINEA"));

        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //COLOCAR LA LINEA ENCABEZADO DE ARTICULO

   //     Linea3 = (TextView)findViewById(R.id.lblArticulos);
    //    Linea3.setText("CD : " +bundle.getString("NCARGA") + " / LINEA: "+bundle.getString("LINEA")); //Cambio por DS 28/09/2017


        lanzarprogrs();
        TareaWSInsercion1 tarea = new TareaWSInsercion1();
        tarea.execute();
        //lista1 = sqLiteHelper.SelectcuadrosArticulos(bundle.getString("NCARGA"), bundle.getString("NLINEA"));
       // rellenarlista();
    }
    public void rellenarlista(){
        lista1 = sqLiteHelper.SelectcuadrosArticulos(bundle.getString("NCARGA"), bundle.getString("NLINEA"));
        listadata= lista1;
        adapter = new ProductAdapter(lista1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(ProductActivity.this, ConfirmationActivity.class);
                intent.putExtra("Id", listadata.get(position).getId());
                intent.putExtra("NLINEA", bundle.getString("NLINEA"));
                intent.putExtra("BODEGA",bundle.getString("BODEGA"));
                intent.putExtra("NCARGA",bundle.getString("NCARGA"));
                intent.putExtra("usuario",  bundle.getString("usuario"));
                intent.putExtra("LINEA",bundle.getString("LINEA"));
                startActivity(intent);
                finish();

            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void onBackPressed() {

        Intent intent = new Intent(ProductActivity.this, LineActivity.class);
        intent.putExtra("NLINEA", bundle.getString("NLINEA"));
        intent.putExtra("NCARGA",bundle.getString("NCARGA"));
        intent.putExtra("usuario",  bundle.getString("usuario") );
        startActivity(intent);
        finish();

        // do something on back.
        return;
    }

    public boolean onSupportNavigateUp() {

        Intent intent = new Intent(this, LineActivity.class);
        intent.putExtra("NLINEA", bundle.getString("NLINEA"));
        intent.putExtra("NCARGA",bundle.getString("NCARGA"));
        intent.putExtra("usuario",  bundle.getString("usuario") );
        startActivity(intent);
        finish();
        return true;
    }

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
    public void lanzarprogrs(){

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Buscando informacion");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Espere mientras los articulos se cargan");

        customDialog.show();

    }
    private List<Picking> filter(List<Picking> models, String query) {
        query = query.toLowerCase();

        final List<Picking> filteredModelList = new ArrayList<>();
        for (Picking model : models) {
            final String text = model.getDESCRIPCION().toLowerCase();
            final String text1 = model.getARTICULO().toLowerCase();
            final String text2 = model.getUBICACION().toLowerCase();
            final String text3 =  model.getLOTE().toLowerCase();
            if (text.contains(query) ||text1.contains(query) || text2.contains(query) || text3.contains(query)) {
                filteredModelList.add(model);
            }
        }
        listadata = filteredModelList;
        return filteredModelList;
    }
    private class TareaWSInsercion1 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {


            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL="http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "VerCuadroPendiente_X_Linea";
            final String SOAP_ACTION = "http://10.1.1.18/VerCuadroPendiente_X_Linea ";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("strBodega", bundle.getString("BODEGA"));
            request.addProperty("dblNCarga", bundle.getString("NCARGA"));
            request.addProperty("strCod_Linea", bundle.getString("NLINEA"));


            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION  ,envelope);

                SoapObject obj1 = (SoapObject) envelope.getResponse();
                sqLiteHelper.borrarXArticulos();

                if (obj1.getPropertyCount() != 0) {
                    lista1 = new ArrayList<>();
                    for (int i = 0; i < obj1.getPropertyCount(); i++) {
                        Picking picking = new Picking();
                        SoapObject od = (SoapObject) obj1.getProperty(i);
                        picking.setBODEGA(od.getProperty(0).toString());
                        picking.setNCARGA(od.getProperty(1).toString());
                        picking.setCODIGO_CORTO(od.getProperty(2).toString());
                        picking.setARTICULO(od.getProperty(3).toString());
                        picking.setDESCRIPCION(od.getProperty(4).toString());
                        picking.setNPEDIDOS(od.getProperty(5).toString());
                        picking.setUBICACION(od.getProperty(6).toString());
                        picking.setLOTE(od.getProperty(7).toString());
                        picking.setCOD_VERIFICACION(od.getProperty(8).toString());
                        picking.setSECUENCIA(od.getProperty(9).toString());
                        picking.setCANT_TOTAL_UMP(od.getProperty(10).toString());
                        picking.setCANT_TOTAL_UMS(od.getProperty(14).toString());
                        picking.setUMT(od.getProperty(12).toString());
                        picking.setFACTOR(od.getProperty(13).toString());
                        picking.setCANT_UMS(od.getProperty(11).toString());
                        picking.setUMS(od.getProperty(15).toString());
                        picking.setUNIDAD_SUELTAS(od.getProperty(16).toString());
                        picking.setExistencia(od.getProperty(17).toString());
                        picking.setNLINEA(bundle.getString("NLINEA"));
                        //lista1.add(picking);
                        sqLiteHelper.InsertXArticulos(picking);
                    }
                    resul = 0;

                }
                else
                    resul = 1;
            }
            catch (Exception e) {
                resul = 3;
                //prueba = e.toString();
                //  e.printStackTrace();
            }

            return resul;
        }
        public Move guardar(SoapObject od){

            Move move = new Move();
            move.setNo_Tarea(String.valueOf(od.getProperty(0).toString()));
            move.setBodega(od.getProperty(1).toString());

            move.setUbicacionO(od.getProperty(2).toString().substring(0,2)+" - "+od.getProperty(2).toString().substring(2,5)+" - "+od.getProperty(2).toString().substring(5,6)+" - "+od.getProperty(2).toString().substring(6));

            move.setUbicacionO(od.getProperty(2).toString());
            move.setLote(od.getProperty(3).toString());
            move.setFechavencimiento(od.getProperty(4).toString());
            move.setArticulo(od.getProperty(5).toString());
            move.setDescripcion(od.getProperty(6).toString());
            move.setUMPallet(od.getProperty(7).toString());
            move.setCantidad_UMP(od.getProperty(8).toString());
            move.setUMT(od.getProperty(9).toString());
            move.setCantidad_UMT(od.getProperty(10).toString());
            move.setFactor_Conv_UMT(od.getProperty(11).toString());
            move.setUbicacionD(od.getProperty(12).toString());
            move.setSeguridad(od.getProperty(13).toString());

            sqLiteHelper.InsertTraslado(move);
            // lista1.add(move);
            return move;
        }
        @Override
        protected void onPreExecute(){
            // progressBar = (ProgressBar)findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            // progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Integer result) {
              customDialog.dismiss();
            // progressBar.setVisibility(View.INVISIBLE);

            if(result == 0){
                rellenarlista();
            }

            else if(result ==1 )
                Toast.makeText(ProductActivity.this, "No hay datos que mostrar" ,Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ProductActivity.this, "Problema de conexion" ,Toast.LENGTH_LONG).show();


        }


    }

}
