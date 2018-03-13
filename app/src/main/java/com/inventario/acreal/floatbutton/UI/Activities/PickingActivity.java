package com.inventario.acreal.floatbutton.UI.Activities;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.inventario.acreal.floatbutton.UI.Adapters.PickingAdapter;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class PickingActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    HttpTransportSE transporte;
    private Toolbar toolbar;
    private SQLiteHelper sqLiteHelper;
    List<Picking> lista1,listadata;
    Bundle bundle;
    RecyclerView recyclerView;
    PickingAdapter adapter;
    Dialog customDialog = null;
    SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuadros);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cuadros de pre-despacho");
        sqLiteHelper = new SQLiteHelper(this);
        bundle = getIntent().getExtras();
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshPicking);
        mRefresh.setColorSchemeResources(R.color.AccentColor);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        customDialog = new Dialog(this,R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.layout);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(true);
                TareaWSInsercion1 tarea = new TareaWSInsercion1();
                tarea.execute();
            }
        });

        lanzarprogrs();
        TareaWSInsercion1 tarea = new TareaWSInsercion1();
        tarea.execute();
    }
    public void lanzarprogrs(){

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Buscando información");
        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Espere mientras los cuadros se cargan");
        customDialog.show();

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

    public void rellenarlista(){
        lista1 = sqLiteHelper.Selectcuadros();
        listadata= lista1;
        adapter = new PickingAdapter(lista1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(PickingActivity.this, LineActivity.class);
                intent.putExtra("NCARGA", listadata.get(position).getNCARGA());
                intent.putExtra("usuario",  bundle.getString("usuario") );
                startActivity(intent);
                finish();
                //Toast.makeText(ListTraslado.this, "selecciono la tarea numero: "+listadata.get(position).getNo_Tarea() ,Toast.LENGTH_LONG).show();

            }
        });
        recyclerView.setAdapter(adapter);
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
            final String text1 = model.getTRANSPORTISTA().toLowerCase();
            final String text2 = model.getBODEGA().toLowerCase();
            final String text3 =  model.getNCARGA().toLowerCase();
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
            final String METHOD_NAME = "VerCuadrosPendientes";
            final String SOAP_ACTION = "http://10.1.1.18/VerCuadrosPendientes";
            String pureba ;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("strUsuario", bundle.getString("usuario"));

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            boolean ret = false;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            String prueba;
            transporte = new HttpTransportSE(URL);


            try
            {
                sqLiteHelper.borrarXcuadros();
                transporte.call(SOAP_ACTION  ,envelope);
                // boolean retornar = (Boolean) envelope.getResponse();
                SoapObject obj1 = (SoapObject) envelope.getResponse();
                String ubicacion,descrip, articulo;
                //String[] lis = new String[obj1.getPropertyCount()];
                lista1 = new ArrayList<Picking>();
                //TODO: revisar aqui los valores
                if (obj1.getPropertyCount() != 0) {
                    for (int i = 0; i < obj1.getPropertyCount(); i++) {
                        Picking cuadros = new Picking();
                        SoapObject od = (SoapObject) obj1.getProperty(i);
                          //  if(od.getProperty(2).toString().equals("PISO"))
                        cuadros.setBODEGA(od.getProperty(0).toString());
                        cuadros.setNCARGA((od.getProperty(1).toString()));
                        cuadros.setFECHA(od.getProperty(2).toString());
                        cuadros.setTRANSPORTISTA(od.getProperty(3).toString());
                        cuadros.setNLINEA(od.getProperty(4).toString());
                        //cuadros.setCODIGO_CORTO(od.getProperty(4).toString());
                        cuadros.setLINEA(od.getProperty(5).toString());
                        //cuadros.setARTICULO(od.getProperty(5).toString());
                        //cuadros.setDESCRIPCION(od.getProperty(6).toString());
                        cuadros.setNPEDIDOS((od.getProperty(6).toString()));
                        /*cuadros.setNLINEA(od.getProperty(7).toString());
                        cuadros.setLINEA(od.getProperty(8).toString());
                        cuadros.setNPEDIDOS((od.getProperty(9).toString()));
                        cuadros.setUBICACION(od.getProperty(10).toString());
                        String lo = od.getProperty(11).toString();
                        if(lo.equals("anyType{}"))
                            cuadros.setLOTE("");
                        else
                            cuadros.setLOTE(od.getProperty(11).toString());
                        cuadros.setCOD_VERIFICACION((od.getProperty(12).toString()));
                        cuadros.setSECUENCIA((od.getProperty(13).toString()));
                        cuadros.setCANT_TOTAL_UMP((od.getProperty(14).toString()));
                        cuadros.setCANT_TOTAL_UMS((od.getProperty(15).toString()));
                        cuadros.setUMT(od.getProperty(16).toString());
                        cuadros.setFACTOR((od.getProperty(17).toString()));
                        cuadros.setCANT_UMS((od.getProperty(18).toString()));
                        cuadros.setUMS(od.getProperty(19).toString());
                        cuadros.setUNIDAD_SUELTAS((od.getProperty(20).toString()));
                        cuadros.setExistencia((od.getProperty(21).toString()));*/
                        sqLiteHelper.InsertXcuadros(cuadros);
                      //  lista1.add(cuadros);

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
            mRefresh.setRefreshing(false);
            // progressBar.setVisibility(View.INVISIBLE);

            if(result == 0){
                rellenarlista();
                adapter.notifyDataSetChanged();
            }

            else if(result ==1 )
                Toast.makeText(PickingActivity.this, "No hay datos que mostrar" ,Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PickingActivity.this, "Problema de conexion" ,Toast.LENGTH_LONG).show();

        }

    }
}
