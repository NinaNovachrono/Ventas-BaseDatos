/*
    Sánchez Plazola José Abraham
    03/08/2021
    Controlador para el reproductor de música (Aplicación JavaFX)
*/
package Controladores;

import Modelos.Modelo_Reproductor;
import Vistas.CrearListas;
import Vistas.Reproductor_Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import Interfaces.Song;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorReproductorXML implements ActionListener, MouseListener, KeyListener{

    //Atributos del reproductor, identificacion de usuario, lista de canciones y playlists, reproductor y vistas
    public int usuarioId;
    private ObservableList<Song> songs;
    private ObservableList<String> playlist;
    private MediaPlayer player;
    private DirectoryChooser directoryChooser;
    private Modelo_Reproductor modelo;
    private CrearListas vista;
    private Reproductor_Login vista_anterior;
    
    //Vista del reproductor
    @FXML private VBox mainVBox;
    
    @FXML private ListView<Song> songListView;
    @FXML private ListView<String> playlistView;
    @FXML private Label currentSong;

    //Constructor
    public void initialize() {
        try {
            vista = new CrearListas();
            this.modelo = new Modelo_Reproductor();
            this.vista.tablaSeleccion.addMouseListener(this);
            this.vista.tablaPlaylist.addMouseListener(this);
            this.vista.btnConfirmar.addMouseListener(this);
            this.vista.btnRegresar.addMouseListener(this);
            this.vista.txtNombre.addKeyListener(this);
            
            //HACER LA RELACION CON EL LOGIN PARA OBTENER EL USUARIO
            this.usuarioId = 1;
            
            //Configuracion de la ventana para elegir directorios
            directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Escoge un directorio");
            directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            
            // Inicializacion de la lista de canciones
            songs = FXCollections.observableArrayList();
            songListView.setItems(songs);
            songListView.getSelectionModel().selectFirst();
            
            //Inicializacion de la lista de playlists
            playlist = FXCollections.observableArrayList();
            playlistView.setItems(playlist);
            playlistView.getSelectionModel().selectFirst();
            
            //Agregar las playlists a la vista
            this.loadPlaylists();
            
            //Mostrar el reproductor
            Main.run(this.usuarioId);
        } catch (SQLException ex) {
            System.out.println("Error " + ex);
        }
    }

    //Método para reproducir las canciones
    @FXML
    public void play() {
        //Obtiene la cancion seleccionada de la vista de la lista
        Song song = songListView.getSelectionModel().getSelectedItem();
        
        //Añade la cancion al reproductor
        Media media = new Media(song.getUrl().toExternalForm());
        
        //Comienza la reproduccion
        if (player == null) {
            player = new MediaPlayer(media);
            player.play();
            currentSong.setText(song.getName());
            modelo.AddBiblioteca(song.getName().replace(".mp3", ""), this.usuarioId);
            return;
        }
        
        //Si está tocando una canción cuando se llama entonces detiene la primera y añade la segunda
        if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.stop();
        }
        player = new MediaPlayer(media);
        player.play();
        currentSong.setText(song.getName());
        modelo.AddBiblioteca(song.getName().replace(".mp3", ""), this.usuarioId);
    }
    
    //Método para detener la canciones que esta tocando
    @FXML
    public void stop() {
        if (player == null) {
            return;
        } else if (player.getStatus() == MediaPlayer.Status.PLAYING) {
            player.stop();
        }
    }
    
    //Método para ocultar la vista y back a la anterior
    @FXML
    public void back(){
        if(player != null) {
            player.stop();
        }
        Main.run(this.usuarioId);
        this.getVista_anterior().setVisible(true);
    }
    
    //Método para agregar un directorio local al reproductor de música
    @FXML
    public void addDirectory() throws MalformedURLException{
        songs.clear();
        //Abre la ventana para escoger directorios
        File directory = directoryChooser.showDialog(mainVBox.getScene().getWindow());
        
        //Crea un objeto "File" para el directorio
        //directoryPath = new File("D:\\Torrents\\VA - Bossa Nova Essentials (2021) Mp3 320kbps [PMEDIA] ⭐️");
        
        //Lista todos los archivos
        File filesList[] = directory.listFiles();
        
        //Lista los archivos de la array
        for(File file: filesList) {
            //Añade cada archivo a la lista de canciones
           songs.add(new Song(file.getName(), file.toURI().toURL()));
        }
        //Actualiza la lista en la vista con los contenidos añadidos
        songListView.setItems(songs);
    }
    
    //Método para mostrar la biblioteca entera del reproductor
    @FXML
    public void showBiblioteca() throws MalformedURLException, SQLException{
        songs.clear();
        File song;
        ResultSet canciones;
        
        canciones = modelo.getPath();
        
        while(canciones.next()){
            song = new File((String) canciones.getObject(1));
            if (song != null) {
                //Se agrega el archivo a la lista y después se actualiza
                songs.add(new Song(song.getName(), song.toURI().toURL()));
                
            } else {
                System.out.println("File not found");
            }
        }
        songListView.setItems(songs);
        canciones.close();
    }
    
    //Método para mostrar las playlists en su tabla
    @FXML
    public void loadPlaylists() throws SQLException{
        playlist.clear();
        ResultSet playlists_rs;
        String playlist_nombre;
        playlists_rs = modelo.getPlaylist();
        
        while(playlists_rs.next()){
            playlist_nombre = (String) playlists_rs.getObject(1);
            playlist.add(playlist_nombre);
        }
        playlistView.setItems(playlist);
        playlists_rs.close();
    }
    
    //Método para mostrar las canciones contenidas en una playlist
    public void loadPlaylistSongs() throws SQLException, MalformedURLException{
        songs.clear();
        File song;
        String playlist_nombre = playlistView.getSelectionModel().getSelectedItem();
        ResultSet song_rs = modelo.getSongsFromPlaylist(playlist_nombre);
        
        while(song_rs.next()){
            song = new File((String) song_rs.getObject(1));
            if (song != null) {
                //Se agrega el archivo a la lista y después se actualiza
                songs.add(new Song(song.getName(), song.toURI().toURL()));
                
            } else {
                System.out.println("File not found");
            }
        }
        song_rs.close();
    }
    
    //Método para mostrar el historial de reproducciones del usuario activo
    @FXML
    public void loadUserBiblio() throws MalformedURLException, SQLException{
        songs.clear();
        File song;
        ResultSet biblio;
        biblio = modelo.ConsultarHistorial(this.usuarioId);
        
        while(biblio.next()){
            song = new File((String) biblio.getObject(1));
            if(song != null){
                songs.add(new Song(song.getName(), song.toURI().toURL()));
            } else {
                System.out.println("File not found");
            }
        }
        biblio.close();
    }
    
    //Método para abrir la vista de creación de playlists y cargar los datos necesarios
    @FXML
    public void crearPlaylist(){
        try {
            DefaultTableModel dtm = (DefaultTableModel) vista.tablaSeleccion.getModel();
            dtm.setRowCount(0);
            
            vista.setVisible(true);
            ResultSet rs = modelo.getNames();
            
            while(rs.next()){
                Object[] fila = new Object[1];
                fila[0] = rs.getObject(1);
                dtm.addRow(fila);
            }
            
            vista.tablaSeleccion.setModel(dtm);
            rs.close();
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }
    
    //Listener para la tecla "Enter" en el cuadro de texto de creacion de playlists
    //Si el nombre ya existe como playlist, carga las canciones de ella
    @Override
    public void keyPressed(KeyEvent ke) {
        //Codigo para revisar si se presiono la tecla enter en el nombre de la playlist a crear
        //Al confirmar el nombre de la playlist en la ventana de creación:
        //  Si ya existe una con ese nombre, la añade a la selección para modificarla
        //  Si no, deja la tabla vacía
        if(ke.getKeyCode() == 10){
            try {
                DefaultTableModel dtm = (DefaultTableModel) vista.tablaPlaylist.getModel();
                dtm.setRowCount(0);
                String nombre = this.vista.txtNombre.getText();
                ResultSet rs;
                rs = this.modelo.sentencia_rs("select nombre from canciones "
                        + "inner join cancionesenlista on idcanciones = canciones_idcanciones "
                        + "inner join listarepro on listarepro_idlistarepro = idlistarepro  "
                        + "where listarepro.nombrelista = '" + nombre + "';");
                //rs = this.modelo.getSongsFromPlaylist(nombre);
                if(rs != null){
                    while(rs.next()){
                        Object[] f = new Object[1];
                        f[0] = rs.getObject(1);
                        dtm.addRow(f);
                    }   
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
                ex.printStackTrace();
            }
            
        }
    }
    
    //Controlador de botones de la vista creación de playlists
    @Override
    public void mouseClicked(MouseEvent e) {
        DefaultTableModel dtm = (DefaultTableModel) vista.tablaPlaylist.getModel();
        
        //Método para añadir canciones a la playlist tentativa
        if(this.vista.tablaSeleccion == e.getSource()){
            int fila = vista.tablaSeleccion.rowAtPoint(e.getPoint());
            if(fila > -1){
                Object[] f = new Object[1];
                f[0] = vista.tablaSeleccion.getValueAt(fila, 0);
                dtm.addRow(f);  

            }
        //Método para eliminar canciones de la playlist tentativa
        } else if(this.vista.tablaPlaylist == e.getSource()){
            int fila = vista.tablaPlaylist.rowAtPoint(e.getPoint());
            if(fila > -1){
                dtm.getValueAt(fila, 0);
                dtm.removeRow(fila);  
            }
        //Método para confirmar la creación de playlist y actualizar o crear la playlist en la base de datos, según corresponda
        } else if(this.vista.btnConfirmar == e.getSource()){
            String nombre = this.vista.txtNombre.getText();
            
            //Validación de que hay más de una canción en la playlist y el nombre no es vacío
            if(dtm.getRowCount() > 1 && !nombre.trim().isEmpty()){
                
                //Si no se puede registrar
                if(!this.modelo.RegistrarListaReproduccion(nombre)){
                    try {
                        ResultSet rs;
                        rs = this.modelo.sentencia_rs("select nameExist('"+ nombre +"')");
                        rs.next();
                        //Si existe una playlist con el mismo nombre
                        if(rs.getInt(1) == 1){
                            //Si se pudo eliminar la playlist del mismo nombre
                            if(this.modelo.sentencia("delete from listarepro where nombrelista = '" + nombre + "';")){
                                //Si no se pudo crear la playlist con el nombre
                                if(!this.modelo.RegistrarListaReproduccion(nombre)){
                                    JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproduccion (Error de nombre)");
                                //Si sí se pudo crear la playlist con el nombre
                                } else {
                                    //Ciclo para generar las sentencias de añadir
                                    for(int i = 0; i < dtm.getRowCount() - 1; i++){
                                        //Si no se logra añadir, borra lo que haya metido y deja de operar
                                        if(!this.modelo.CancionesALista(dtm.getValueAt(i, 0).toString(), nombre)){
                                            JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproduccion (Error de canciones)");
                                            this.modelo.sentencia("delete from listarepro where nombrelista = '" + nombre + "';");
                                            return;
                                        }
                                    }
                                    
                                    JOptionPane.showMessageDialog(null, "Lista creada con éxito!");                                    
                                }
                            //Si no se pudo eliminar la playlist con el mismo nombre
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproduccion (Error al borrar)");
                            }
                        //Si no existe una playlist con el mismo nombre, no se pudo registrar por otros motivos
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproduccion (Error de nombre)");
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex);
                    }
                    
                //Si sí se pudo registrar
                } else {
                    //Ciclo para generar las sentencias de añadir
                    for(int i = 0; i < dtm.getRowCount() - 1; i++){
                        //Si no se logra añadir, borra lo que haya metido y deja de operar
                        if(!this.modelo.CancionesALista(dtm.getValueAt(i, 0).toString(), nombre)){
                            JOptionPane.showMessageDialog(null, "No se pudo crear la lista de reproduccion (Error de canciones)");
                            this.modelo.sentencia("delete from listarepro where nombrelista = '" + nombre + "';");
                            return;
                        }
                    }
                    //Sí logró añadirse
                    JOptionPane.showMessageDialog(null, "Lista creada con éxito!");
                    try {
                        //Carga de nuevo las playlists en el reproductor
                        this.loadPlaylists();
                    } catch (SQLException ex) {
                        System.out.println("Error: " + ex);
                    }
                }
            //Si la validación del nombre y numero de canciones falló
            } else {
                JOptionPane.showMessageDialog(null, "La playlist debe tener al menos dos canciones y un nombre");
            }
        //Método para cerrar la vista de creación de playlists y volver al reproductor
        } else if(this.vista.btnRegresar == e.getSource()){
           vista.setVisible(false);
           dtm.setRowCount(0);
           dtm = (DefaultTableModel) vista.tablaSeleccion.getModel();
           dtm.setRowCount(0);
       }
    }
    
    //Getter y setter para la vista anterior
    public Reproductor_Login getVista_anterior() {
        return vista_anterior;
    }

    public void setVista_anterior(Reproductor_Login vista_anterior) {
        this.vista_anterior = vista_anterior;
    }
    
    
    //Métodos innecesarios
    @Override
    public void actionPerformed(ActionEvent ae) {
        return;
    }
    @Override
    public void mousePressed(MouseEvent me) {
        return;
    }
    @Override
    public void mouseReleased(MouseEvent me) {
        return;
    }
    @Override
    public void mouseEntered(MouseEvent me) {
        return;
    }
    @Override
    public void mouseExited(MouseEvent me) {
        return;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        return;
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        return;
    }
    
}
