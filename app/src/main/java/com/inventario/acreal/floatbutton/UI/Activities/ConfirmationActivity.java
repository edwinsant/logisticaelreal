package com.inventario.acreal.floatbutton.UI.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.R;
import com.inventario.acreal.floatbutton.UI.Adapters.StateAdapter;
import com.inventario.acreal.floatbutton.Utils.SQLiteHelper;
import com.inventario.acreal.floatbutton.Utils.ShowDialog;
import com.inventario.acreal.floatbutton.Utils.Tabla;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationActivity extends AppCompatActivity {
    TextView BODEGA, FECHA, TRANSPORTISTA, CODIGO_CORTO, ARTICULO, DESCRIPCION, NLINEA, LINEA, UBICACION, LOTE, UMT, UMS;
    TextView NCARGA, NPEDIDOS, COD_VERIFICACION, SECUENCIA, CANT_TOTAL_UMP, CANT_TOTAL_UMS, FACTOR, CANT_UMS, UNIDAD_SUELTAS, DESPACHAR, Nexistencia;
    Bundle bundle;
    String secuencia, ubicac, lote, existencia, disponibles, COMP_FIRME_ORD_VTA, COMP_FLEX_OV_OT, COMP_FIRME_OT;
    Picking picking;
    Dialog customDialog = null;
    Dialog customDialog1 = null;
    Dialog dialogcancelacion = null;
    ImageView imglote;
    String despachar;
    HttpTransportSE transporte;
    List<Picking> lista1, listadata;
    StateAdapter adapter;
    TableLayout tableLayout;
    Tabla tabla;
    Button Enviar;
    int cambio_ubicacion;
    EditText editdespachar;
    EditText editseguridad;
    ImageView ivBox, ivUnit;
    TextView tvReqBox, tvReqUnit, tvBoxMes, tvTotalUnits;
    private SQLiteHelper sqLiteHelper;
    private Resources rs;
    private int FILAS, COLUMNAS;
    private Toolbar toolbar;
    Boolean editcantidad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_confirm_product);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Enviar = (Button) findViewById(R.id.btn_confirmation_data);
        tvReqBox = (TextView) findViewById(R.id.tv_boxs_required_count);
        tvBoxMes = (TextView) findViewById(R.id.tv_ump_required);
        tvReqUnit = (TextView) findViewById(R.id.tv_units_required_count);
        tvTotalUnits = (TextView) findViewById(R.id.tv_count_units_dispatch);

        SECUENCIA = (TextView) findViewById(R.id.tv_secuency_name);
        UBICACION = (TextView) findViewById(R.id.tv_origin_name);
        LOTE = (TextView) findViewById(R.id.tv_lote_name);
        CANT_TOTAL_UMP = (TextView) findViewById(R.id.tv_required_count);
        UMT = (TextView) findViewById(R.id.tv_umt);
        CANT_TOTAL_UMS = (TextView) findViewById(R.id.tv_boxs_count);
        UMS = (TextView) findViewById(R.id.tv_ump);
        UNIDAD_SUELTAS = (TextView) findViewById(R.id.tv_units_count);
        FACTOR = (TextView) findViewById(R.id.tv_factor_count);
        // editdespachar = (EditText)findViewById(R.id.editdespachar);
        editseguridad = (EditText) findViewById(R.id.et_security_key);
        Nexistencia = (TextView) findViewById(R.id.tv_existence_count);
        ivBox = (ImageView) findViewById(R.id.iv_edit_boxs);
        ivUnit = (ImageView) findViewById(R.id.iv_edit_units);

        imglote = (ImageView) findViewById(R.id.imglote);
        sqLiteHelper = new SQLiteHelper(this);
        bundle = getIntent().getExtras();
        rs = ConfirmationActivity.this.getResources();
        String valor = Integer.toString(bundle.getInt("Id"));
        picking = sqLiteHelper.SelectLineaArticulo(valor);

        getSupportActionBar().setTitle(picking.getARTICULO() + " - " + picking.getDESCRIPCION());

        SECUENCIA.setText(picking.getSECUENCIA());
        UBICACION.setText(picking.getUBICACION());
        LOTE.setText(picking.getLOTE());
        CANT_TOTAL_UMP.setText(picking.getCANT_TOTAL_UMP());
        UMT.setText(picking.getUMT());
        CANT_TOTAL_UMS.setText(picking.getCANT_TOTAL_UMS());
        tvReqBox.setText(picking.getCANT_TOTAL_UMS());
        UMS.setText(picking.getUMS());
        tvBoxMes.setText(picking.getUMS());
        UNIDAD_SUELTAS.setText(picking.getUNIDAD_SUELTAS());
        tvReqUnit.setText(picking.getUNIDAD_SUELTAS());
        FACTOR.setText(picking.getFACTOR());
        final String  totalUnit = String.valueOf(CalculateTotalUnits(Integer.parseInt(picking.getFACTOR()),
                Integer.parseInt(picking.getCANT_TOTAL_UMS()),
                Integer.parseInt(picking.getUNIDAD_SUELTAS())));

        //asignacion del valor de la funcion Total
        tvTotalUnits.setText(totalUnit);

        Nexistencia.setText(picking.getExistencia());
        customDialog = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.estado);


        //Confirmacion de las Unidades
        ivUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    ShowDialog.showAlertDialog(ConfirmationActivity.this, 2, UNIDAD_SUELTAS);
             /   String  totalUnit2;

                totalUnit2 = String.valueOf(CalculateTotalUnits(Integer.parseInt(picking.getFACTOR()),
                        Integer.parseInt(CANT_TOTAL_UMS.getText().toString()),
                        Integer.parseInt(UNIDAD_SUELTAS.getText().toString())));

                tvTotalUnits.setText(totalUnit2);*/
                ShowAlertDialog( 1, UNIDAD_SUELTAS);
            }
        });


        //Confirmacion de las Cajas
        ivBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialog( 1, CANT_TOTAL_UMS);

            }
        });


