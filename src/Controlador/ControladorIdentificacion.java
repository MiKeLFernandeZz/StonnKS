package Controlador;

import java.io.File;
import java.io.IOException;

import Modelo.FechaYHora;
import Modelo.FuncionesAplicacion.TipoDescanso;
import Modelo.Tiempo;
import application.Main;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ControladorIdentificacion {

    @FXML private Rectangle Icono;
    @FXML private AnchorPane Panel;
    @FXML private AnchorPane Parent;
    @FXML private ProgressBar Progresion;
    @FXML private Rectangle Dialog;
    @FXML private Rectangle Dark;
    @FXML private AnchorPane Container;
    @FXML private Button actividad;
    @FXML private Button descanso;
    @FXML private Button jornada;
    @FXML private Label lbl_Actividad;
    @FXML private Label lbl_Diario;
    @FXML private Label lbl_Semanal;
    
    boolean barra = true;
    String file;
    Estado estado;
    
    GaussianBlur blur = new GaussianBlur(0);
    Timeline timer;
    Timeline apear;
    Timeline disapear;
    
    @FXML public void initialize() {
    	if(System.getProperty("os.name").startsWith("Windows"))
			file = "file:\\";
		else
			file = "file://";
    	//TODO cambiar los datos con QUERYS
    	establecerBotones();
    	establecerDatos();
    	setIcono(new Image(file + new File("icons/Andoni.jpeg").getAbsolutePath(), 295, 280, false, false));
    	setDialog(new Image(file + new File("icons/Check.jpg").getAbsolutePath(), 295, 280, false, false));
    	Panel.setEffect(blur);
    	//ComprobarEstado
    }
    
    private void establecerDatos() {
    	//TODO cambiar todo esto
		lbl_Actividad.setText("Actividad: " + 
			ControladorBaseDatos.getOutput().sacarUltimaActividadDelParte(
			ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID())));
//		lbl_Diario.setText("Horas trabajadas hoy" + 
//			ControladorBaseDatos.get);
		
	}

	@FXML private void cambiarEscenaActividad() throws IOException{
    	cambiarEscena("/application/Actividad.fxml");
    }
	
	@FXML private void descanso() {
		if (descanso.getText().equals("Iniciar Descanso")) {
			FechaYHora dt = new FechaYHora();
			ControladorBaseDatos.getAplicacion().iniciarDescanso(Main.getTrabajadorID(), dt.getHoraBase(), TipoDescanso.ALMUERZO);
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
    
    @FXML private void iniciarJornada() {
    	estado = Estado.JORNADAINICIADA;
    	FechaYHora dt = new FechaYHora();
    	establecerBotones();
    	ControladorBaseDatos.getAplicacion().iniciarJornada(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase(), Main.getActividadID());
    }
    
    @FXML private void finalizarJornada() {
    	estado = Estado.JORNADASININICIAR;
    	establecerBotones();
    	FechaYHora dt = new FechaYHora();
    	ControladorBaseDatos.getAplicacion().finalizarJornada(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase());
    }
    
    private void establecerBotones() {
    	switch(estado) {
    	case JORNADASININICIAR:{
    		descanso.setDisable(true);
    		jornada.setText("Iniciar Jornada");
    		descanso.setVisible(false);
    		descanso.setText("Iniciar Descanso");
    	}break;
    	case JORNADAINICIADA:{
    		descanso.setDisable(false);
    		jornada.setText("Finalizar Jornada");
    		descanso.setVisible(true);
    		descanso.setText("Iniciar Descanso");
    	}break;
    	case DESCANSO:{
    		descanso.setDisable(false);
    		jornada.setText("Finalizar Jornada");
    		jornada.setVisible(true);
    		descanso.setText("Finalizar Descanso");
    	}break;
    	}
    }
    
    private void setIcono(Image imagen) {
    	ImagePattern pattern = new ImagePattern(imagen);
        Icono.setFill(pattern);
    }
    
    private void setDialog(Image imagen) {
    	ImagePattern pattern = new ImagePattern(imagen);
        Dialog.setFill(pattern);
    }
    
    @FXML private void enfocar() {
    	if(timer != null) {
	    	timer.stop();
	    	timer = new Timeline(new KeyFrame(Duration.millis(30), e->{
	    		if(blur.getRadius() > 0) {
	    			blur.setRadius(blur.getRadius() - 0.6);
	    			Panel.setEffect(blur);
	    			if(blur.getRadius()>0 && barra == true)
	    				Progresion.setProgress(blur.getRadius()/15);
	    			else {
	    				Progresion.setProgress(0);
	    			}
	    		}else {
	    			barra = true;
	    			Progresion.setVisible(false);
	    			timer.stop();
	    			timer.getKeyFrames().clear();
	    		}
			}));
	    	timer.setCycleCount(Animation.INDEFINITE);
	    	timer.play();
    	}
    }
    
    @FXML private void desenfocar() {
    	if(timer != null) {
    		timer.stop();
    	}
    	timer = new Timeline(new KeyFrame(Duration.millis(30), e->{
			if(blur.getRadius() < 15) {
				blur.setRadius(blur.getRadius() + 0.24);
				Panel.setEffect(blur);
				Progresion.setVisible(true);
				Progresion.setProgress(blur.getRadius()/15);
			}else {
				barra = false;
				Progresion.setVisible(false);
				timer.stop();
				timer.getKeyFrames().clear();
				//TODO esta mierda esta un poco xd
				if(jornada.getText().equals("Iniciar Jornada"))
					iniciarJornada();
				else
					finalizarJornada();
				if(apear == null) {
					crearDialogo();
				}
			}
		}));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
	}
    
    private void crearDialogo() {
    	apear = new Timeline(new KeyFrame(Duration.millis(30), e->{
    		if(Dialog.getOpacity() < 0.75) {
    			Dialog.setOpacity(Dialog.getOpacity() + 0.03);
    			Dark.setOpacity(Dark.getOpacity() + 0.014);
    		}
    		else {
    			System.out.println("Saliendo");
    			apear.stop();
    			apear.getKeyFrames().clear();
    			delay(1500, () -> ocultarDialog());
    		}
		}));
    	apear.setCycleCount(Animation.INDEFINITE);
    	apear.play();
    	
    	
    }
    
    private void ocultarDialog() {
		disapear = new Timeline(new KeyFrame(Duration.millis(30), y->{
    		if(Dialog.getOpacity() >= 0) {
    			Dialog.setOpacity(Dialog.getOpacity() - 0.06);
    			Dark.setOpacity(Dark.getOpacity() - 0.03);
    		}
    		else {
    			Dark.setOpacity(0);
    			disapear.stop();
    			disapear.getKeyFrames().clear();
    			apear = null;
    		}
		}));
		disapear.setCycleCount(Animation.INDEFINITE);
		disapear.play();
    }
    
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
      }
    
    public enum Estado{
    	JORNADASININICIAR,
    	JORNADAINICIADA,
    	DESCANSO
    }
}

