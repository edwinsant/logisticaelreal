package com.inventario.acreal.floatbutton.Utils;

import net.sourceforge.jtds.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by BYCARA on 31/1/2017.
 */

public class DBConnection {
    private static  DBConnection instance = null;
    private static final String URL = "jdbc:jtds:sqlserver://10.1.1.12:1521/dbjdepd;";
    private static final String USER = "crodriguez";
    private static final String PASS = "crodriguez";
    private static Connection connection = null;

    private DBConnection(){}

    public  static DBConnection getInstance(){
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Connection getConnection(){
        if (connection == null)
            connection = conectar();
        return connection;
    }
    private  Connection conectar(){
        Connection conn = null;
        try{
            (new Driver()).getClass();
            conn = DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return conn;
    }


}