//Cambiar el lote a Despachar
        imglote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TareaWSInsercion1 tarea = new TareaWSInsercion1();
                tarea.execute();
            }
        });

        //Funciones de Boton Enviar
        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lote = LOTE.getText().toString();
                ubicac = UBICACION.getText().toString();
                //despachar =  editdespachar.getText().toString();
                //despachar = totalUnit.toString();
                despachar = tvTotalUnits.getText().toString();

               // Toast.makeText(ConfirmationActivity.this,despachar,Toast.LENGTH_LONG).show();

                Double despa, orden, exis, seguridad;
                boolean error = false;

                //  if(editdespachar == null){
                //    Toast.makeText(ConfirmationActivity.this, "In working", Toast.LENGTH_SHORT).show();
                //  return;
                // }


                if (TextUtils.isEmpty(despachar)) {
                    Toast.makeText(ConfirmationActivity.this, "Ingrese la cantidad a despachar", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                if (TextUtils.isEmpty(editseguridad.getText())) {
                    Toast.makeText(ConfirmationActivity.this, "Ingrese el valor de seguridad", Toast.LENGTH_SHORT).show();
                    error = true;
                }

                if (!error) {
                    despa = Double.valueOf(despachar); //Despachado
                    orden = Double.valueOf(picking.getCANT_TOTAL_UMP()); //Lo Solicitado
                    exis = Double.valueOf(Nexistencia.getText().toString()); //Existencias

                    // seguridad = Double.valueOf(editseguridad.getText().toString());

                    if (!lote.equals("")) {
                        if (exis < despa) {
                            Toast.makeText(ConfirmationActivity.this, "La cantidad despachada tiene que ser menor que la existencia fisica", Toast.LENGTH_LONG).show();
                        } else if (exis <= 0)
                            Toast.makeText(ConfirmationActivity.this, "Los despachado  tiene que ser menor que la existencias", Toast.LENGTH_LONG).show();
                        else {
                            if (despa > orden)
                                Toast.makeText(ConfirmationActivity.this, "La cantidad despachada debe ser igual o menor que la cantidad  solicitada", Toast.LENGTH_LONG).show();
                            else if (despa <= 0)
                                Toast.makeText(ConfirmationActivity.this, "La cantidad despachada debe ser mayor a 0", Toast.LENGTH_LONG).show();
                            else {
                                if (picking.getCOD_VERIFICACION().equals(editseguridad.getText().toString()))
                                    Dialogo("Confirmar ProductActivity", "Desea realmente confirmar articulo");
                                else
                                    Toast.makeText(ConfirmationActivity.this, "El codigo de seguridad es incorrecto", Toast.LENGTH_LONG).show();
                            }
                        }

                    } else
                        Toast.makeText(ConfirmationActivity.this, "Este articulos no tiene lote. Seleccione un Lote disponible", Toast.LENGTH_LONG).show();
                }
            }
        });

    }




    public  void ShowAlertDialog( final int value, final TextView control) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater() ;
        final View view = inflater.inflate(R.layout.confirm_counts_dialog, null);
        final EditText mCount = (EditText) view.findViewById(R.id.et_count);


        //Confirmar la Cantidad
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String totalUnit2;
                if(notEmpty(mCount.getText().toString())){
                    int count = Integer.parseInt(mCount.getText().toString());
                    //Valor en la Text View
                    control.setText(String.valueOf(count)) ;

                    //Recalculo de la Funcion
                    totalUnit2 = String.valueOf(CalculateTotalUnits(Integer.parseInt(picking.getFACTOR()),
                            Integer.parseInt(CANT_TOTAL_UMS.getText().toString()),
                            Integer.parseInt(UNIDAD_SUELTAS.getText().toString())));

                    tvTotalUnits.setText(totalUnit2);
                    despachar = tvTotalUnits.toString();
                }

                //  control.setText("123");
                dialog.dismiss();
            }
        });

        //Confirmar
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(value == 1){
            builder.setTitle(this.getResources().getString(R.string.change_count_boxes));
        }else{
            builder.setTitle(this.getResources().getString(R.string.change_count_unit));
        }

        builder.setView(view);
        builder.create();
        final AlertDialog dialog = builder.show();

        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
        TextView tv = (TextView) dialog.findViewById(textViewId);
        tv.setTextColor(this.getResources().getColor(R.color.negro));

        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
        View divider = dialog.findViewById(dividerId);
        if (divider != null){
            divider.setBackgroundColor(this.getResources().getColor(R.color.colorAccent));
        }
    }

    public  boolean notEmpty(String text) {
        return (text != null && text.length() > 0);
    }


    public  int CalculateTotalUnits(int factor, int boxes, int units) {

        int total = 0;

        if (factor > 0 && boxes > 0) {
            total = (factor * boxes) + units;
        } else if (boxes == 0) {
            total = units;
        }

        return total;
    }


    //Mensaje Dialog para ConfirmationActivity de Articulos
    public void Dialogo(String Titulo, String Contenido) {
        customDialog1 = new Dialog(this, R.style.Theme_Dialog_Translucent);
        //deshabilitamos el título por defecto
        customDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog1.setCancelable(false);

        //establecemos el contenido de nuestro dialog
        customDialog1.setContentView(R.layout.dialog);

        TextView titulo = (TextView) customDialog1.findViewById(R.id.titulo);

        titulo.setText(Titulo);

        TextView contenido = (TextView) customDialog1.findViewById(R.id.contenido);

        contenido.setText(Contenido);

        customDialog1.show();

        ((Button) customDialog1.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog1.dismiss();
                TareaWSInsercion2 tarea = new TareaWSInsercion2();
                tarea.execute();

            }
        });

        ((Button) customDialog1.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                customDialog1.dismiss();
                Toast.makeText(ConfirmationActivity.this, "Usted no Confirmo el articulo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Mensaje Modal de confirmacion de Cancelacion
    public void dialogcancelaciontotal(String Titulo) {

        //Tema Transparente
        dialogcancelacion = new Dialog(this, R.style.Theme_Dialog_Translucent);

        //Obliga al usuario a usar los botones Cancelar y Aceptar
        dialogcancelacion.setCancelable(false);

        //Asigna el Layout de Seguridad para la cancelacion
        dialogcancelacion.setContentView(R.layout.dialogseguridad);

        //Asigna el titulo
        TextView titulo = (TextView) dialogcancelacion.findViewById(R.id.titulo);
        titulo.setText(Titulo);

        dialogcancelacion.show();

        ((Button) dialogcancelacion.findViewById(R.id.aceptar)).setOnClickListener(new View.OnClickListener() {
            EditText txtcancelseguridad;

            @Override
            public void onClick(View view) {
                boolean error = false;

                //Salvacion = Asignacion del DialogCancelacion
                txtcancelseguridad = (EditText) dialogcancelacion.findViewById(R.id.txtseguridad2);

                if (TextUtils.isEmpty(txtcancelseguridad.getText())) {
                    Toast.makeText(ConfirmationActivity.this, "Ingrese El codigo de seguridad", Toast.LENGTH_LONG).show();
                    error = true;
                } else {
                    if (picking.getCOD_VERIFICACION().equals(txtcancelseguridad.getText().toString())) {
                        TareaWSInsercion3 tarea = new TareaWSInsercion3();
                        tarea.execute();
                    } else
                        Toast.makeText(ConfirmationActivity.this, "El codigo de seguridad es incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });

        ((Button) dialogcancelacion.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcancelacion.dismiss();
                Toast.makeText(ConfirmationActivity.this, "Usted no Confirmo el articulo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Funcion del Boton Enviar
    public void Enviar() {

        Intent intent = new Intent(ConfirmationActivity.this, ProductActivity.class);
        intent.putExtra("NLINEA", bundle.getString("NLINEA"));
        intent.putExtra("NCARGA", bundle.getString("NCARGA"));
        intent.putExtra("usuario", bundle.getString("usuario"));
        startActivity(intent);
        finish();

    }

    //Creacion del Menu de la pantalla
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmacion, menu);
        return true;
    }

    //Seleccionar el Boton Cancelar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cancel_action:
                dialogcancelaciontotal("Seguridad");
                //   dialogcancelacion.show();
                // TareaWSInsercion3 tarea = new TareaWSInsercion3();
                // tarea.execute();
                //metodoSearch()
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ConfirmationActivity.this, ProductActivity.class);
        intent.putExtra("NLINEA", bundle.getString("NLINEA"));
        intent.putExtra("NCARGA", bundle.getString("NCARGA"));
        intent.putExtra("BODEGA", bundle.getString("BODEGA"));
        intent.putExtra("usuario", bundle.getString("usuario"));
        intent.putExtra("LINEA", bundle.getString("LINEA"));
        startActivity(intent);
        finish();

        // do something on back.
        return false;

    }



    //Manejo de Mensajes
    public void lanzartoast(int valor) {
        Toast toast3 = new Toast(getApplicationContext());
        int resour = 0;
        String mensaje = "";

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView txtMsg = (TextView) layout.findViewById(R.id.txtMensaje);
        ImageView imgIcono = (ImageView) layout.findViewById(R.id.imgIcono);

        if (valor == 0) {
            resour = R.mipmap.ic_shortcut_seguridad;
            mensaje = "ConfirmationActivity incorrecta";
        } else if (valor == 1) {
            mensaje = "ConfirmationActivity aplicada correctamente";
            resour = R.mipmap.ic_shortcut_aplicada;
        } else if (valor == 2) {
            resour = R.mipmap.ic_shortcut_error;
            mensaje = "Problema con el servidor";
        } else if (valor == 4) {
            mensaje = "Cancelacion correctamente";
            resour = R.mipmap.ic_shortcut_aplicada;
        }
        if (valor == 5) {
            resour = R.mipmap.ic_shortcut_seguridad;
            mensaje = "Cancelacion incorrecta";
        }

        // Drawable nuevo
        imgIcono.setImageResource(resour);
        txtMsg.setText(mensaje);
        toast3.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast3.setDuration(Toast.LENGTH_LONG);
        toast3.setView(layout);
        toast3.show();
    }


    //Consulta de Existencia
    public void lanzarprogrs() {
        TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
        titulo.setText("Consulta de existencia");
        lansarestado();
        //final EditText ipwebse = (EditText)customDialog.findViewById(R.id.editbodega);
        //final EditText ipbd = (EditText)customDialog.findViewById(R.id.editProducto);
        ((Button) customDialog.findViewById(R.id.cancelar)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                tableLayout.removeAllViews();
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    //Accion al Regresar
    public void onBackPressed() {

        Intent intent = new Intent(ConfirmationActivity.this, ProductActivity.class);
        intent.putExtra("NLINEA", bundle.getString("NLINEA"));
        intent.putExtra("NCARGA", bundle.getString("NCARGA"));
        intent.putExtra("BODEGA", bundle.getString("BODEGA"));
        intent.putExtra("usuario", bundle.getString("usuario"));
        intent.putExtra("LINEA", bundle.getString("LINEA"));
        startActivity(intent);
        finish();

        // do something on back.
        return;
    }

    //LLena la tabla de nuevos lotes
    private void lansarestado() {
        TextView tsecuencia, txtubicacion, txtLote2, txtestado, txtfecha, txtexistencia, txtdisponibles, txtcompfirmeordvta, Comp_flex_ov_ot, Comp_firme_ot;
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) customDialog.findViewById(R.id.recyclerView1);
        tableLayout = (TableLayout) customDialog.findViewById(R.id.tabla);
        // tabla = new Tabla(this, tableLayout);
        agregarCabecera(R.array.cabecera_tabla);
        if (lista1 != null) {

            for (int i = 0; i < lista1.size(); i++) {
                ArrayList<String> elementos = new ArrayList<String>();

                elementos.add(lista1.get(i).getUBICACION());
                elementos.add(lista1.get(i).getLOTE());

                elementos.add(lista1.get(i).getExistencia());
                elementos.add(lista1.get(i).getDisponibles());
                elementos.add(lista1.get(i).getEstado());
                elementos.add(lista1.get(i).getFECHA());
                elementos.add(lista1.get(i).getSECUENCIA());

                elementos.add(lista1.get(i).getCOMP_FIRME_ORD_VTA());
                elementos.add(lista1.get(i).getCOMP_FLEX_OV_OT());
                elementos.add(lista1.get(i).getCOMP_FIRME_OT());
                agregarFilaTabla(elementos);
            }

        }
    }

    //BUSCAR UN NUEVO LOTE
    public void agregarCabecera(int recursocabecera) {
        TableRow.LayoutParams layoutCelda;
        TableRow fila = new TableRow(ConfirmationActivity.this);
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        fila.setLayoutParams(layoutFila);

        String[] arraycabecera = rs.getStringArray(recursocabecera);
        COLUMNAS = arraycabecera.length;

        for (int i = 0; i < arraycabecera.length; i++) {
            TextView texto = new TextView(ConfirmationActivity.this);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arraycabecera[i]), TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arraycabecera[i]);
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(ConfirmationActivity.this, R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda_cabecera);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        tableLayout.addView(fila);

        FILAS++;
    }


    //AGREGA FILAS A LA NUEVA TABLA
    public void agregarFilaTabla(ArrayList<String> elementos) {
        TableRow.LayoutParams layoutCelda = null;
        TableRow.LayoutParams layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(ConfirmationActivity.this);
        fila.setLayoutParams(layoutFila);
        final TextView button = new TextView(ConfirmationActivity.this);
        button.setText("Cambio");
        button.setId(FILAS);
        button.setGravity(Gravity.LEFT);
        button.setBackgroundResource(R.drawable.tabla_celda);
        button.setTextAppearance(ConfirmationActivity.this, R.style.estilo_celda);
        layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(button.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutCelda);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View row = (View) v.getParent();
                Double existencia;
                ViewGroup container = ((ViewGroup) row.getParent());
                TableRow tablerow = (TableRow) v.getParent();
                int i = ((ViewGroup) row.getParent()).getId();
                TextView items2 = (TextView) tablerow.getChildAt(2); //LOTE
                TextView items3 = (TextView) tablerow.getChildAt(3); // EXISTENCIA
                TextView items1 = (TextView) tablerow.getChildAt(1);  //UBICACION
                TextView items7 = (TextView) tablerow.getChildAt(7);
                existencia = Double.valueOf(items3.getText().toString());
                if (existencia <= 0) {
                    Toast.makeText(ConfirmationActivity.this, "Tiene que seleccionar una existencia mayor que 0", Toast.LENGTH_LONG).show();
                } else {
                    SECUENCIA.setText(items7.getText().toString());
                    int color = R.color.red;
                    SECUENCIA.setTextColor(getResources().getColor(color));
                    LOTE.setText(items2.getText().toString());
                    LOTE.setTextColor(getResources().getColor(color));
                    UBICACION.setText(items1.getText().toString());
                    UBICACION.setTextColor(getResources().getColor(color));
                    Nexistencia.setText(items3.getText().toString());
                    Nexistencia.setTextColor(getResources().getColor(color));
                    cambio_ubicacion = 1;
                    tableLayout.removeAllViews();
                    customDialog.dismiss();
                }

            }
        });
        fila.addView(button);
        for (int i = 0; i < elementos.size(); i++) {
            TextView texto = new TextView(ConfirmationActivity.this);
            texto.setText(String.valueOf(elementos.get(i)));
            texto.setGravity(Gravity.NO_GRAVITY);
            texto.setTextAppearance(ConfirmationActivity.this, R.style.estilo_celda);
            texto.setBackgroundResource(R.drawable.tabla_celda);
            layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(texto.getText().toString()), TableRow.LayoutParams.MATCH_PARENT);
            texto.setLayoutParams(layoutCelda);

            fila.addView(texto);
        }

        tableLayout.addView(fila);


        FILAS++;
    }

    //Obtener el Ancho Pixeles
    private int obtenerAnchoPixelesTexto(String texto) {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(50);
        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }

    //Consultar Existencias
    private class TareaWSInsercion1 extends AsyncTask<String, Integer, Integer> {
        int progress;

        @Override
        protected Integer doInBackground(String... params) {

            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL = "http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "ConsultaExistencia";
            final String SOAP_ACTION = "http://10.1.1.18/ConsultaExistencia";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("strBodega", picking.getBODEGA());
            request.addProperty("strProducto", picking.getARTICULO());
            request.addProperty("strUMTransaccion", picking.getUMT());

            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            transporte = new HttpTransportSE(URL);

            try {

                transporte.call(SOAP_ACTION, envelope);

                SoapObject obj1 = (SoapObject) envelope.getResponse();

                if (obj1.getPropertyCount() != 0) {
                    lista1 = new ArrayList<>();
                    for (int i = 0; i < obj1.getPropertyCount(); i++) {
                        Picking picking = new Picking();
                        SoapObject od = (SoapObject) obj1.getProperty(i);
                        picking.setSECUENCIA(od.getProperty(0).toString());
                        picking.setUBICACION(od.getProperty(1).toString());
                        picking.setLOTE(od.getProperty(2).toString());
                        picking.setEstado(od.getProperty(3).toString());
                        picking.setFECHA(od.getProperty(4).toString());
                        picking.setExistencia(od.getProperty(5).toString());
                        picking.setDisponibles(od.getProperty(6).toString());
                        picking.setCOMP_FIRME_ORD_VTA(od.getProperty(7).toString());
                        picking.setCOMP_FLEX_OV_OT(od.getProperty(8).toString());
                        picking.setCOMP_FIRME_OT(od.getProperty(9).toString());
                        lista1.add(picking);
                    }
                    resul = 0;

                } else
                    resul = 1;
            } catch (Exception e) {
                resul = 3;
                //prueba = e.toString();
                //  e.printStackTrace();
            }

            return resul;
        }

        public Move guardar(SoapObject od) {

            Move move = new Move();
            move.setNo_Tarea(String.valueOf(od.getProperty(0).toString()));
            move.setBodega(od.getProperty(1).toString());

            move.setUbicacionO(od.getProperty(2).toString().substring(0, 2) + " - " + od.getProperty(2).toString().substring(2, 5) + " - " + od.getProperty(2).toString().substring(5, 6) + " - " + od.getProperty(2).toString().substring(6));

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
        protected void onPreExecute() {
            // progressBar = (ProgressBar)findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);

            super.onPreExecute();
            //Recalcula las cajas y Unidades Sueltas

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            //  customDialog.dismiss();
            // progressBar.setVisibility(View.INVISIBLE);

            if (result == 0) {
                lanzarprogrs();
            } else if (result == 1)
                Toast.makeText(ConfirmationActivity.this, "Sin Existencias en la Bodega", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ConfirmationActivity.this, "Problema de conexion", Toast.LENGTH_LONG).show();

        }
    }


    //Confirmar el ProductActivity
    private class TareaWSInsercion2 extends AsyncTask<String, Integer, Integer> {
        int progress;

        @Override
        protected Integer doInBackground(String... params) {

            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL = "http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "ConfirmarArticulo";
            final String SOAP_ACTION = "http://10.1.1.18/ConfirmarArticulo ";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("NCARGA", picking.getNCARGA());
            request.addProperty("BODEGA", picking.getBODEGA());
            request.addProperty("ARTICULO", picking.getARTICULO());
            request.addProperty("CAMBIO_UBICACION", picking.getUBICACION());
            request.addProperty("UBICACION_V", picking.getUBICACION());
            request.addProperty("LOTE_V", picking.getLOTE());
            request.addProperty("UBICACION_N", ubicac);
            request.addProperty("LOTE_N", lote);
            request.addProperty("UMT", picking.getUMT());
            //     request.addProperty("CANTIDAD_ORD", picking.getCANT_TOTAL_UMP());
            request.addProperty("CANTIDAD_DESP", despachar);
            request.addProperty("USUARIO", bundle.getString("usuario"));
            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                int status = Integer.valueOf(response.toString());
                if (status == 1) {

                    resul = 1;
                } else
                    resul = 0;

            } catch (Exception e) {
                resul = 2;
                //prueba = e.toString();
                //  e.printStackTrace();
            }

            return resul;
        }

        public Move guardar(SoapObject od) {

            Move move = new Move();
            move.setNo_Tarea(String.valueOf(od.getProperty(0).toString()));
            move.setBodega(od.getProperty(1).toString());

            move.setUbicacionO(od.getProperty(2).toString().substring(0, 2) + " - " + od.getProperty(2).toString().substring(2, 5) + " - " + od.getProperty(2).toString().substring(5, 6) + " - " + od.getProperty(2).toString().substring(6));

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
        protected void onPreExecute() {
            // progressBar = (ProgressBar)findViewById(R.id.progressBar);
            //progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            //  customDialog.dismiss();
            // progressBar.setVisibility(View.INVISIBLE);

            lanzartoast(result);
            if (result == 1)
                onBackPressed();
        }
    }


    //Cancelar la LineActivity del ProductActivity
    private class TareaWSInsercion3 extends AsyncTask<String, Integer, Integer> {
        int progress;

        @Override
        protected Integer doInBackground(String... params) {

            int resul = 0;
            //  getmac();
            final String NAMESPACE = "http://10.1.1.18/";
            final String URL = "http://10.1.1.18:9090/ServicioWebSoap/wsDistribucion.asmx";
            final String METHOD_NAME = "CancelarArticulo";
            final String SOAP_ACTION = "http://10.1.1.18/CancelarArticulo ";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("NCARGA", picking.getNCARGA());
            request.addProperty("BODEGA", picking.getBODEGA());
            request.addProperty("ARTICULO", picking.getARTICULO());
            request.addProperty("USUARIO", bundle.getString("usuario"));
            SoapSerializationEnvelope envelope =
                    new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            transporte = new HttpTransportSE(URL);

            try {

                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                int status = Integer.valueOf(response.toString());
                if (status == 1) {

                    resul = 4;
                } else
                    resul = 5;

            } catch (Exception e) {
                resul = 2;
                //prueba = e.toString();
                //  e.printStackTrace();
            }

            return resul;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            //  customDialog.dismiss();
            // progressBar.setVisibility(View.INVISIBLE);

            if (result == 4) {
                sqLiteHelper.borrararticulo(String.valueOf(picking.getId()));
                onBackPressed();
            }
            lanzartoast(result);
        }
    }

    }





