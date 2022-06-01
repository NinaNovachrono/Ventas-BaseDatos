/*
Control_Facturas
07/08/21
Jesus Raul Zatarain Rendon
 */
package Controladores;

import Modelos.Modelo_Facturas;
import Vistas.Consulta_Facturas;
import Vistas.SeleccionVista;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Personal
 */
public class Control_Facturas implements MouseListener, WindowListener{
    private Modelo_Facturas modelo;
    private Consulta_Facturas vista;
    private SeleccionVista vista_anterior;

    public Control_Facturas(Modelo_Facturas modelo, Consulta_Facturas vista, SeleccionVista vista_anterior){
        this.modelo = modelo;
        this.vista=vista;
        this.vista_anterior = vista_anterior;
        this.vista.addWindowListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("Consulta de facturas");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.tb_Facturas.setModel(modelo.usuarioConsultar());
        vista.setVisible(true);
    }
    
    @Override
    public void windowClosing(WindowEvent we) {
        vista_anterior.setVisible(true);
    }    
        
    //Métodos innecesarios
    @Override
    public void mouseClicked(MouseEvent me) {
    }

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
