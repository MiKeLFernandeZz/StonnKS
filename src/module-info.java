module Test1 {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.joda.time;
	requires javafx.graphics;
	requires json;
	requires javafx.base;
	requires java.desktop;
	requires com.fazecast.jSerialComm;
	requires java.sql;
	requires mysql.connector.java;
	opens application to javafx.graphics, javafx.fxml;
	opens Controlador to javafx.fxml;
	exports application to javafx.fxml, javafx.graphics;
}
