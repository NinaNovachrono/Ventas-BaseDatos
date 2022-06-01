/*
Modelo_Productos
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
public class Modelo_Productos {
    private Conexion conexion = new Conexion();
    
    
    public DefaultTableModel usuarioConsultar() {
         try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            DefaultTableModel dtm;
            ResultSet rs = s.executeQuery("call consulta_productos();");
                    
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
    
    public boolean RegistrarProductos( int idAlbumes, String Nombre, String Precio, String Existencia, String Genero_idGenero, String Artista_idArtista){
        try{
            Connection con = conexion.abrirConexion ( );
            Statement s = con.createStatement ( );

           String cadenaSQL = "INSERT INTO albumes VALUES ("+idAlbumes+",'"+Nombre+"','"+Precio+"','"+Existencia+"',"+Genero_idGenero+","+Artista_idArtista+");";
            int registro = s.executeUpdate(cadenaSQL);

            conexion.cerrarConexion(con);
            return true;

        }catch(SQLException e){
            System.out.println("Error: " + e); 
            return false;
        }
    }
}
