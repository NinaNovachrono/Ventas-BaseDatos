/*
Control_Productos
07/08/21
Jesus Raul Zatarain Rendon
 */
package Controladores;


import Modelos.Modelo_Productos;
import Vistas.Consulta_Productos;
import Vistas.Registro_Productos;
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
public class Control_Productos implements WindowListener, ActionListener{
    private Modelo_Productos modelo;
    private Consulta_Productos vista_consulta;
    private Registro_Productos vista_registro;
    private SeleccionVista vista_anterior;

    public Control_Productos(Modelo_Productos modelo, Consulta_Productos vista_consulta, Registro_Productos vista_registro, SeleccionVista vista_anterior){
        this.modelo = modelo;
        this.vista_consulta=vista_consulta;
        this.vista_anterior = vista_anterior;
        this.vista_registro = vista_registro;
        this.vista_consulta.addWindowListener(this);
        this.vista_registro.addWindowListener(this);
        this.vista_registro.btnAgg.addActionListener(this);
    }

    public void iniciarVista_consulta() {
        vista_consulta.setTitle("Consulta de productos");
        vista_consulta.pack();
        vista_consulta.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista_consulta.setLocationRelativeTo(null);
        vista_consulta.tb_Productos.setModel(modelo.usuarioConsultar());
        vista_consulta.setVisible(true);
    }
    
    public void iniciarVista_registro(){
        vista_registro.setTitle("Registro de productos");
        vista_registro.pack();
        vista_registro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista_registro.setLocationRelativeTo(null);
        vista_registro.TablaRegistro.setModel(modelo.usuarioConsultar());
        vista_registro.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent evento) {
        if (vista_registro.btnAgg == evento.getSource()){
            if(modelo.RegistrarProductos(Integer.parseInt(vista_registro.IDTXT.getText()), vista_registro.NombreTXT.getText(),vista_registro.PrecioTXT.getText(), vista_registro.ExistenciaTXT.getText(),vista_registro.GeneroTXT.getText(),vista_registro.ArtistaTXT.getText())){
                JOptionPane.showMessageDialog(null, "Registrado Exitosamente");
            }else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el Producto");
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
