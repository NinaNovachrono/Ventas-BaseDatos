/*
Osuna Pimienta Melani Nohemi
07/08/2021
Controlador para el registro de usuarios
*/


package Controladores;

import Modelos.Modelo_Registro;
import Vistas.Reproductor_Login;
import Vistas.Reproductor_Registro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Control_Registro  implements ActionListener, WindowListener{
    private Modelo_Registro modelo;
    private Reproductor_Registro vista;
    private Reproductor_Login vista_anterior;
    
    public Control_Registro (Modelo_Registro modelo, Reproductor_Registro vista, Reproductor_Login vista_anterior){
        this.modelo = modelo;
        this.vista = vista;
        this.vista_anterior = vista_anterior;
        this.vista.RegButt.addActionListener(this);
        this.vista.addWindowListener(this);
    }

    
    public void iniciarVista() {
        vista.setTitle("Registro");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }  

    
    @Override
    public void actionPerformed(ActionEvent evento) {
       if (vista.RegButt == evento.getSource()){
          if(modelo.RegistrarUsuarios(vista.UsuarioText.getText(), vista.Telefono.getText(),vista.ContraText.getText() ,vista.ContraCText.getText())){
                JOptionPane.showMessageDialog(null, "Registrado Exitosamente");
            }else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el usuario");
            }
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
