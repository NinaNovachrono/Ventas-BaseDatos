/*
Osuna Pimienta Melani Nohemi
07/08/2021
Modelo para el registro de usuarios
*/
package Modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;


public class Modelo_Registro {
private Conexion conexion = new Conexion();
   public boolean RegistrarUsuarios(String usuario, String telefono, String contra, String contrac){
        try{
            Connection con = conexion.abrirConexion ( );
            Statement s = con.createStatement ( );
          
           String cadenaSQL = String.format("insert into usuarios(nombreUsuario, telefono, clientes_idclientes, password) values ( '%s', '%s', "
                   + "          (Select clientes_idclientes from telefonoclientes inner join clientes on idclientes = clientes_idclientes where telefono = '%s'), '%s')"
                                , usuario, telefono, telefono, contrac);
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e); 
            return false;
        }
    }
   
 //   private Conexion_Registro conexion = new Conexion_Registro ();
       private DefaultTableModel consultarRegistroU ( ){
        try{
            Connection con = conexion.abrirConexion ( );
            Statement s = con.createStatement ( );
            ResultSet rs = s.executeQuery ( "select * from usuarios");
            DefaultTableModel dtm = new DefaultTableModel(){
                
                /*@Override
                public boolean isCellEditable(int row, int column){
                    return true;
                }*/
            };
            
            ResultSetMetaData rsMd =  rs.getMetaData();
            int columnas = rsMd.getColumnCount();//regresa numero de columnas
            for(int i=1; i<=columnas;i++){
                dtm.addColumn(rsMd.getColumnLabel(i));
            }
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int i=0; i<columnas;i++){
                    fila[i] = rs.getObject(i+1);
                }
                dtm.addRow(fila);
            }
            return null;
        }catch(SQLException e){
            return null; 
        }
       
    }

    }

 

  
    

