package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.JSONObject;

import javafx.scene.image.ImageView;

public class Tiempo {
	
	JSONObject ActualData;
	JSONObject WeakData;
	String file;

	public Tiempo() {
		actualizarActualData();
		actualizarSemanalData();
		if(System.getProperty("os.name").startsWith("Windows"))
			file = "file:\\";
		else
			file = "file://";
	}
	
	public void actualizarActualData() {
		URL url;
		try {
			url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=43.063613&lon=-2.505620&appid=04430e4a04cfa3c15afe4e907aee191e");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			InputStream responseStream = con.getInputStream();
			String result = new BufferedReader(new InputStreamReader(responseStream)).lines().collect(Collectors.joining("\n"));
			ActualData = new JSONObject(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarSemanalData() {
		URL url;
		try {
			url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=43.063613&lon=-2.505620&exclude=current,minutely,hourly,alerts&appid=04430e4a04cfa3c15afe4e907aee191e");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			InputStream responseStream = con.getInputStream();
			String result = new BufferedReader(new InputStreamReader(responseStream)).lines().collect(Collectors.joining("\n"));
			WeakData = new JSONObject(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getClimaSemanal(int dia) {
		JSONObject daily = WeakData.getJSONArray("daily").getJSONObject(dia);
		JSONObject weather = daily.getJSONArray("weather").getJSONObject(0);
		return (int) weather.get("id");
	}
	
	public ImageView getClimaImagen(int code) {
		if (code <= 232)
			return new ImageView(file + new File("icons/Thunderstorm.png").getAbsolutePath());
		else if(code <= 321)
			return new ImageView(file + new File("icons/Drizzle.png").getAbsolutePath());
		else if(code <= 531)
			return new ImageView(file + new File("icons/Rain.png").getAbsolutePath());
		else if(code <= 622)
			return new ImageView(file + new File("icons/Snow.png").getAbsolutePath());
		else if(code <= 781)
			return new ImageView(file + new File("icons/Cloudy.png").getAbsolutePath());
		else if(code <= 800)
			return new ImageView(file + new File("icons/Sunny.png").getAbsolutePath());
		else if(code <= 802)
			return new ImageView(file + new File("icons/Partly-cloudy.png").getAbsolutePath());
		else if(code <= 804)
			return new ImageView(file + new File("icons/Cloudy.png").getAbsolutePath());
		return null;
	}
	
	public int getClimaActual() {
		JSONObject weather = ActualData.getJSONArray("weather").getJSONObject(0);
		return (int) weather.get("id");
	}
	
	public double getTemperaturaActual() {
		JSONObject weather = ActualData.getJSONObject("main");
		return ((Double) weather.get("temp")) - 273.15;
	}
	
	public double getTemperaturaMinima(int dia) {
		JSONObject daily = WeakData.getJSONArray("daily").getJSONObject(dia);
		JSONObject temp = daily.getJSONObject("temp");
		return Double.valueOf(temp.get("min").toString()) - 273.15;
	}
	
	public double getTemperaturaMaxima(int dia) {
		JSONObject daily = WeakData.getJSONArray("daily").getJSONObject(dia);
		JSONObject temp = daily.getJSONObject("temp");
		return Double.valueOf(temp.get("max").toString()) - 273.15;
	}
}
