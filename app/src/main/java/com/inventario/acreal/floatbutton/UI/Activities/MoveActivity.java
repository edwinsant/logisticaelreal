package com.inventario.acreal.floatbutton.UI.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.inventario.acreal.floatbutton.UI.Fragments.ContentFragment;
import com.inventario.acreal.floatbutton.Utils.DividerItemDecoration;
import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.UI.Adapters.Listviewadatper;
import com.inventario.acreal.floatbutton.UI.Adapters.MaterialPaletteAdapter;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Events.RecyclerViewOnItemClickListener;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class MoveActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    ListView lista;
    RecyclerView recyclerView;
    private SearchView mSearchView;
    List<Move> lista1,listadata;
    Context context ;
    MaterialPaletteAdapter adapter;
    private SQLiteHelper sqLiteHelper;
    Listviewadatper lis ;
    Bundle bundle;
    TextView txtnombre;
    int request_code = 1;
    Dialog customDialog = null;
    int valor;
    HttpTransportSE transporte;
    SwipeRefreshLayout mRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listtraslado); // Initializing Toolbar and setting it as the actionbar
        ListView list = (ListView) findViewById(android.R.id.list);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        //VERTICAL
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //HORIZONTAL
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
      //  recyclerView.addItemDecoration(new DividerItemDecoration(this));
        sqLiteHelper = new SQLiteHelper(this);
        bundle = getIntent().getExtras();
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshMove);
        mRefresh.setColorSchemeResources(R.color.AccentColor);
        valor = bundle.getInt("menu");
        customDialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.layout);

        context = this;


        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefresh.setRefreshing(true);
                lanzarhilo();
            }

        });

        lanzarprogrs();
        lanzarhilo();

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerLayout = navigationView.getHeaderView(0);
        txtnombre = (TextView) headerLayout.findViewById(R.id.username);
        txtnombre.setText(bundle.getString("usuario"));
        ContentFragment fragment = new ContentFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // fragmentTransaction.replace(R.id.frame,fragment);
        //  fragmentTransaction.commit();
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu


        // Initializing Drawer Layout and ActionBarToggle
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

    public static boolean isStringNumeric(String str )
    {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if ( !Character.isDigit( str.charAt( 0 ) ) && str.charAt( 0 ) != localeMinusSign ) return false;

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for ( char c : str.substring( 1 ).toCharArray() )
        {
            if ( !Character.isDigit( c ) )
            {
                if ( c == localeDecimalSeparator && !isDecimalSeparatorFound )
                {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    public void lanzarhilo(){
        TareaWSInsercion1 tarea = new TareaWSInsercion1();
        tarea.execute();
    }
    public void lanzarprogrs(){

        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Buscando informacion");

        TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
        contenido.setText("Espere mientras las tareas se cargan");

        customDialog.show();


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


    public boolean onQueryTextChange(String newText) {
        final List<Move> filteredModelList = filter(lista1, newText);
        adapter.setFilter(filteredModelList);

        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        Snackbar.make(findViewById(R.id.drawer), "Query: " + query, Snackbar.LENGTH_LONG)
                .show();
        return false;
    }

    private List<Move> filter(List<Move> models, String query) {
        query = query.toLowerCase();

        final List<Move> filteredModelList = new ArrayList<>();
        for (Move model : models) {
            final String text = model.getDescripcion().toLowerCase();
            final String text1 = model.getArticulo().toLowerCase();
            final String text2 = model.getUbicacionO().toLowerCase();
            final String text3 =  model.getNo_Tarea().toLowerCase();
            final String text4 = model.getCod_cip().toLowerCase();
            if (text.contains(query) ||text1.contains(query) || text2.contains(query) || text3.contains(query) || text4.contains(query)) {
                filteredModelList.add(model);
            }
        }
        listadata = filteredModelList;
        return filteredModelList;
    }
    public void rellenarlista(){
        listadata= lista1;
        adapter = new  MaterialPaletteAdapter(lista1, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(MoveActivity.this, DetailProductActivity.class);
                intent.putExtra("Conteo", listadata.get(position).getSeguridad());
                intent.putExtra("menu",valor);
                intent.putExtra("cip",listadata.get(position).getCod_cip());
                startActivity(intent);
                finish();
                //Toast.makeText(MoveActivity.this, "selecciono la tarea numero: "+listadata.get(position).getNo_Tarea() ,Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == request_code) && (resultCode == RESULT_OK)){
            bundle = getIntent().getExtras();
           // sqLiteHelper = new SQLiteHelper(this);
        }
    }

    private class TareaWSInsercion1 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {


            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL="http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "TareasPendientes";
            final String SOAP_ACTION = "http://10.1.1.18/TareasPendientes";
            String pureba ;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("strUsuario", bundle.getString("usuario"));
            // request.addProperty("strPassword",getcontrasenas.trim());

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            boolean ret = false;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            String prueba;
             transporte = new HttpTransportSE(URL,2000);


            try
            {
                sqLiteHelper.borrar();
                transporte.call(SOAP_ACTION  ,envelope);

                // boolean retornar = (Boolean) envelope.getResponse();
                SoapObject obj1 = (SoapObject) envelope.getResponse();
                String ubicacion,descrip, articulo;
                String[] lis = new String[obj1.getPropertyCount()];
                lista1 = new ArrayList<Move>();
                if (lis.length != 0) {
                    for (int i = 0; i < lis.length; i++) {
                        Move move = new Move();
                        SoapObject od = (SoapObject) obj1.getProperty(i);
                        if(valor == 1){
                            if(od.getProperty(2).toString().equals("PISO"))
                                guardar(od);
                        }
                        else if(valor == 2){
                                if(!od.getProperty(2).toString().equals("PISO"))
                                      guardar(od);
                            }
                    }

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
            if(isStringNumeric(od.getProperty(2).toString().substring(0,1)))
                move.setUbicacionO(od.getProperty(2).toString().substring(0,2)+" - "+od.getProperty(2).toString().substring(2,5)+" - "+od.getProperty(2).toString().substring(5,6)+" - "+od.getProperty(2).toString().substring(6));
            else
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
            move.setCod_cip(od.getProperty(14).toString());
            move.setUsuario(bundle.getString("usuario"));
            sqLiteHelper.InsertTraslado(move);
            lista1.add(move);
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
            // progressBar.setVisibility(View.INVISIBLE);
            customDialog.dismiss();
            mRefresh.setRefreshing(false);
            if(valor==1){
                getSupportActionBar().setTitle("Reubicacion Piso");
                navigationView.getMenu().findItem(R.id.inbox).setVisible(false);
                navigationView.getMenu().findItem(R.id.starred).setVisible(true);
            }
            else if(valor==2){
                getSupportActionBar().setTitle("Tarea de traslado");
                navigationView.getMenu().findItem(R.id.starred).setVisible(false);
                navigationView.getMenu().findItem(R.id.inbox).setVisible(true);
            }
            if(result == 0){
                    rellenarlista();

            }

            else if(result ==1 )
                Toast.makeText(MoveActivity.this, "No hay datos que mostrar" ,Toast.LENGTH_LONG).show();
            else if(result == 3)
                Toast.makeText(MoveActivity.this, "Problema de conexion" ,Toast.LENGTH_LONG).show();


        }


    }

}


