/*
    Sánchez Plazola José Abraham
    06/08/2021
    Controlador para las ventanas pertinentes a empleados
*/
package Controladores;

import Modelos.Modelo_Empleados;
import Vistas.Consulta_Empleados;
import Vistas.Registro_Empleados;
import Vistas.SeleccionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Personal
 */
public class Control_Empleados implements ActionListener, MouseListener, WindowListener{
    private Modelo_Empleados modelo;
    private Consulta_Empleados vista_consulta;
    private Registro_Empleados vista_registro;
    private SeleccionVista vista_anterior;
    
    public Control_Empleados(Modelo_Empleados modelo, Consulta_Empleados vista_consulta, Registro_Empleados vista_registro, SeleccionVista vista_anterior){
        this.modelo = modelo;
        this.vista_consulta = vista_consulta;
        this.vista_registro = vista_registro;
        this.vista_anterior = vista_anterior;
        this.vista_registro.btnAgregar.addActionListener(this);
        this.vista_registro.btnLimpiar.addActionListener(this);
        this.vista_registro.btnRegresar.addActionListener(this);
        this.vista_registro.addWindowListener(this);
        this.vista_consulta.addWindowListener(this);
    }
    
    public void iniciarVista_Registro(){
        vista_registro.setTitle("Registro de empleados");
        vista_registro.pack();
        vista_registro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista_registro.setLocationRelativeTo(null);
        vista_registro.setVisible(true);
    }
    
    public void iniciarVista_Consulta(){
        vista_consulta.setTitle("Consulta de empleados");
        vista_consulta.pack();
        vista_consulta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista_consulta.setLocationRelativeTo(null);
        vista_consulta.talbla.setModel(this.modelo.consultarEmpleados());
        vista_consulta.setVisible(true);
    }
    
    public void limpiar(){
        this.vista_registro.txtApellido.setText("");
        this.vista_registro.txtCalle.setText("");
        this.vista_registro.txtColonia.setText("");
        this.vista_registro.txtNombre.setText("");
        this.vista_registro.txtNumSS.setText("");
        this.vista_registro.txtRFC.setText("");
        this.vista_registro.txtTelefono.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(this.vista_registro.btnAgregar == ae.getSource()){
            if(this.modelo.RegistrarEmpleados(this.vista_registro.txtNombre.getText(), this.vista_registro.txtApellido.getText(),
                                                this.vista_registro.txtColonia.getText(), this.vista_registro.txtCalle.getText(),
                                                this.vista_registro.txtRFC.getText(), this.vista_registro.txtNumSS.getText())){
                if(this.modelo.sentencia("insert into telefonoempleados values('"+ this.vista_registro.txtTelefono.getText() +"', "
                        + "(select idempleados from empleados where RFC = '" + this.vista_registro.txtRFC.getText() + "'));")){
                    JOptionPane.showMessageDialog(null, "Registro ingresado correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el numero celular");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar");
            }
        } else if(this.vista_registro.btnLimpiar == ae.getSource()){
            this.limpiar();
        } else if(this.vista_registro.btnRegresar == ae.getSource()){
            this.vista_consulta.setVisible(false);
            this.vista_registro.setVisible(false);
            this.vista_anterior.setVisible(true);
        }
    }
    
    @Override
    public void windowClosing(WindowEvent we) {
        this.vista_anterior.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }
    
    //Métodos innecesarios
    @Override
    public void mousePressed(MouseEvent me) {   
    }
    @Override
    public void mouseReleased(MouseEvent me) {
    }
    @Override
    public void mouseEntered(MouseEvent me) {
    }
    @Override
    public void mouseExited(MouseEvent me) {
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
