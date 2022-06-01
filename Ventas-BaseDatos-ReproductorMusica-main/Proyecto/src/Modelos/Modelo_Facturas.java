/*
Modelo_Facturas
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
 * @author jerar
 */
public class Modelo_Facturas {
    private Conexion conexion = new Conexion();
    
    
    public DefaultTableModel usuarioConsultar() {
         try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            ResultSet rs = s.executeQuery("call consulta_facturas();");
                    
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
            System.out.println("Error: " + e);
            return null; 
        }
    }
}
