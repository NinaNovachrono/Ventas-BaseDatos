/*
    Sánchez Plazola José Abraham
    03/08/2021
    Clase main para realizar las pruebas necesarias del programa
*/
package Controladores;

import Vistas.Reproductor_Login;
import Vistas.SeleccionVista;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    //Variables globales stage y vistalogin
    //AMbas necesarias debido a la implementacion de la aplicacion JavaFX
    private static Stage this_primaryStage;
    private static Reproductor_Login vistalogin = new Reproductor_Login();
    private static ControladorReproductorXML controllerXML;
    
    //Método start para la aplicación javaFX, llamado al inicio del programa
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Valor para impedir que la aplicación cierre si se oculta su ventana
        Platform.setImplicitExit(false);
        
        //Cargamos la vista a la aplicación
        File path = new File("C:\\Users\\Personal\\Documents\\NetBeansProjects\\ReproductorBuenoAhoraSí\\src\\Vistas\\sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path.toURI().toURL());
        Parent root = (Parent)fxmlLoader.load();  
        
        //Cargamos el controlador
        ControladorReproductorXML controller = fxmlLoader.<ControladorReproductorXML>getController();
        
        //Colocamos que la vista anterior al reproductor sea el login
        controller.setVista_anterior(vistalogin);
        
        //Se coloca la escena y se muesrta
        primaryStage.setTitle("Reproductor PsiDelta");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();
        
        //Guardamos la escena como la variable global
        this_primaryStage = primaryStage;
        controllerXML = controller;
                //SOLO OCULTAR LA APP JAVAFX Y LUEGO HACER UN MÉTODO EN EL LOGIN QUE LE REGRESE EL ID DEL USUARIO AL HACER LOGIN
                // Y LO CAMBIE EN EL CONTROLADOR DE LA APP JAVAFX
    }

    //Función run para mostrar y ocultar la vista del reproductor según se necesite
    //Llamada desde los controladores necesarios
    public static void run(int idUsuario){ 
        Platform.runLater(() -> {
            if(this_primaryStage.isShowing()){
                this_primaryStage.hide();
                controllerXML.usuarioId = idUsuario;
            } else {
                this_primaryStage.show();
                controllerXML.usuarioId = idUsuario;
            }
        });
    }
    
    public static void main(String[] args) {
        ControladorControladores controladorChido;
        SeleccionVista vistaChida = new SeleccionVista();
        
        controladorChido = new ControladorControladores(vistaChida, vistalogin);
        
        controladorChido.iniciarVista();
        launch();
    }
}
