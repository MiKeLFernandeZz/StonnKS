package Controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Modelo.FechaYHora;
import Modelo.FuncionesAplicacion.TipoDescanso;
import application.Main;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ControladorDescanso {

    @FXML private AnchorPane Container;
    @FXML private AnchorPane Parent;
    @FXML private Button btn_IniciarDescanso;
    @FXML private ListView<String> lista;
    List<String> listaOpciones;
    
    @FXML public void initialize() {
    	lista.getStylesheets().add(getClass().getResource("/CSS/CellStyles.css").toExternalForm());
    	listaOpciones = new ArrayList<>();
    	lista.setCellFactory(e -> new ListCell<>() {
    		@Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setPrefHeight(55);
                if (empty) {
                	setPrefHeight(0);
                    setText(null);
                } else {
                	setPrefHeight(45);
                    setText(item);
                }
            }
    	});
    	
    	listaOpciones.add(TipoDescanso.ALMUERZO.toString());
    	listaOpciones.add(TipoDescanso.COMIDA.toString());
    	listaOpciones.add(TipoDescanso.TOMARAIRE.toString());
    	listaOpciones.add(TipoDescanso.URGENCIA.toString());
    	lista.getItems().addAll(listaOpciones);
    }
    
    @FXML void iniciarDescanso(MouseEvent event) throws IOException{
    	System.out.println("Iniciar Descanso");
    	int i = lista.getSelectionModel().getSelectedIndex();
    	if(i > 0) {
    		System.out.println(i);
        	String s = listaOpciones.get(i);
        	FechaYHora dt = new FechaYHora();
        	ControladorBaseDatos.getAplicacion().iniciarDescanso(
    			Main.getTrabajadorID(), dt.getHoraBase(),
    			TipoDescanso.getTipo(s));
        	cambiarEscena("/application/StandBY.fxml");
    	}
    }
    
    private void cambiarEscena(String s) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(s));
    	Scene scene = Parent.getScene();
    	root.translateYProperty().set(-scene.getHeight());
    	
    	Parent.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.9), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            Parent.getChildren().remove(Container);
            timeline.stop();
        });
        timeline.play();
    }
}
