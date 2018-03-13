package com.inventario.acreal.floatbutton.UI.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DecimalFormatSymbols;

public class DetailProductActivity extends AppCompatActivity {
    SQLiteHelper db;
    Move move;
    TextView editcodigo, txtLote2, txtArticulo, fechavencimiento, Descripcion, ump, cantidadUMP, umt, cantidadUMt, UbicacionD, Seguridad, Ntarea;
    Button Enviar;
    EditText codigoSeguridad;
    Bundle bundle;
    String address, IMEI = "";
    HttpTransportSE transporte;
    Dialog customDialog = null;
    String pasar;
    int valor;
    Toolbar mToolbar;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;

    private TelephonyManager mTelephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_move);
        mToolbar = (Toolbar)  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editcodigo = (TextView) findViewById(R.id.tv_origin_name);
        txtLote2 = (TextView) findViewById(R.id.tv_lote_name);
        fechavencimiento = (TextView) findViewById(R.id.tv_expiration_date);
        ump = (TextView) findViewById(R.id.tv_ump);
        cantidadUMP = (TextView) findViewById(R.id.tv_units_number);
        umt = (TextView) findViewById(R.id.tv_umt);
        cantidadUMt = (TextView) findViewById(R.id.tv_count_number);
        Enviar = (Button) findViewById(R.id.btn_send_confirmation_key);
        UbicacionD = (TextView) findViewById(R.id.tv_destiny_name);
        Seguridad = (TextView) findViewById(R.id.tv_security);
        Descripcion = (TextView) findViewById(R.id.tv_name_product);
        txtArticulo = (TextView) findViewById(R.id.tv_id_product);
        Ntarea = (TextView) findViewById(R.id.tv_task_number);
        bundle = getIntent().getExtras();
        pasar = (bundle.getString("Conteo"));
        valor = bundle.getInt("menu");

        getSupportActionBar().setTitle("Pallet - "+ bundle.getString("cip"));
        db = new SQLiteHelper(this);
        move = db.Selectporcodigo(pasar);

        if (move == null) {
            return;
        }

        txtArticulo.setText(move.getArticulo() + " - ");


        editcodigo.setText(move.getUbicacionO());
        txtLote2.setText(move.getLote());
        fechavencimiento.setText(move.getFechavencimiento());
        Descripcion.setText(move.getDescripcion());
        ump.setText(move.getUMPallet());
        cantidadUMP.setText(move.getCantidad_UMP());
        umt.setText(move.getUMT());
        cantidadUMt.setText(move.getCantidad_UMT());
        Ntarea.setText("Tarea # " + move.getNo_Tarea());
        // UbicacionD.setText(move.getUbicacionD());
        if (isStringNumeric(move.getUbicacionD().substring(0, 1)))
            UbicacionD.setText(move.getUbicacionD().substring(0, 2) + " - " + move.getUbicacionD().substring(2, 5) + " - " + move.getUbicacionD().substring(5, 6) + " - " + move.getUbicacionD().substring(6));
        else
            UbicacionD.setText(move.getUbicacionD());
        Seguridad.setText(move.getSeguridad());
        getIMEI();

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IMEI.equals("")) {
                    codigoSeguridad = (EditText) findViewById(R.id.et_security_key);
                    String seguirdad = codigoSeguridad.getText().toString();
                    String seguridad1 = move.getSeguridad().trim();
                    if (seguirdad.trim().equals(seguridad1)) {

                        showDialog();

                    } else
                        lanzartoast(4);
                } else {
                    Toast.makeText(DetailProductActivity.this, "Tiene que habilitar el permisos para leer y realizar llamadas ", Toast.LENGTH_LONG).show();
                    getIMEI();
                }

                //Toast.makeText(DetailProductActivity.this, "Ingrese correctamente el codigo de seguridad", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showDialog(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("¿Desea realmente cambiar de ubicación?");
        alertDialog.setTitle("Cambiar ubicación");
        alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TareaWSInsercion1 tarea = new TareaWSInsercion1();
                   tarea.execute();
    }
});
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void lanzartoast(int valor) {

        Toast toast3 = new Toast(getApplicationContext());
        int resour = 0;
        String mensaje = "";

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView txtMsg = (TextView) layout.findViewById(R.id.txtMensaje);
        ImageView imgIcono = (ImageView) layout.findViewById(R.id.imgIcono);
        toast3.setView(layout);

        if (valor == 0) {
            //resour = R.mipmap.ic_shortcut_seguridad;
            toast3.getView().setBackgroundColor(getResources().getColor(R.color.red));
            mensaje = "Transaccion incorrecta";
        } else if (valor == 1) {
            toast3.getView().setBackgroundColor(getResources().getColor(R.color.green));
            mensaje = "Tarea aplicada correctamente";
          //  resour = R.mipmap.ic_shortcut_aplicada;
        } else if (valor == 2) {
            mensaje = "La tarea fue eliminada previamente";
            resour = R.mipmap.ic_shortcut_eliminada;
        }
        if (valor == 4) {
           // resour = R.mipmap.ic_shortcut_error;
            toast3.getView().setBackgroundColor(getResources().getColor(R.color.yellow));
            mensaje = "Ingrese correctamente el codigo de seguridad";
        }

        // Drawable nuevo
        imgIcono.setImageResource(resour);
        txtMsg.setText(mensaje);
        toast3.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.show();
    }

    public static boolean isStringNumeric(String str) {
        DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
        char localeMinusSign = currentLocaleSymbols.getMinusSign();

        if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) return false;

        boolean isDecimalSeparatorFound = false;
        char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

        for (char c : str.substring(1).toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
                    isDecimalSeparatorFound = true;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }
    }

    private void getDeviceImei() {

        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String deviceid = mTelephonyManager.getDeviceId();
       // Log.d("msg", "DeviceImei " + deviceid);
    }
    private  void getIMEI() {
      //  @SuppressLint("WifiManagerLeak") WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //WifiInfo info = manager.getConnectionInfo();
        //address = info.getMacAddress();
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE );
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE }, 225);
        } else {
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            IMEI =   telephonyManager.getDeviceId();
            Log.i("Mensaje", "Se tiene permiso!");
        }
       /* TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        IMEI =   telephonyManager.getDeviceId();
        String ime = IMEI;
        //return address + " Numero IMEI: " +telephonyManager.getDeviceId();*/
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void Enviar()
    {
        Intent intent = new Intent(DetailProductActivity.this, MoveActivity.class);
        intent.putExtra("usuario", move.getUsuario() );
        intent.putExtra("menu",valor);
        startActivity(intent);
        finish();
    }

    public boolean onSupportNavigateUp() {

        Intent intent = new Intent(this,MoveActivity.class);
        intent.putExtra("menu",valor);
        intent.putExtra("usuario",move.getUsuario());
        startActivity(intent);
        finish();
        return true;
    }

    private class TareaWSInsercion1 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {


            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL="http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "InsertarReubicacion";
            final String SOAP_ACTION = "http://10.1.1.18/InsertarReubicacion";
            String pureba ;
            String codigo,ubicacionD;
            codigo = move.getUbicacionO();
            codigo = codigo.replace(" ","");
            codigo = codigo.replace("-","");
            ubicacionD = move.getUbicacionD().trim();
            ubicacionD = ubicacionD.replace(" ","");
            ubicacionD = ubicacionD.replace("-","");
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("UMT", move.getUMT().trim());
            request.addProperty("CANTIDAD", move.getCantidad_UMT().trim());
            request.addProperty("UBICACION_ORIGEN", codigo.trim());
            request.addProperty("UBICACION_DESTINO", ubicacionD);
            request.addProperty("LOTE", move.getLote().trim());
            request.addProperty("ARTICULO", move.getArticulo().trim());
            request.addProperty("BODEGA", move.getBodega().trim());
            request.addProperty("USUARIO", move.getUsuario().trim());
            request.addProperty("IMEI", IMEI);
            request.addProperty("SEGURIDAD", move.getSeguridad().trim());
            request.addProperty("TAREA", move.getNo_Tarea());
            request.addProperty("COD_CIP", bundle.getString("cip"));

            // request.addProperty("strPassword",getcontrasenas.trim());

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            boolean ret = false;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            int valor;
            transporte = new HttpTransportSE(URL,2000);
            try
            {
                transporte.call(SOAP_ACTION  ,envelope);
                Object response;
                // boolean retornar = (Boolean) envelope.getResponse();
                 response =  envelope.getResponse();


                valor = Integer.valueOf(response.toString());
                if (valor == 1)
                    resul = valor;
                else if(valor == 2)
                    resul = valor;
                else
                    resul = 0;
            }
            catch (Exception e) {
                resul = 3;
                String retornar;
                retornar = e.toString();
                //  e.printStackTrace();
            }

            return resul;
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
            if(result == 0)
                lanzartoast(result);//Toast.makeText(DetailProductActivity.this, "Transaccion incorrecta" ,Toast.LENGTH_LONG).show();
            else if(result ==1){
                lanzartoast(result);//Toast.makeText(DetailProductActivity.this,"Tarea aplicada correctamente", Toast.LENGTH_SHORT).show();
                Enviar();
            }
            else if(result ==2){
                lanzartoast(result);//Toast.makeText(DetailProductActivity.this, "La terea fue eliminada previamente" ,Toast.LENGTH_LONG).show();
                Enviar();
            }
            else
                 Toast.makeText(DetailProductActivity.this, "Problema de conexion" ,Toast.LENGTH_LONG).show();
        }


    }
}
