/*
Control_Clientes
07/08/21
Jesus Raul Zatarain Rendon
 */
package Controladores;

import Modelos.Modelo_Clientes;
import Vistas.Consulta_Clientes;
import Vistas.Registro_Clientes;
import Vistas.SeleccionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Personal
 */
public class Control_Clientes implements ActionListener, WindowListener{
        private Modelo_Clientes modelo;
        private Registro_Clientes vista;
        private Consulta_Clientes vista_consulta;
        private SeleccionVista vista_anterior;
        
        public Control_Clientes(Modelo_Clientes modelo, Registro_Clientes vista, Consulta_Clientes vista_consulta, SeleccionVista vista_anterior){
            this.modelo = modelo;
            this.vista=vista;
            this.vista_consulta = vista_consulta;
            this.vista_anterior = vista_anterior;
            this.vista.btn_Agregar.addActionListener(this);
            this.vista.addWindowListener(this);
            this.vista_consulta.addWindowListener(this);
        }
        public void iniciarVista_registro() {
            vista.setTitle("Registro clientes");
            vista.pack();
            vista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        }
         public void LimpiarCajasTexto(){
            vista.txt_Nombre.setText("");
            vista.txt_Apellidos.setText("");
            vista.txt_Colonia.setText("");
            vista.txt_Calle.setText("");
            vista.txt_RFC.setText("");
            vista.txt_Correo.setText("");  
            vista.txt_Telefono.setText("");
        }
        public void iniciarVista_consulta() {
            vista_consulta.setTitle("Consulta de clientes");
            vista_consulta.pack();
            vista_consulta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            vista_consulta.setLocationRelativeTo(null);
            vista_consulta.talbla.setModel(modelo.consultarClientes());
            vista_consulta.setVisible(true);
        }
         
        @Override
        public void actionPerformed(ActionEvent evento) {
            if(vista.btn_Agregar == evento.getSource()) {
                if(modelo.usuarioAgregar(vista.txt_Nombre.getText(), vista.txt_Apellidos.getText(), vista.txt_Colonia.getText(),vista.txt_Calle.getText(),vista.txt_RFC.getText())){
                    if(modelo.telefonoAgregar(vista.txt_Telefono.getText(), vista.txt_RFC.getText())){
                        JOptionPane.showMessageDialog(null, "Registro insertado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo insertar su telefono");
                    }
                    LimpiarCajasTexto();
            }else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar");
            }
        }
    }
    @Override
    public void windowClosing(WindowEvent we) {
        vista_anterior.setVisible(true);
    }

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