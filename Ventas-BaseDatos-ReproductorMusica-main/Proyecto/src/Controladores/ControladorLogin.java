/*
    Sánchez Plazola José Abraham
    04/08/2021
    Controlador para login al reproductor de musica
*/
package Controladores;

import Modelos.Modelo_Registro;
import Modelos.Modelo_Reproductor;
import Vistas.Reproductor_Login;
import Vistas.Reproductor_Registro;
import Vistas.SeleccionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Personal
 */
public class ControladorLogin implements ActionListener, WindowListener{
    //Atributos vista, modelo y la vista anterior a la cuál regresar cuando sea necesario
    Reproductor_Login vista;
    Modelo_Reproductor modelo;
    SeleccionVista vista_anterior;
    private int idusuario;
    
    //Constructor parametros, agregamos listeners a los botones relevantes
    public ControladorLogin(Reproductor_Login vista_login, Modelo_Reproductor modelo, SeleccionVista vista_anterior){
        this.vista = vista_login;
        this.modelo = modelo;
        this.vista_anterior = vista_anterior;
        this.vista.IngresarBtn.addActionListener(this);
        this.vista.addWindowListener(this);
        this.vista.RegBtn.addActionListener(this);
    }
    
    //Método para iniciar la vista relacionada
    public void iniciarVista(){
        vista.setTitle("Ventana de login");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    //Listener para esconder la vista actual y mostrar la siguiente
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.vista.IngresarBtn == ae.getSource()){
            try{
                String usuario = "";
                String contra = "";
                ResultSet rs = this.modelo.sentencia_rs("select nombreusuario from usuarios where nombreusuario = '"+ this.vista.UsuarioTxt.getText() +"';");
                while(rs.next()){
                    usuario = rs.getString("NombreUsuario");
                }
                rs.close();
                rs = this.modelo.sentencia_rs("select password from usuarios where nombreusuario = '"+ this.vista.UsuarioTxt.getText() +"';");
                while(rs.next()){
                    contra = rs.getString("Password");
                }
                rs.close();
                if(this.vista.UsuarioTxt.getText().equals(usuario) && this.vista.ContraTxt.getText().equals(contra)){
                    rs = this.modelo.sentencia_rs("select idusuarios from usuarios where nombreusuario = '"+ this.vista.UsuarioTxt.getText() +"';");
                    while(rs.next()){
                        idusuario = rs.getInt("idusuarios");
                    }
                    Main.run(idusuario);
                    this.vista.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Combinacion de usuario y contraseña incorrecta");
                }
            } catch(Exception e){
                System.out.println("Error: " + e);
            }
        } else if(this.vista.RegBtn == ae.getSource()){
            this.vista.setVisible(false);
            Control_Registro controlador;
            Reproductor_Registro vista = new Reproductor_Registro();
            Modelo_Registro modelo = new Modelo_Registro();
            
            controlador = new Control_Registro(modelo, vista, this.vista);
            controlador.iniciarVista();
        }
    }

    @Override
    public void windowClosing(WindowEvent we) {
        this.vista_anterior.setVisible(true);
    }
    
    //Métodos innecesarios
    @Override
    public void windowOpened(WindowEvent we) {
        
    }
    @Override
    public void windowClosed(WindowEvent we) {
        
    }
    @Override
    public void windowIconified(WindowEvent we) {
    }
    @Override
    public void windowDeiconified(WindowEvent we) {
    }
    @Override
    public void windowActivated(WindowEvent we) {
    }
    @Override
    public void windowDeactivated(WindowEvent we) {
    }

}
