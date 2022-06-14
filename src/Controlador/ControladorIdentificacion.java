package Controlador;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import Modelo.FechaYHora;
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

public class ControladorIdentificacion{

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
    @FXML private Label lbl_Empresa;
    @FXML private Label lbl_Nombre;
    @FXML private Label lbl_Puesto;
    
    boolean barra = true;
    String file;
    Estado estado;
    
    GaussianBlur blur = new GaussianBlur(0);
    Timeline timer;
    Timeline apear;
    Timeline disapear;
    Timeline inactividad;
    
    @FXML public void initialize() {
    	if(System.getProperty("os.name").startsWith("Windows"))
			file = "file:\\";
		else
			file = "file://";
    	establecerEstado();
    	establecerBotones();
    	establecerDatos();
    	establecerTimer();
    	establecerImagenes();
    	Panel.setEffect(blur);
    }
    
    private void establecerImagenes() {
    	URL url;
    	File f = new File("icons/" + Main.getTrabajadorID() + ".jpg");
    	if(!f.exists()) { 
    		System.out.println("Imagen no encontrada");
			try {
				url = new URL("http://" + Main.getIP() + ":8090/" + Main.getTrabajadorID() + ".jpg");
				System.out.println(url);
				InputStream in = new BufferedInputStream(url.openStream());
		    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		    	byte[] buf = new byte[1024];
		    	int n = 0;
		    	while (-1!=(n=in.read(buf)))
		    	{
		    	   out.write(buf, 0, n);
		    	}
		    	out.close();
		    	in.close();
		    	
		    	byte[] response = out.toByteArray();
		    	FileOutputStream fos = new FileOutputStream(f.getAbsoluteFile());
		    	fos.write(response);
		    	fos.close();
	
				//Thread.sleep(1000);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		
    	setIcono(new Image(file + new File("icons/" + Main.getTrabajadorID() + ".jpg").getAbsolutePath(), 295, 280, false, false));
    	setDialog(new Image(file + new File("icons/Check.jpg").getAbsolutePath(), 295, 280, false, false));
    	
		
	}

	private void establecerTimer(){
    	timer = new Timeline(new KeyFrame(Duration.seconds(30), e->{
    		try {
				cambiarEscena("/application/StandBy.fxml");
				timer.stop();
    			timer.getKeyFrames().clear();
    			timer = null;
    			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}));
    	timer.setCycleCount(Animation.INDEFINITE);
    	timer.play();
	}

	private void establecerEstado() {
		if(ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID())!= 0) {
			if(ControladorBaseDatos.getOutput().sacarDescansoAbiertoPorJornadaID(
					ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID())) != 0) {
				estado = Estado.DESCANSO;
			}else {
				estado = Estado.JORNADAINICIADA;
			}
		}else {
			estado = Estado.JORNADASININICIAR;
		}
	}

	private void establecerDatos() {
		FechaYHora dt = new FechaYHora();
		lbl_Nombre.setText(ControladorBaseDatos.getOutput().NombrePorTrabajadorID(Main.getTrabajadorID()) + " "
				+ ControladorBaseDatos.getOutput().ApellidoPorTrabajadorID(Main.getTrabajadorID()));
		
		lbl_Empresa.setText(ControladorBaseDatos.getOutput().EmpresaDelTrabajadorID(Main.getTrabajadorID()));
		
		lbl_Puesto.setText(ControladorBaseDatos.getOutput().PuestoDelTrabajadorID(Main.getTrabajadorID()));
		
		if(ControladorBaseDatos.getOutput().sacarUltimaActividadDelParte(
			ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID()))>0){
			System.out.println(ControladorBaseDatos.getOutput().sacarUltimaActividadDelParte(
					ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID())));
				lbl_Actividad.setText("Actividad: " + 
					ControladorBaseDatos.getOutput().NombrePorActividadID(
					ControladorBaseDatos.getOutput().sacarUltimaActividadDelParte(
					ControladorBaseDatos.getOutput().buscarJornadaActual(Main.getTrabajadorID()))));
		}else {
				lbl_Actividad.setText("Actividad: " + 
					ControladorBaseDatos.getOutput().NombrePorActividadID(
							Main.getActividadID()));
		}
		
		lbl_Diario.setText("Horas tabajadas hoy: " + ControladorBaseDatos.getAplicacion()
			.sacarHorasDeUnaJornada(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase()) / 60
			+ "h " + ControladorBaseDatos.getAplicacion()
			.sacarHorasDeUnaJornada(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase()) %60
			+ "min");
		
		lbl_Semanal.setText("Horas semanales: " + ControladorBaseDatos.getAplicacion()
			.sacarHorasDeLaSemana(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase()) / 60
			+ "h " + ControladorBaseDatos.getAplicacion()
			.sacarHorasDeLaSemana(Main.getTrabajadorID(), dt.getFechaBase(), dt.getHoraBase()) %60
			+ "min");
		
	}

	//TODO cambiar a la pantalla de eleccion de actividad(ControladorActividad), al pulsar el boton
	@FXML private void cambiarEscenaActividad() throws IOException{
    	cambiarEscena("/application/Actividad.fxml");
    }
	
	@FXML private void descanso() throws IOException{
		FechaYHora dt = new FechaYHora();
		if (descanso.getText().equals("Iniciar Descanso")) {
			cambiarEscena("/application/Descanso.fxml");
		}else {
			ControladorBaseDatos.getAplicacion().finalizarDescanso(Main.getTrabajadorID(), dt.getHoraBase());
			estado = Estado.JORNADAINICIADA;
			establecerBotones();
		}
	}
    
    private void cambiarEscena(String s) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource(s));
    	Scene scene = Parent.getScene();
    	
    	if(Main.isAnimacion()) {
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
    	}else {
    		Parent.getChildren().add(root);
    		Parent.getChildren().remove(Container);
    	}
    	
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
    	if(Main.isAnimacion()) {
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
    	}else {
    		if(jornada.getText().equals("Iniciar Jornada"))
				iniciarJornada();
			else
				finalizarJornada();
    		try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
	}
    
    private void crearDialogo() {
    	apear = new Timeline(new KeyFrame(Duration.millis(30), e->{
    		if(Dialog.getOpacity() < 0.75) {
    			Dialog.setOpacity(Dialog.getOpacity() + 0.03);
    			Dark.setOpacity(Dark.getOpacity() + 0.014);
    		}
    		else {
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

