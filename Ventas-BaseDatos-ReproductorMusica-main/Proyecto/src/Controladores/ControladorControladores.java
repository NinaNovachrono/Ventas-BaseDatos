/*
    Sánchez Plazola José Abraham
    03/08/2021
    Controlador de los controladores utilizado para crear nuevas instancias de ellos e iniciar las vistas correspondientes
*/
package Controladores;

import Modelos.*;
import Vistas.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Personal
 */
public class ControladorControladores implements ActionListener{
    //Atributos vista y vistalogin
    //Vistalogin 
    private SeleccionVista vista;
    private Reproductor_Login vistalogin;
    
    //Constructor de parametros y agregar listener a los botones necesarios
    public ControladorControladores(SeleccionVista vista, Reproductor_Login vistalogin){
        this.vista = vista;
        this.vistalogin = vistalogin;
        this.vista.btnConsultaEmpleados.addActionListener(this);
        this.vista.btnConsultaClientes.addActionListener(this);
        this.vista.btnConsultaFacturas.addActionListener(this);
        this.vista.btnConsultaProductos.addActionListener(this);
        this.vista.btnLogin.addActionListener(this);
        this.vista.btnRegistroClientes.addActionListener(this);
        this.vista.btnRegistroEmpleados.addActionListener(this);
        this.vista.btnRegistroProductos.addActionListener(this);
        this.vista.btnRegistroVentas.addActionListener(this);
    }
    
    //Inicializar vista
    public void iniciarVista(){
        vista.setTitle("Selección de vista");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    //Creación de las vistas de acuerdo al botón presionado
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(vista.btnLogin == ae.getSource()){
            this.vista.setVisible(false);
            
            ControladorLogin controlador;

            Modelo_Reproductor modelo_reproductor = new Modelo_Reproductor();
            controlador = new ControladorLogin(this.vistalogin, modelo_reproductor, this.vista);

            controlador.iniciarVista();
        } else if(vista.btnRegistroEmpleados == ae.getSource()){
            this.vista.setVisible(false);
            Control_Empleados controlador;
            Modelo_Empleados modelo = new Modelo_Empleados();
            Registro_Empleados vista_registro = new Registro_Empleados();
            Consulta_Empleados vista_consulta = new Consulta_Empleados();
            
            controlador = new Control_Empleados(modelo, vista_consulta, vista_registro, this.vista);

            controlador.iniciarVista_Registro();
        } else if(vista.btnConsultaEmpleados == ae.getSource()){
            this.vista.setVisible(false);
            Control_Empleados controlador;
            Modelo_Empleados modelo = new Modelo_Empleados();
            Registro_Empleados vista_registro = new Registro_Empleados();
            Consulta_Empleados vista_consulta = new Consulta_Empleados();
            
            controlador = new Control_Empleados(modelo, vista_consulta, vista_registro, this.vista);

            controlador.iniciarVista_Consulta();
        } else if(vista.btnConsultaFacturas == ae.getSource()){
            this.vista.setVisible(false);
            Control_Facturas controlador;
            Modelo_Facturas modelo = new Modelo_Facturas();
            Consulta_Facturas vista = new Consulta_Facturas();
            
            controlador = new Control_Facturas(modelo, vista, this.vista);
            
            controlador.iniciarVista();
        } else if(vista.btnConsultaProductos == ae.getSource()){
            this.vista.setVisible(false);
            Control_Productos controlador;
            Modelo_Productos modelo = new Modelo_Productos();
            Consulta_Productos vista_consulta = new Consulta_Productos();
            Registro_Productos vista_registro = new Registro_Productos();
            
            controlador = new Control_Productos(modelo, vista_consulta, vista_registro, this.vista);
            
            controlador.iniciarVista_consulta();
        } else if(vista.btnRegistroClientes == ae.getSource()){
            this.vista.setVisible(false);
            Control_Clientes controlador;
            Modelo_Clientes modelo = new Modelo_Clientes();
            Registro_Clientes vista = new Registro_Clientes();
            Consulta_Clientes vista_consulta = new Consulta_Clientes();
            
            controlador = new Control_Clientes(modelo, vista, vista_consulta, this.vista);
            
            controlador.iniciarVista_registro();
        } else if(vista.btnRegistroVentas == ae.getSource()) {
            this.vista.setVisible(false);
            Control_Ventas controlador;
            Modelo_Ventas modelo = new Modelo_Ventas();
            Registro_Ventas vista = new Registro_Ventas();
            
            controlador = new Control_Ventas(vista, modelo, this.vista);
            
            controlador.iniciarVista();
        } else if(vista.btnRegistroProductos == ae.getSource()) {
            this.vista.setVisible(false);
            Control_Productos controlador;
            Modelo_Productos modelo = new Modelo_Productos();
            Consulta_Productos vista_consulta = new Consulta_Productos();
            Registro_Productos vista_registro = new Registro_Productos();
            
            controlador = new Control_Productos(modelo, vista_consulta, vista_registro, this.vista);
            
            controlador.iniciarVista_registro();
        } else if(vista.btnConsultaClientes == ae.getSource()) {
            this.vista.setVisible(false);
            Control_Clientes controlador;
            Modelo_Clientes modelo = new Modelo_Clientes();
            Registro_Clientes vista = new Registro_Clientes();
            Consulta_Clientes vista_consulta = new Consulta_Clientes();
            
            controlador = new Control_Clientes(modelo, vista, vista_consulta, this.vista);
            
            controlador.iniciarVista_consulta();
        }
    }
}
