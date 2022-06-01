/*
    Sánchez Plazola José Abraham
    02/08/2021
    Interfaz para la definición de las funciones "Abrir" y "Cerrar" conexiones a la base de datos
*/
package Interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface PlantillaConexion {
    
    /**
     * Función para cerrar una conexión actual a un servidor activo
     * @param c Objeto tipo "Connection" que será la conexión actual a cerrar
     * @throws SQLException En caso de que la conexion no se cierre correctamente, o no haya alguna a cerrar
     */
    void cerrarConexion(Connection c) throws SQLException;
    
    /**
     *  Funcion para abrir y obtener el objeto utilizado para la conexion con un servidor manejador de base de datos
     * @return con - Objeto tipo "Connection" utilizado para abrir la comunicación con un servidor de base de datos
     * @throws SQLException En caso de que la conexion sea incorrecta
     */
    Connection abrirConexion() throws SQLException;
}
