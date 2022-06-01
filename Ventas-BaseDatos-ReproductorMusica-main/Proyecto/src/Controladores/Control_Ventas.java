/*
Descripción: Clase Modelo de la Vista Registro Ventas
 07/Agosto/2021
Manuel Alfonso Cordero Covantes TCI 6-2
 */
package Controladores;

import Modelos.Modelo_Ventas;
import Vistas.Registro_Ventas;
import Vistas.SeleccionVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author マヌエル・コルデロ
 */
public class Control_Ventas implements ActionListener, WindowListener{
    private Modelo_Ventas modelo;
    private Registro_Ventas vista;
    private SeleccionVista vista_anterior;
    private int EmpleadoId;
    private int id_factura;
        
    public Control_Ventas(Registro_Ventas vista, Modelo_Ventas modelo, SeleccionVista vista_anterior){
        this.modelo = modelo;
        this.vista=vista;
        this.vista_anterior = vista_anterior;
        this.id_factura = 0;
        this.EmpleadoId = 1;
        this.vista.btnBuscarCliente.addActionListener (this);
        this.vista.btnBuscarProducto.addActionListener (this);
        this.vista.btnFinalizar.addActionListener (this);
        this.vista.btnAdd.addActionListener(this);
        this.vista.btnBack.addActionListener(this);
        this.vista.addWindowListener(this);
    }

    public void iniciarVista() {
        vista.setTitle("Ventana Ventas");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if(vista.btnBuscarCliente == evento.getSource()) {
            if(modelo.busquedcliente(vista.TxtTelefono.getText())){
                this.vista.lblNombreVariable.setText(this.modelo.nombre);
                JOptionPane.showMessageDialog(null, "¡Cliente Encontrado!");
                if(modelo.registrarFactura(EmpleadoId, this.modelo.cliente)){
                JOptionPane.showMessageDialog(null, "Factura creada correctamente, ingrese los siguientes datos");
                } else {
                JOptionPane.showMessageDialog(null, "Fallo al crear factura");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Cliente Inexistente");
            }
            

        } else if(vista.btnBuscarProducto == evento.getSource()) {
            if(modelo.busquedaproducto(Integer.parseInt(vista.txtCodigo.getText()))){
                id_factura = modelo.busquedaId(this.modelo.cliente, EmpleadoId);
                if(id_factura == -1){
                    JOptionPane.showMessageDialog(null, "Error al generar factura");
                }
                this.vista.lblID.setText(String.valueOf(this.id_factura));
                this.vista.lblExistenciaVariable.setText(this.modelo.codd);
                this.vista.lblNombreClienteVariable.setText(this.modelo.nombre1);
                JOptionPane.showMessageDialog(null, "¡Producto Encontrado!");
            }else {
                JOptionPane.showMessageDialog(null, "Producto inexistente");
            }
        } else if(vista.btnFinalizar == evento.getSource()) {
            if(modelo.finalizar_total_factura(id_factura)){
                JOptionPane.showMessageDialog(null, "Factura finalizada");
            }else {
                JOptionPane.showMessageDialog(null, "Error al finalizar la factura");
            }
        } else if(vista.btnAdd == evento.getSource()){
            if(modelo.registrar_productos(Integer.parseInt(vista.txtCodigo.getText()),
                    Integer.parseInt(vista.txtCantidad.getText()), Integer.parseInt(this.vista.lblID.getText()))){
                JOptionPane.showMessageDialog(null, "Producto/s Registrado/s :D");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar :(");
            }
        } else if(vista.btnBack == evento.getSource()){
            this.vista.setVisible(false);
            this.vista_anterior.setVisible(true);
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
