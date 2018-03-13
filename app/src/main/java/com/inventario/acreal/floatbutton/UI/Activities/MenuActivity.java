package com.inventario.acreal.floatbutton.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.inventario.acreal.floatbutton.Models.Menu;
import com.inventario.acreal.floatbutton.UI.Adapters.menuAdapter;
import com.inventario.acreal.floatbutton.UI.Fragments.ContentFragment;
import com.inventario.acreal.floatbutton.UI.Adapters.Listviewadatper;
import com.inventario.acreal.floatbutton.UI.Adapters.MaterialPaletteAdapter;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.OnItemClickListener;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;
import com.inventario.acreal.floatbutton.Models.listas;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MenuActivity extends AppCompatActivity implements OnItemClickListener<Menu> {
    private Toolbar toolbar;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    ListView lista;
    RecyclerView mRecycler;
    menuAdapter mAdapter;
    ArrayList<Menu> mMenuItems;
    GridLayoutManager manager;

    private SearchView mSearchView;
    SQLiteHelper db;
    TextView txtnombre;
    List<listas> lista1,listadata;
    Context context ;
    String id, usuario, nombre = "";
    MaterialPaletteAdapter adapter;
    Listviewadatper lis ;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option); // Initializing Toolbar and setting it as the actionbar
        ListView list = (ListView) findViewById(android.R.id.list);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        db = new SQLiteHelper(this);
        initView();


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        ContentFragment fragment = new ContentFragment();
        View headerLayout = navigationView.getHeaderView(0);
        txtnombre = (TextView) headerLayout.findViewById(R.id.username) ;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       // fragmentTransaction.replace(R.id.frame,fragment);
      //  fragmentTransaction.commit();
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                Intent intent = null;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){

                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        intent = new Intent(MenuActivity.this, MoveActivity.class);
                        intent.putExtra("usuario",bundle.getString("usuario") );
                        intent.putExtra("menu", 1);
                        startActivity(intent);
                        finish();

                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.starred:
                        intent = new Intent(MenuActivity.this, MoveActivity.class);
                        intent.putExtra("usuario",bundle.getString("usuario") );
                        intent.putExtra("menu", 2);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Intent intentPicking = new Intent(MenuActivity.this, PickingActivity.class);
                        intentPicking.putExtra("usuario", bundle.getString("usuario"));
                        startActivity(intentPicking);
                        return true;

                    case R.id.trash:
                        db.borrarusuario(bundle.getString("usuario"));
                        Intent intent1 = new Intent(MenuActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        return true;
                    case R.id.version:
                        Toast.makeText(getApplicationContext(), navigationView.getMenu().findItem(R.id.version).getTitle(),Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        TareaWSInsercion1 tarea = new TareaWSInsercion1();
        tarea.execute("prueba");
    }

    private List<Menu> getMenuItems(){
        mMenuItems = new ArrayList<>();

        mMenuItems.add(new Menu(1,"Reubicación de piso",R.drawable.piso));
        mMenuItems.add(new Menu(2,"Confirmación de traslado",R.drawable.traslado));
        mMenuItems.add(new Menu(3,"Picking",R.drawable.ubicacion));
        mMenuItems.add(new Menu(4,"Recepción de devoluciones",R.drawable.producto));
        mMenuItems.add(new Menu(5,"Inventario",R.drawable.inventario));

        return mMenuItems;
    }

    private void initView() {
        getMenuItems();
        mRecycler = (RecyclerView) findViewById(R.id.rv_main_menu);
        manager = new GridLayoutManager(this, 2);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(manager);
        mAdapter = new menuAdapter(getApplicationContext(),mMenuItems,this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, Menu item) {
        switch (item.getId()){
            case 1:
                Intent  intentRelocation = new Intent(MenuActivity.this, MoveActivity.class);
                intentRelocation.putExtra("usuario",bundle.getString("usuario") );
                intentRelocation.putExtra("menu", 1);
                startActivity(intentRelocation);
                break;
            case 2:
                Intent intentMove = new Intent(MenuActivity.this, MoveActivity.class);
                intentMove.putExtra("usuario",bundle.getString("usuario") );
                intentMove.putExtra("menu", 2);
                startActivity(intentMove);
                break;
            case 3:
                Intent intentPicking = new Intent(MenuActivity.this, PickingActivity.class);
                intentPicking.putExtra("usuario",bundle.getString("usuario") );
                startActivity(intentPicking);
                break;
            case 4:
                Intent intentReturns = new Intent(MenuActivity.this,  testingAct.class);
                intentReturns.putExtra("usuario",bundle.getString("usuario") );
                startActivity(intentReturns);
                break;
            case 5:
                Intent intentInventory = new Intent(MenuActivity.this, InventoryActivity.class);
                intentInventory.putExtra("usuario",bundle.getString("usuario") );
                startActivity(intentInventory);
                break;
        }
    }

    private class TareaWSInsercion1 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {

            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL="http://10.1.1.18:9090/ServicioWebSoap/wsSeguridad.asmx";
            final String METHOD_NAME = "BuscarUsuarioLst";
            final String SOAP_ACTION = "http://10.1.1.18/BuscarUsuarioLst";
            String pureba ;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
         /*   request.addProperty("strUsuario", getuser.trim());
            request.addProperty("strPassword",getcontrasenas.trim());*/
            request.addProperty("strUsuario", bundle.getString("usuario"));

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);
            String prueba;
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION  ,envelope);

                // boolean retornar = (Boolean) envelope.getResponse();
                SoapObject obj1 = (SoapObject) envelope.getResponse();

                SoapObject od;
                String[] lis = new String[obj1.getPropertyCount()];

                if (lis.length != 0) {
                    for (int i = 0; i < lis.length; i++) {
                        od = (SoapObject) obj1.getProperty(i);
                        id = od.getProperty(0).toString();
                        usuario = od.getProperty(1).toString();
                        nombre = od.getProperty(2).toString();
                    }

                }
                else
                    resul = 2;
            }
            catch (IOException e) {
                resul = 3;
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                resul = 3;
                e.printStackTrace();
            }

            return resul;
        }
        @Override
        protected void onPreExecute(){
            //  progressBar = (ProgressBar)findViewById(R.id.progressBar);
            // progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            // progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Integer result) {
            //  progressBar.setVisibility(View.INVISIBLE);
           txtnombre.setText(bundle.getString("usuario"));
        }
    }
}

