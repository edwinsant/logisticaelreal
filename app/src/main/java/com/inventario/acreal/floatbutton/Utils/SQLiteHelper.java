package com.inventario.acreal.floatbutton.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.inventario.acreal.floatbutton.Models.Move;
import com.inventario.acreal.floatbutton.Models.Picking;
import com.inventario.acreal.floatbutton.Models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crodriguez on 05/04/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_BASE_DATOS = "Testtiando.db";

    private static final int VERSION_ACTUAL = 2;

    private final Context contexto;




    String Move = "CREATE TABLE Move (id INTEGER primary key,No_Tarea TEXT, Usuario TEXT, Bodega TEXT, UbicacionO TEXT, Lote TEXT" +
            ", ProductActivity TEXT, Descripcion TEXT, UMPallet TEXT,Cantidad_UMP TEXT, UMT TEXT, Cantidad_UMT TEXT, Factor_Conv_UMT TEXT,UbicacionD TEXT, Seguridad TEXT" +
            ", Fechavencimiento TEXT,Cod_cip TEXT)";
    String Product ="CREATE TABLE Product (INTEGER AUTOINCREMENT,Producto TEXT, Cantidad TEXT, UM TEXT, Lote TEXT, UbicacionO TEXT, UbicacionDTEXT" +
            ", Seguridad TEXT)";
    String Location = "CREATE TABLE Location(INTEGER AUTOINCREMENT,Producto TEXT, Cantidad TEXT, UM TEXT, Lote TEXT, UbicacionO TEXT, UbicacionD TEXT, Seguridad TEXT)";

    String User = "CREATE TABLE User (id INTEGER primary key, usuario TEXT, Activo Integer)";

    String Picking = "CREATE TABLE Picking (id INTEGER primary key, BODEGA TEXT, FECHA TEXT, TRANSPORTISTA TEXT, CODIGO_CORTO TEXT, ARTICULO TEXT, DESCRIPCION TEXT, NLINEA TEXT, LINEA TEXT," +
            "UBICACION TEXT, LOTE TEXT, UMT TEXT, UMS TEXT, NCARGA TEXT, NPEDIDOS TEXT, COD_VERIFICACION TEXT, SECUENCIA TEXT, CANT_TOTAL_UMP TEXT, CANT_TOTAL_UMS TEXT, FACTOR TEXT, CANT_UMS TEXT," +
            " UNIDAD_SUELTAS TEXT, Existencia TEXT)";
    String XArticulos = "CREATE TABLE XArticulos (id INTEGER primary key, BODEGA TEXT, FECHA TEXT, TRANSPORTISTA TEXT, CODIGO_CORTO TEXT, ARTICULO TEXT, DESCRIPCION TEXT, NLINEA TEXT, LINEA TEXT," +
            "UBICACION TEXT, LOTE TEXT, UMT TEXT, UMS TEXT, NCARGA TEXT, NPEDIDOS TEXT, COD_VERIFICACION TEXT, SECUENCIA TEXT, CANT_TOTAL_UMP TEXT, CANT_TOTAL_UMS TEXT, FACTOR TEXT, CANT_UMS TEXT," +
            " UNIDAD_SUELTAS TEXT, Existencia TEXT)";

    public SQLiteHelper(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS,null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Move = "drop table IF EXISTS Move;";
        String Product = "drop table IF EXISTS Product;";
        String Location = "drop table IF EXISTS Location;";
        String user = "drop table IF EXISTS User;";
        sqLiteDatabase.execSQL(Move);
        sqLiteDatabase.execSQL(Product);
        sqLiteDatabase.execSQL(Location);
        sqLiteDatabase.execSQL(User);
        sqLiteDatabase.execSQL(Picking);
        sqLiteDatabase.execSQL(XArticulos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String Move = "drop table IF EXISTS Move;";
        String Product = "drop table IF EXISTS Product;";
        String Location = "drop table IF EXISTS Location;";
        String user = "drop table IF EXISTS User;";
        sqLiteDatabase.execSQL(Move);
        sqLiteDatabase.execSQL(Product);
        sqLiteDatabase.execSQL(Location);
        sqLiteDatabase.execSQL(User);
        sqLiteDatabase.execSQL(Picking);
        sqLiteDatabase.execSQL(XArticulos);

    }
    public  void borrarXcuadros(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Picking");
        sqLiteDatabase.execSQL(Picking);
    }
    public  void borrarXArticulos(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS XArticulos");
        sqLiteDatabase.execSQL(XArticulos);
    }
    public void borrar()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Move");
        sqLiteDatabase.execSQL(Move);
    }
    public boolean Insersuario(String user, Integer activo){
        User valor = null;
        ContentValues values = new ContentValues();
        String query = " SELECT name FROM sqlite_master WHERE TYPE='table' AND name='User'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()>0) {
            query = "Select * from User where usuario = '"+user +"'";
            cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                valor = new User();
                if (cursor.moveToFirst()) {
                    valor.setUsuario(cursor.getString(1));
                    valor.setActivo(cursor.getInt(2));
                }
               // values.put("usuario", user);
                values.put("Activo", activo);
                database.update("User", values, "usuario= '" +user +"'", null);
            }
            else {
                values.put("usuario", user);
                values.put("Activo", activo);
                database.insert("User", null, values);
            }
        }




        database.close();
        return  true;

    }
    public User SelectActivoUsuairo(){
        User valor = null;
        String query = " SELECT name FROM sqlite_master WHERE TYPE='table' AND name='User'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()>0) {
            query = "Select * from User";
            cursor = database.rawQuery(query,null);
            if (cursor.getCount() > 0) {
                valor = new User();
                if (cursor.moveToFirst()) {
                    do {
                        valor.setUsuario(cursor.getString(1));
                        valor.setActivo(cursor.getInt(2));
                    } while (cursor.moveToNext());
                }
            }
        }
        return valor;

    }

    public List<Picking> Selectcuadros(){
        List<Picking> Lcuadros = null;
        String query ="SELECT name FROM sqlite_master WHERE TYPE='table' AND name='Picking'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0) {
            query = "SELECT DISTINCT BODEGA, FECHA, NCARGA, TRANSPORTISTA FROM Picking ORDER BY NCARGA";
            cursor = database.rawQuery(query,null);
            if (cursor.getCount() > 0){
                Lcuadros = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do{
                        Picking picking = new Picking();
                        picking.setBODEGA(cursor.getString(0));
                        picking.setFECHA(cursor.getString(1));
                        picking.setNCARGA(cursor.getString(2));
                        picking.setTRANSPORTISTA(cursor.getString(3));
                        Lcuadros.add(picking);
                    }while (cursor.moveToNext());
                }
            }
        }
        return Lcuadros;
    }
    public List<Picking> SelectcuadrosArticulos(String NCARGA, String NLINEA){
        List<Picking> Lcuadros = null;
        String query ="SELECT name FROM sqlite_master WHERE TYPE='table' AND name='XArticulos'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0) {
            query = "SELECT Id, LOTE, UBICACION, ARTICULO, DESCRIPCION, LINEA,NCARGA FROM XArticulos ";
            cursor = database.rawQuery(query,null);
            if (cursor.getCount() > 0){
                Lcuadros = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do{
                        Picking picking = new Picking();
                        picking.setId(Integer.parseInt(cursor.getString(0)));
                        picking.setLOTE(cursor.getString(1));
                        picking.setUBICACION(cursor.getString(2));
                        picking.setARTICULO(cursor.getString(3));
                        picking.setDESCRIPCION(cursor.getString(4));
                        picking.setLINEA(cursor.getString(5));
                        picking.setNCARGA(cursor.getString(6));
                        Lcuadros.add(picking);
                    }while (cursor.moveToNext());
                }
            }
        }
        return Lcuadros;
    }

    public Picking SelectLineaArticulo(String id){
        Picking picking = new Picking();
        String query ="SELECT name FROM sqlite_master WHERE TYPE='table' AND name='XArticulos'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0) {
            query = "SELECT Id, LOTE, UBICACION, ARTICULO, DESCRIPCION, CODIGO_CORTO,  CANT_TOTAL_UMP, CANT_TOTAL_UMS, UMT, UMS, UNIDAD_SUELTAS, FACTOR, BODEGA, SECUENCIA, Existencia, NCARGA, COD_VERIFICACION FROM XArticulos WHERE Id == '" +id+"'";
            cursor = database.rawQuery(query,null);
            if (cursor.getCount() > 0){

                if(cursor.moveToFirst()){
                    do{
                        picking.setId(Integer.parseInt(cursor.getString(0)));
                        picking.setLOTE(cursor.getString(1));
                        picking.setUBICACION(cursor.getString(2));
                        picking.setARTICULO(cursor.getString(3));
                        picking.setDESCRIPCION(cursor.getString(4));
                        picking.setCODIGO_CORTO(cursor.getString(5));
                        picking.setCANT_TOTAL_UMP(cursor.getString(6));
                        picking.setCANT_TOTAL_UMS(cursor.getString(7));
                        picking.setUMT(cursor.getString(8));
                        picking.setUMS(cursor.getString(9));
                        picking.setUNIDAD_SUELTAS(cursor.getString(10));
                        picking.setFACTOR(cursor.getString(11));
                        picking.setBODEGA(cursor.getString(12));
                        picking.setSECUENCIA(cursor.getString(13));
                        picking.setExistencia(cursor.getString(14));
                        picking.setNCARGA(cursor.getString(15));
                        picking.setCOD_VERIFICACION(cursor.getString(16));

                    }while (cursor.moveToNext());
                }
            }
        }
        return picking;
    }
    public List<Picking> SelectLineas(String NCARGA){
        List<Picking> Lcuadros = null;
        String query ="SELECT name FROM sqlite_master WHERE TYPE='table' AND name='Picking'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0) {
            query = "SELECT DISTINCT LINEA, NLINEA, BODEGA,FECHA FROM Picking WHERE NCARGA = '"+NCARGA+"' ORDER BY LINEA";
            cursor = database.rawQuery(query,null);
            if (cursor.getCount() > 0){
                Lcuadros = new ArrayList<>();
                if(cursor.moveToFirst()){
                    do{
                        Picking picking = new Picking();
                        picking.setLINEA(cursor.getString(0));
                        picking.setNLINEA(cursor.getString(1));
                        picking.setBODEGA(cursor.getString(2));
                        picking.setFECHA(cursor.getString(3));
                        Lcuadros.add(picking);
                    }while (cursor.moveToNext());
                }
            }
        }
        return Lcuadros;
    }
    public boolean InsertXcuadros(Picking picking)
    {
        ContentValues values = new ContentValues();
        values.put("BODEGA", picking.getBODEGA());
        values.put("FECHA", picking.getFECHA());
        values.put("TRANSPORTISTA", picking.getTRANSPORTISTA());
        values.put("CODIGO_CORTO", picking.getCODIGO_CORTO());
        values.put("ARTICULO", picking.getARTICULO());
        values.put("DESCRIPCION", picking.getDESCRIPCION());
        values.put("NLINEA", picking.getNLINEA());
        values.put("LINEA", picking.getLINEA());
        values.put("UBICACION", picking.getUBICACION());
        values.put("LOTE", picking.getLOTE());
        values.put("UMT", picking.getUMT());
        values.put("UMS", picking.getUMS());
        values.put("NCARGA", (picking.getNCARGA()));
        values.put("NPEDIDOS", (picking.getNPEDIDOS()));
        values.put("COD_VERIFICACION", (picking.getCOD_VERIFICACION()));
        values.put("SECUENCIA", (picking.getSECUENCIA()));
        values.put("CANT_TOTAL_UMP", (picking.getCANT_TOTAL_UMP()));
        values.put("CANT_TOTAL_UMS", (picking.getCANT_TOTAL_UMS()));
        values.put("FACTOR", (picking.getFACTOR()));
        values.put("CANT_UMS", (picking.getCANT_UMS()));
        values.put("UNIDAD_SUELTAS", (picking.getUNIDAD_SUELTAS()));
        values.put("Existencia", picking.getExistencia());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("picking", null, values);
        database.close();
        return true;
    }
    public boolean InsertXArticulos(Picking picking)
    {
        ContentValues values = new ContentValues();
        values.put("BODEGA", picking.getBODEGA());
        values.put("CODIGO_CORTO", picking.getCODIGO_CORTO());
        values.put("ARTICULO", picking.getARTICULO());
        values.put("DESCRIPCION", picking.getDESCRIPCION());
        values.put("UBICACION", picking.getUBICACION());
        values.put("LOTE", picking.getLOTE());
        values.put("UMT", picking.getUMT());
        values.put("UMS", picking.getUMS());
        values.put("NCARGA", (picking.getNCARGA()));
        values.put("NPEDIDOS", (picking.getNPEDIDOS()));
        values.put("COD_VERIFICACION", (picking.getCOD_VERIFICACION()));
        values.put("SECUENCIA", (picking.getSECUENCIA()));
        values.put("CANT_TOTAL_UMP", (picking.getCANT_TOTAL_UMP()));
        values.put("CANT_TOTAL_UMS", (picking.getCANT_TOTAL_UMS()));
        values.put("FACTOR", (picking.getFACTOR()));
        values.put("CANT_UMS", (picking.getCANT_UMS()));
        values.put("UNIDAD_SUELTAS", (picking.getUNIDAD_SUELTAS()));
        values.put("Existencia", picking.getExistencia());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("XArticulos", null, values);
        database.close();
        return true;
    }
    public boolean InsertTraslado(Move move)
    {
        ContentValues values = new ContentValues();
        values.put("No_Tarea", move.getNo_Tarea());
        values.put("Usuario", move.getUsuario());
        values.put("Bodega", move.getBodega());
        values.put("UbicacionO", move.getUbicacionO());
        values.put("Lote", move.getLote());
        values.put("ProductActivity", move.getArticulo());
        values.put("Descripcion", move.getDescripcion());
        values.put("UMPallet", move.getUMPallet());
        values.put("Cantidad_UMP", move.getCantidad_UMP());
        values.put("UMT", move.getUMT());
        values.put("Cantidad_UMT", move.getCantidad_UMT());
        values.put("Factor_Conv_UMT", move.getFactor_Conv_UMT());
        values.put("UbicacionD", move.getUbicacionD());
        values.put("Seguridad", move.getSeguridad());
        values.put("Fechavencimiento", move.getFechavencimiento());
        values.put("Cod_cip", move.getCod_cip());
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("Move", null, values);
        database.close();

        return true;
    }
    public boolean InsertXproducto(ContentValues values)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("Product", null, values);
        database.close();
        return true;
    }
    public boolean InsertXubicacion(ContentValues values)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("Product", null, values);
        database.close();
        return true;
    }
    public boolean Uptate(ContentValues values, String codigo)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.update("Inventario",values,"Codigo="+codigo,null);
        database.close();
        return true;
    }
    public boolean borrarusuario(String usuario){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("User","usuario="+"'"+usuario+"'",null);
        database.close();
        return true;
    }
    public boolean borrararticulo(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("Picking","Id="+"'"+id+"'",null);
        database.close();
        return true;
    }

    public List<Move> getAllTask(){
        String selectQuery = "SELECT  * FROM Move";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Move> mMoveList =  new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                mMoveList.add(new Move());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mMoveList;
    }

    public List<Move> SelectTraslado(){
        List<Move> Move =  new ArrayList<>();
        String query = " select * from Move";
        Move guardar = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()>0){
            guardar = new Move();
            if (cursor.moveToFirst()){
                do {

                    guardar.setUsuario(cursor.getString(2));
                    guardar.setUbicacionO(cursor.getString(4));
                    guardar.setArticulo(cursor.getString(6));
                    guardar.setDescripcion(cursor.getString(7));
                    guardar.setCod_cip(cursor.getString(16));
                    Move.add(guardar);
                    //Inventario.add(guardar);
                } while (cursor.moveToNext());
            }
        }
        return Move;
    }
    public Move Selectporcodigo(String Codigo)
    {
        String query = " select * from Move where Seguridad = "+"'"+Codigo+"'";
        Move guardar = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()>0){
            guardar = new Move();
            if (cursor.moveToFirst()){
                do {
                    guardar.setNo_Tarea(cursor.getString(1));
                    guardar.setUsuario(cursor.getString(2));
                    guardar.setBodega(cursor.getString(3));
                    guardar.setUbicacionO(cursor.getString(4));
                    guardar.setLote(cursor.getString(5));
                    guardar.setArticulo(cursor.getString(6));
                    guardar.setDescripcion(cursor.getString(7));
                    guardar.setUMPallet(cursor.getString(8));
                    guardar.setCantidad_UMP(cursor.getString(9));
                    guardar.setUMT(cursor.getString(10));
                    guardar.setCantidad_UMT(cursor.getString(11));
                    guardar.setFactor_Conv_UMT(cursor.getString(12));
                    guardar.setUbicacionD(cursor.getString(13));
                    guardar.setSeguridad(cursor.getString(14));
                    guardar.setFechavencimiento(cursor.getString(15));
                    //Inventario.add(guardar);
                } while (cursor.moveToNext());
            }
        }
        SelectTraslado();
        return guardar;
    }
    /*
    public String[] SelectCodigo()
    {
        String query = " Select Codigo from Inventario ";
        String[] codigo = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursorAdapter = database.rawQuery(query,null);
        if(cursorAdapter.getCount() > 0) {
            codigo = new String[cursorAdapter.getCount()];
            int i = 0;
            if (cursorAdapter.moveToFirst()) {
                do {
                    codigo[i] = Integer.toString(cursorAdapter.getInt(0));
                    i++;
                } while (cursorAdapter.moveToNext());
            }
        }
        return codigo;
    }*/
    /*public List<Inventario> select()
    {
      List<Inventario> Inventario =  new ArrayList<>();
         Inventario guardar = null;
        String query = " Select * from Inventario ";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursorAdapter = database.rawQuery(query, null);
        if (cursorAdapter.moveToFirst()){
            do {
                guardar = new Inventario();
                guardar.setCodigo(cursorAdapter.getString(0));
                guardar.setDescripcion(cursorAdapter.getString(1));
                guardar.setUxC(cursorAdapter.getInt(2));
                guardar.setCaja(cursorAdapter.getInt(3));
                guardar.setUnidades(cursorAdapter.getInt(4));
                guardar.setTotal(cursorAdapter.getInt(5));
                Inventario.add(guardar);
            } while (cursorAdapter.moveToNext());
        }
        return Inventario;
    }*/
}
