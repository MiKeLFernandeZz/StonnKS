package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	Scene StandBy;
	Scene Identificacion;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/scene/Standby.fxml"));
			StandBy = new Scene(root);
			StandBy.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(StandBy);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scene getStandBy() {
		return StandBy;
	}

	public Scene getIdentificacion() {
		return Identificacion;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
