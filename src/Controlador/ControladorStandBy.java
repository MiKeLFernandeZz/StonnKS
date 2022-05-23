package Controlador;

import java.io.IOException;
import java.text.DecimalFormat;

import Modelo.FechaYHora;
import Modelo.Tiempo;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class ControladorStandBy{

    @FXML private Label Dia_Sem_1;
    @FXML private Label Dia_Sem_2;
    @FXML private Label Dia_Sem_3;
    @FXML private Label Dia_Sem_4;
    @FXML private Label Dia_Sem_5;
    @FXML private Label Dia_Sem_6;
    @FXML private Label Dia_Sem_7;
    @FXML private ImageView Icon_Sem_1;
    @FXML private ImageView Icon_Sem_2;
    @FXML private ImageView Icon_Sem_3;
    @FXML private ImageView Icon_Sem_4;
    @FXML private ImageView Icon_Sem_5;
    @FXML private ImageView Icon_Sem_6;
    @FXML private ImageView Icon_Sem_7;
    @FXML private Label LB_Hora;
    @FXML private Label LB_Min;
    @FXML private AnchorPane PNL_Hora;
    @FXML private AnchorPane PNL_Tiempo;
    @FXML private Label LB_Fecha;
    @FXML private ImageView Icon_Actual;
    @FXML private Label LB_Temp;
    @FXML private Label Temp_Sem_1;
    @FXML private Label Temp_Sem_2;
    @FXML private Label Temp_Sem_3;
    @FXML private Label Temp_Sem_4;
    @FXML private Label Temp_Sem_5;
    @FXML private Label Temp_Sem_6;
    @FXML private Label Temp_Sem_7;
    @FXML private AnchorPane Parent;
    @FXML private AnchorPane Container;
    
    FechaYHora dt;
    Timeline timer;
    Tiempo tiempo;
    
    private static final DecimalFormat df = new DecimalFormat("0.0");
    
    @FXML public void initialize() {
		System.out.println("Application started");
		tiempo = new Tiempo();
		establecerFechaYHora();
		establecerTiempo();
		establecerTiempoActual();
		establecerTemperaturas();
		if(timer == null) {
			timer = new Timeline(new KeyFrame(Duration.millis(dt.getMilisParaMinuto()), e->{
				establecerFechaYHora();
				establecerTiempo();
				establecerTiempoActual();
			}), new KeyFrame(Duration.minutes(1)));
			timer.setCycleCount(Animation.INDEFINITE);
			timer.play();
		}
	}
    
    @FXML private void CambiarScena() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/scene/Identificacion.fxml"));
    	Scene scene = Parent.getScene();
    	root.translateYProperty().set(scene.getHeight());
    	
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
    
    private void establecerFechaYHora() {
    	dt = new FechaYHora();
    	LB_Fecha.setText(dt.getFecha());
    	LB_Hora.setText(dt.getHora());
		LB_Min.setText(dt.getMinuto());
		establecerDias();
    }
    
    private void establecerHora() {
    	LB_Hora.setText(dt.getHora());
		LB_Min.setText(dt.getMinuto());
    }
    
    private void establecerDias() {
    	int dia = dt.getDiaSemana();
    	Dia_Sem_1.setText("Miercoles");
    	establecerDia(Dia_Sem_1, dia%7);
    	establecerDia(Dia_Sem_2, ++dia%7);
    	establecerDia(Dia_Sem_3, ++dia%7);
    	establecerDia(Dia_Sem_4, ++dia%7);
    	establecerDia(Dia_Sem_5, ++dia%7);
    	establecerDia(Dia_Sem_6, ++dia%7);
    	establecerDia(Dia_Sem_7, ++dia%7);
    }
    
    private void establecerTemperaturas() {
    	Temp_Sem_1.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(1), (double)tiempo.getTemperaturaMaxima(1)));
    	Temp_Sem_2.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(2), (double)tiempo.getTemperaturaMaxima(2)));
    	Temp_Sem_3.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(3), (double)tiempo.getTemperaturaMaxima(3)));
    	Temp_Sem_4.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(4), (double)tiempo.getTemperaturaMaxima(4)));
    	Temp_Sem_5.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(5), (double)tiempo.getTemperaturaMaxima(5)));
    	Temp_Sem_6.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(6), (double)tiempo.getTemperaturaMaxima(6)));
    	Temp_Sem_7.setText(crearLabelTemp((double)tiempo.getTemperaturaMinima(7), (double)tiempo.getTemperaturaMaxima(7)));
    }
    
    private String crearLabelTemp(double min, double max) {
    	return df.format(min) + "ºC/" + df.format(max) + "ºC";
    }
    
    private void establecerDia(Label lb, int day) {
    	switch(day) {
    	case 0: lb.setText("Lunes"); break;
    	case 1: lb.setText("Martes"); break;
    	case 2: lb.setText("Miercoles"); break;
    	case 3: lb.setText("Jueves"); break;
    	case 4: lb.setText("Viernes"); break;
    	case 5: lb.setText("Sabado"); break;
    	case 6: lb.setText("Domingo"); break;
    	}
    }
    
    private void establecerTiempo() {
    	tiempo.actualizarActualData();
    	double temp = tiempo.getTemperaturaActual();
    	LB_Temp.setText(df.format(temp) + "ºC");
    }
    
    private void establecerTiempoActual() {
    	Icon_Actual.setImage(tiempo.getClimaImagen(tiempo.getClimaActual()).getImage());
    	Icon_Sem_1.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(1)).getImage());
    	Icon_Sem_2.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(2)).getImage());
    	Icon_Sem_3.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(3)).getImage());
    	Icon_Sem_4.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(4)).getImage());
    	Icon_Sem_5.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(5)).getImage());
    	Icon_Sem_6.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(6)).getImage());
    	Icon_Sem_7.setImage(tiempo.getClimaImagen(tiempo.getClimaSemanal(7)).getImage());
    }
}
