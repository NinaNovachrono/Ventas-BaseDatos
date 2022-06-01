/*
    Sánchez Plazola José Abraham
    02/08/2021
    Modelo para las operaciones de la entidad "Empleados" de la base de datos
*/
package Modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;


public class Modelo_Empleados {
    private Conexion conexion = new Conexion();
    
    public boolean sentencia(String sql){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format(sql);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Función para registrar un empleado a la base de datos
     * @param nombre Nombre de la persona
     * @param apell Apellido de la persona
     * @param colon Colonia donde reside
     * @param calle Calle y numero donde reside
     * @param rfc RFC del contribuyente
     * @param numss Numero de seguro social del contribuyente
     * @return True si la operacion fue exitosa, false si ocurrió algún error
     */
    public boolean RegistrarEmpleados(String nombre, String apell, String colon, String calle, String rfc, String numss){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call registro_empleados('%s', '%s', '%s', '%s', '%s', '%s')", nombre, apell, colon, calle, rfc, numss);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Función para realizar operaciones con un registro empleado de la base de datos
     * @param id Id del registro
     * @param nombre Nombre de la persona
     * @param apell Apellido de la persona
     * @param colon Colonia donde reside
     * @param calle Calle y numero donde reside
     * @param rfc RFC del contribuyente
     * @param numss Numero de seguro social del contribuyente
     * @param operacion Id de la operacion a realizar
     * @return True si la operacion fue exitosa, false si ocurrió algún error
     */
    public boolean OperacionesEmpleados(int id, String nombre, String apell, String calle, String colon, String rfc, String numss, String operacion){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call operaciones_empleados(%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s');", id, nombre, apell, colon, calle, rfc, numss, operacion);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Funcion para consultar los registros de los empleados
     * @return True si la operacion fue exitosa, false si ocurrió algún error
     */
    public DefaultTableModel consultarEmpleados() {
         try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            ResultSet rs = s.executeQuery("call consulta_empleados(0);");
                    
            dtm = new DefaultTableModel();
            ResultSetMetaData rsMd = rs.getMetaData();
            int columnas = rsMd.getColumnCount(); //Regresar el numero de columnas
            
            for(int i=1; i<=columnas; i++){   //Ciclo para obtenr nombre de cada columas (Encabezado)
                dtm.addColumn(rsMd.getColumnLabel(i));
            }
            //Ciclo para llenar las filas
            while(rs.next()){
                Object [] fila = new Object[columnas];
                for(int i =0; i<columnas; i++){
                 fila[i] = rs.getObject(i+1);
                }
                dtm.addRow(fila);
            }
            return dtm;
        }catch(SQLException e){
            
            return null; 
        }
    }
}
