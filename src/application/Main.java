package application;
	
import Controlador.ControladorBaseDatos;
import Controlador.ControladorSerial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	Scene scene;
	ControladorSerial controladorSerial;
	ControladorBaseDatos controladorBaseDatos;
	static int trabajadorID = 4;
	static int actividadID = 12;
	static String IP = "192.168.19.32";
	public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
	}

	final static boolean animacion = false;
	
	public static boolean isAnimacion() {
		return animacion;
	}

	public static int getActividadID() {
		return actividadID;
	}

	public static void setActividadID(int actividadID) {
		Main.actividadID = actividadID;
	}

	public static int getTrabajadorID() {
		return trabajadorID;
	}

	public static void setTrabajadorID(int usuarioID) {
		Main.trabajadorID = usuarioID;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			controladorSerial = new ControladorSerial();
			controladorBaseDatos = new ControladorBaseDatos();
			Parent root = FXMLLoader.load(getClass().getResource("StandBy.fxml"));
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
