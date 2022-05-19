package Controlador;

import java.io.IOException;

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
    boolean barra = true;
    //boolean 
    
    GaussianBlur blur = new GaussianBlur(0);
    Timeline timer;
    Timeline apear;
    Timeline disapear;
    
    @FXML public void initialize() {
    	setIcono(new Image("file:C:\\Users\\Mikel\\Desktop\\TLPBL4/Andoni.jpeg", 295, 280, false, false));
    	setDialog(new Image("file:C:\\Users\\Mikel\\Desktop\\TLPBL4/Check.jpg", 295, 280, false, false));
    	Panel.setEffect(blur);
    	Panel.setEffect(blur);
    }
    
    @FXML private void CambiarScena() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/scene/StandBy.fxml"));
    	Scene scene = Parent.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
    	Parent.getChildren().add(root);
    	
    	Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.9), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            Parent.getChildren().remove(Container);
        });
        timeline.play();
    }
    
    private void setIcono(Image imagen) {
    	ImagePattern pattern = new ImagePattern(imagen);
        Icono.setFill(pattern);
    }
    
    private void setDialog(Image imagen) {
    	ImagePattern pattern = new ImagePattern(imagen);
        Dialog.setFill(pattern);
    }
    
    @FXML private void Enfocar() {
    	if(timer != null) {
	    	timer.stop();
	    	timer = new Timeline(new KeyFrame(Duration.millis(10), e->{
	    		if(blur.getRadius() > 0) {
	    			blur.setRadius(blur.getRadius() - 0.2);
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
    
    @FXML private void Desenfocar() {
    	if(timer != null) {
    		timer.stop();
    	}
    	timer = new Timeline(new KeyFrame(Duration.millis(10), e->{
			if(blur.getRadius() < 15) {
				blur.setRadius(blur.getRadius() + 0.08);
				Panel.setEffect(blur);
				Progresion.setVisible(true);
				Progresion.setProgress(blur.getRadius()/15);
			}else {
				barra = false;
				Progresion.setVisible(false);
				timer.stop();
				timer.getKeyFrames().clear();
				if(apear == null) {
					crearDialogo();
				}
			}
		}));
		timer.setCycleCount(Animation.INDEFINITE);
		timer.play();
	}
    
    private void crearDialogo() {
    	apear = new Timeline(new KeyFrame(Duration.millis(15), e->{
    		if(Dialog.getOpacity() < 0.75) {
    			Dialog.setOpacity(Dialog.getOpacity() + 0.015);
    			Dark.setOpacity(Dark.getOpacity() + 0.007);
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
		disapear = new Timeline(new KeyFrame(Duration.millis(10), y->{
    		if(Dialog.getOpacity() >= 0) {
    			Dialog.setOpacity(Dialog.getOpacity() - 0.02);
    			Dark.setOpacity(Dark.getOpacity() - 0.01);
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

}

