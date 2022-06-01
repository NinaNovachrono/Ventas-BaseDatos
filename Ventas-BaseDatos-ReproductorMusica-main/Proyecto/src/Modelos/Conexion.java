/*
    Sánchez Plazola José Abraham
    02/08/2021
    Clase para la apertura de comunicacion con el servidor manejador de base de datos
*/
package Modelos;

import Interfaces.PlantillaConexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion implements PlantillaConexion {

    @Override
    public void cerrarConexion(Connection c) throws SQLException {
        try{
            if(!c.isClosed()){
                c.close();
            }
        } catch(SQLException e) {
            System.out.println("Error al cerrar la conexión");
        }
    }

    @Override
    public Connection abrirConexion() throws SQLException {
        Connection con;
        String DB = "";
        String user = "";
        String pass = "";
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(DB, user, pass);
        }catch(SQLException e){
            System.out.println("No se pudo abrir conexion, error: " + e);
            con = null;
        }
        return con;
    }
    
}
