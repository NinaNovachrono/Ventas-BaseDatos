/*
    Sánchez Plazola José Abraham
    02/08/2021
    Modelo para las operaciones de la entidad "Reproductor" de la base de datos
*/
package Modelos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Personal
 */
public class Modelo_Reproductor {
    private Conexion conexion = new Conexion();
    
    
    public boolean sentencia(String sql){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            
            s.executeUpdate(sql);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }

    
    public ResultSet sentencia_rs(String sql){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(sql);

            return rs;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return null;
        }
    }
    /**
     *  Función utilizada para el registro de nuevos usuarios a la plataforma
     * @param user Nombre de usuario
     * @param email Email del usuario
     * @param id Numero de identificación del cliente asociado
     * @param pass Contraseña del usuario
     * @return True si se completo la operación correctamente, de otra forma, false
     */
    public boolean RegistrarUsuario(String user, String email, int id, String pass){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call registrar_usuario('%s', '%s', %d, '%s');", user, email, id, pass);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Funcion utilizada para registrar una nueva lista de reproducción
     * @param nombre Nombre de la lista de reproduccion a crear
     * @return True si se completo la operación correctamente, de otra forma, false
     */
    public boolean RegistrarListaReproduccion(String nombre){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call registrar_lista('%s');", nombre);
            System.out.println(cadenaSQL);
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Funcion utilizada para vincular a un usuario con una lista de reproduccion y permitirle acceso a esta
     * @param usuarioid Numero de identificación del usuario a vincular con la lista
     * @param listaid Numero de identificación de la lista a vincular con el usuario
     * @return True si se completo la operación correctamente, de otra forma, false
     */
    public boolean UsuariosALista(int usuarioid, int listaid){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call añadir_usuarios_a_lista(%d, %d);", usuarioid, listaid);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    public boolean CancionesALista(String nombreCancion, String nombreLista){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            
            String cadenaSQL = String.format("call anadir_canciones_a_lista('%s', '%s');", nombreCancion, nombreLista);
            System.out.println(cadenaSQL);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    /**
     *  Funcion utilizada para crear el historial de reproducción del usuario
     * @param id Numero de identificacion del usuario
     * @return True si se completo la operación correctamente, de otra forma, false
     */
    public boolean CrearHistorial(int id){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call crear_historial(%d);", id);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }
    
    public boolean AddBiblioteca(String nombre, int idusuario){
        try{
            Connection con = conexion.abrirConexion();
            
            Statement s = con.createStatement();
            String cadenaSQL = String.format("call añadir_biblioteca('%s', %d);", nombre, idusuario);
            
            s.executeUpdate(cadenaSQL);
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch(SQLException e){
            System.out.println("Error: " + e);
            return false;
        }
    }

    public ResultSet ConsultarHistorial(int historial){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();

            ResultSet rs = s.executeQuery("call consultar_historial(" + historial + ");");
            
            return rs;
        }catch(SQLException e){
            System.out.println("Error " + e);
            return null;
        }
    }
    
    public ResultSet getPath(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            
            ResultSet rs = s.executeQuery("call obtener_ruta();");
            
            return rs;
            
        }catch(SQLException e){
            System.out.println("Error " + e);
            return null;
        }
    }

    public ResultSet getNames(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            
            ResultSet rs = s.executeQuery("select nombre from canciones;");
            
            return rs;
            
        }catch(SQLException e){
            System.out.println("Error " + e);
            return null;
        }   
    }
    
    public ResultSet getPlaylist(){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            
            ResultSet rs = s.executeQuery("call consultar_lista();");
            
            return rs;
            
        }catch(SQLException e){
            System.out.println("Error " + e);
            return null;
        }        
    }
    
    public ResultSet getSongsFromPlaylist(String nombre){
        try{
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            
            ResultSet rs = s.executeQuery("call obtener_ruta_por_nombre('" + nombre + "');");
            
            return rs;
        }catch(SQLException e){
            System.out.println("Error " + e);
            return null;
        }
    }
    

}