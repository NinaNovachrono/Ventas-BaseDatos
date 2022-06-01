/*
 Descripción: Clase Modelo de la Vista Registro Ventas
 07/Agosto/2021
Manuel Alfonso Cordero Covantes TCI 6-2
 */
package Modelos;

import Vistas.Registro_Ventas;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Manolo
 */


public class Modelo_Ventas  {
    public static String nombre;
    public static String nombre1;
    public static String codd, ter;
    public static double precio, cantidad,total;
    public int empleado = 1;
    public int cliente = 0;
    private Conexion conexion = new Conexion();
    DefaultTableModel dtm;
    Object[] o = new Object[5];

    public static String ss = "1";
    public boolean  VentaAgregar (int idAlbum,int Cantidad ){
        try{
            Connection con = conexion.abrirConexion();
            //Ejecución de consulta "Agregar" en DetallesFacturas
            Statement s= con.createStatement();
            String cadenaSQL = "INSERT INTO detallesfactura VALUES ((codigo para encontrar la id de factura),'"+idAlbum+"','"+Cantidad+"');";
            int registro = s.executeUpdate(cadenaSQL);
            Integer i = Integer.valueOf(ss);
            i=i+1;
            String value = String.valueOf(i);
            ss = value;
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }   
    
    public boolean busquedcliente(String tel){
       Registro_Ventas ventas = new Registro_Ventas(); try{
            Connection con = conexion.abrirConexion();
            //String tel = ventas.TxtTelefono.getText();
            Statement stmt = con.createStatement();
            ResultSet rs, id;
        
            Statement s= con.createStatement();
            
            //Ejecución de consulta: Devuelve el Id del Cliente
            rs = stmt.executeQuery("SELECT Clientes_idClientes FROM telefonoClientes WHERE telefono =" +tel);
            while ( rs.next() ) {
                String rs2 = rs.getString("Clientes_IdClientes"); //Conversion de la consulta a String
                Integer i = Integer.valueOf(rs2); //Conversion  String a entero
                cliente = i;
                id = s.executeQuery("SELECT Nombre FROM Clientes WHERE IdClientes = " +i); //Consulta que devuelve el Nombre base a su ID
                while (id.next()){
                    String nombre1 = id.getString("Nombre"); //conversion de la Consulta a String
                    nombre= nombre1;
                }
            }
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    
    public boolean busquedaproducto(int IdCodigo){
        Registro_Ventas ventas = new Registro_Ventas();   
        int ss = 1;
        String s=String.valueOf(ss);
        Integer i = Integer.valueOf(s);
        String f =String.valueOf(i);
        ventas.lblID.setText(f);
        try{
            Connection con = conexion.abrirConexion();
            Statement stmt = con.createStatement();
            Statement t = con.createStatement();
            ResultSet rs, exs;
            exs = stmt.executeQuery("SELECT Existencia FROM albumes WHERE IdAlbumes = "+IdCodigo);//Devuelve Existencias en base a su ID
            while (exs.next()){
                String cod1 = exs.getString("Existencia");
                codd= cod1;
                rs = t.executeQuery("SELECT Nombre FROM albumes WHERE IdAlbumes =" +IdCodigo);//Devuelve el nombre base a au ID
                while ( rs.next() ) {
                    String nombrep = rs.getString("Nombre");
                    nombre1=nombrep;
                }
            }
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    
    public int busquedaId(int cliente, int empleado){
        try{
            Connection con = conexion.abrirConexion();
            int id = -1;
            //para ejecutar la consulta
            Statement s= con.createStatement();
            
            String cadenaSQL = String.format("select idfacturas from facturas where TotalF = 0"
                                        + " and clientes_idclientes = %d and empleados_idempleados = %d;", cliente, empleado);
            ResultSet rs = s.executeQuery(cadenaSQL);
            
            while(rs.next()){
                id = rs.getInt(1);
            }
            
            conexion.cerrarConexion(con);
            return id;
        }catch(SQLException e){
            System.out.print("error" +e);
            return -1;
        }
    }
    
    public boolean registrarFactura(int empleado, int cliente){
        try{
            Connection con = conexion.abrirConexion();
            
            //para ejecutar la consulta
            Statement s= con.createStatement();
            
            String cadenaSQL = String.format("call registrar_factura(%d, %d);", empleado, cliente);
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    
    public boolean registrar_productos(int idalbum, int cantidad, int idfactura){
        try{
            Connection con = conexion.abrirConexion();
            
            //para ejecutar la consulta
            Statement s= con.createStatement();
            
            String cadenaSQL = String.format("call registrar_productos_en_factura(%d, %d, %d);", idalbum, cantidad, idfactura);
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    
    public boolean finalizar_total_factura(int idfactura){
        try{
            Connection con = conexion.abrirConexion();
            
            //para ejecutar la consulta
            Statement s= con.createStatement();
            
            String cadenaSQL = String.format("call finalizar_total_factura(%d);", idfactura);
            int registro = s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        }
    }
    
    /*public boolean añadirTabla( int Cantidad){
        Registro_Ventas ventas = new Registro_Ventas();
        //dtm = (DefaultTableModel) ventas.tabla.getModel();
        try{
            Connection con = conexion.abrirConexion();
            Statement stmt = con.createStatement();
            ResultSet rs, id;
            String rs2;
            String IdCodigo =  ventas.txtCodigo.getText();

            id = stmt.executeQuery("SELECT Precio FROM albumes WHERE IdAlbumes = "+IdCodigo); //Devuelve el precio en base a su id
            while (id.next()){
              rs2 = id.getString("Precio");
             precio = Double.parseDouble(rs2);
             cantidad = Cantidad;
             total = precio * cantidad;}
             //o[0] = IdCodigo;
             //o[1] = ventas.lblNombreClienteVariable.getText();
             //o[2] = precio;
             //o[3] = cantidad;
             //o[4] = total;
            /*dtm.addRow(o);
        double sumatoria1=0.0;
        int totalRow= ventas.tabla.getRowCount();
        totalRow-=1;
        for(int i=0;i<=(totalRow);i++)
        {
           double sumatoria= Double.parseDouble(String.valueOf(ventas.tabla.getValueAt(i,4)));
            //en la parte de arriba indica el primer parametro la fila y el segundo la columna la cual se esta manejando
          sumatoria1+=sumatoria;
          String s=String.valueOf(sumatoria1); 
          ter= s ;
          //ventas.lblFinalVariable.setText(s);
        conexion.cerrarConexion(con);
            return true;
        }catch(SQLException e){
            System.out.print("error" +e);
            return false;
        } */
}