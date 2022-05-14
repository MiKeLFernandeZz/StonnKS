module Test1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.joda.time;
	requires javafx.graphics;
	requires json;
	opens application to javafx.graphics, javafx.fxml;
	opens Controlador to javafx.fxml;
}
