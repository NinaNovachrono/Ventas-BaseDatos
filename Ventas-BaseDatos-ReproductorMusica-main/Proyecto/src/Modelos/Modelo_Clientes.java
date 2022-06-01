/*
Modelo_Clientes
07/08/21
Jesus Raul Zatarain Rendon
 */
package Modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Personal
 */
public class Modelo_Clientes {
    private Conexion conexion = new Conexion();
    
    public boolean usuarioAgregar(String nombre,String apellidos, String colonia, String calle, String rfc){
        try{
            Connection con = conexion.abrirConexion();
            
            //para ejecutar la consulta
            Statement s= con.createStatement();
            
            String cadenaSQL = String.format("call registro_clientes('%s', '%s', '%s', '%s', '%s');", nombre, apellidos, colonia, calle, rfc);
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    public boolean  telefonoAgregar (String telefono, String rfc){
        try{
            Connection con = conexion.abrirConexion();
            
            //para ejecutar la consulta
            Statement s= con.createStatement();
            String cadenaSQL = "INSERT INTO telefonoclientes VALUES ('"+telefono+"', (select idclientes from clientes where rfc = '" + rfc + "'));";
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    public DefaultTableModel consultarClientes() {
         try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            ResultSet rs = s.executeQuery("call consulta_clientes(0);");
                    
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
