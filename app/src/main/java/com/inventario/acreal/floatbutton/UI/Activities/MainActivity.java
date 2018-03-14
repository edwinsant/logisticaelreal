package com.inventario.acreal.floatbutton.UI.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.inventario.acreal.floatbutton.Models.User;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.Utils.BaseAdapter;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;

import io.fabric.sdk.android.Fabric;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btacceder;
    EditText usuario, contrasena;
    ProgressBar progressBar;
    String getuser, getcontrasenas;
    SQLiteHelper db;
    User valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //integración Fabric
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(fabric);
        //-------
        setContentView(R.layout.act_login);
        btacceder = (Button)findViewById(R.id.bt_buscarDa);
        usuario = (EditText) findViewById(R.id.edt_user);
        contrasena = (EditText) findViewById(R.id.edt_passw);
        db = new SQLiteHelper(this);
        TareaWSInsercion2 tareaWSInsercion2 = new TareaWSInsercion2();
        tareaWSInsercion2.execute();
    }

    public void MostrarElement(){
        btacceder.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);
        contrasena.setVisibility(View.VISIBLE);
        btacceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getuser = usuario.getText().toString();
                getcontrasenas = contrasena.getText().toString();
                TareaWSInsercion1 tarea = new TareaWSInsercion1();
                tarea.execute("prueba");
            }
        });
    }
    private class TareaWSInsercion2 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {

            int resul = 0;
            valor = db.SelectActivoUsuairo();
            if(valor != null){
                resul =1;
            }
            else{
                resul = 2;
            }
            return resul;
        }
        @Override
        protected void onPreExecute(){
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values){
            // progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result==1)
            {
                if(valor.getActivo() == 1){
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("usuario", valor.getUsuario());
                    startActivity(intent);

                    finish();
                }
                else{

                    MostrarElement();
                }

            }
            else if(result == 2)
            {
                MostrarElement();
            }
            else
                Toast.makeText(MainActivity.this, "Verifique la Conexión" ,Toast.LENGTH_LONG).show();
        }
    }

    private class TareaWSInsercion1 extends AsyncTask<String,Integer,Integer> {
        int progress;
        @Override
        protected Integer doInBackground(String... params) {

            //Verifica el usuario
            int resul = 0;
          //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL="http://10.1.1.18:9090/ServicioWebSoap/wsAutenticacionUsuario.asmx";
            final String METHOD_NAME = "AutenticarUsuario";
            final String SOAP_ACTION = "http://10.1.1.18/AutenticarUsuario";
            String pureba ;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("strUsuario", getuser.trim());
            request.addProperty("strPassword",getcontrasenas.trim());
            Object obj1,obj2;

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            boolean ret = false;

            envelope.setOutputSoapObject(request);
            String prueba;
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try
            {
                transporte.call(SOAP_ACTION  ,envelope);

               // boolean retornar = (Boolean) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                prueba = response.toString();
                Boolean status = Boolean.valueOf(response.toString());
                if(status){
                    db.Insersuario(getuser.trim(),1);
                    resul = 1;
                }

                else
                    resul = 2;

              /*  String[] lis = new String[obj1.];
                if (lis.length != 0) {
                    for (int i = 0; i < lis.length; i++) {
                        SoapObject od = (SoapObject) obj1.getProperty(i);
                        ret = Boolean.valueOf(od.getProperty(0).toString());

                    }

                }
                else
                    resul = 2;*/
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
           progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values){
           // progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.INVISIBLE);
            if (result==1)
            {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("usuario", getuser.trim());
                startActivity(intent);
                finish();
            }
            else if(result == 2)
            {
                Toast.makeText(MainActivity.this, R.string.user_password_incorrect,Toast.LENGTH_LONG).show();
                usuario.setText("");
                contrasena.setText("");
                usuario.requestFocus();
            }
            else
                Toast.makeText(MainActivity.this, "Verifique la Conexión" ,Toast.LENGTH_LONG).show();
        }
    }
}
