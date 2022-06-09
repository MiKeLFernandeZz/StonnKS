package Controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ControladorActividad {

    @FXML private Button btn_CambiarJornada;
    @FXML private ListView<String> lista;
    @FXML private AnchorPane Container;
    @FXML private AnchorPane Parent;
    @FXML private Label lbl_Actividad;
    @FXML private Label lbl_Semanal;
    @FXML private Label lbl_Diario;
    
    List<String> listaActividades;
    
    @FXML public void initialize() {
    	lista.getStylesheets().add(getClass().getResource("/CSS/CellStyles.css").toExternalForm());
    	//lista.setStyle("-fx-control-inner-background:  #4449a5;");
    	listaActividades = new ArrayList<>();
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
    	
    	for(int i = 0; i < 3; i++) {
    		listaActividades.add("Andoni");
    		listaActividades.add("Makina");
    	}
    	lista.getItems().addAll(ControladorBaseDatos.getOutput().sacarActividadesPorTrabajadorID(Main.getTrabajadorID()));
    }
    
    @FXML private void cambiarJornada() throws IOException{
    	System.out.println("Cambiar Jornada");
    	int i = lista.getSelectionModel().getSelectedIndex();
    	if(i > 0) {
    		System.out.println(i);
        	String s = listaActividades.get(i);
        	System.out.println(s);
        	//TODO QUERY para iniciar actividad
        	
        	//ControladorBaseDatos.getAplicacion().cambiarActividad(, s, i, s);
        	
        	cambiarEscena("/application/Identificacion.fxml");
    	}
    	
    }
    
    private void establecerInfo() {
    	
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
