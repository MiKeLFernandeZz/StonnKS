package Controlador;

import java.sql.Connection;

import Modelo.Conector;
import Modelo.FuncionesAplicacion;
import Modelo.FuncionesInput;
import Modelo.FuncionesOutput;
import Modelo.Inyectador;

public class ControladorBaseDatos {
	static Connection con;
	static Inyectador inyect;
	static FuncionesInput input;
	static FuncionesOutput output;
	static FuncionesAplicacion aplicacion;
	
	public ControladorBaseDatos() {
		Conector conector=new Conector();
		con = conector.getConexion();
		if(con!=null) {
			System.out.println("CONECTADO");
			inyect=new Inyectador(conector);
			output=new FuncionesOutput(con);
			input=new FuncionesInput(inyect, output);
			aplicacion=new FuncionesAplicacion(inyect, output);
		}
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		ControladorBaseDatos.con = con;
	}

	public static Inyectador getInyect() {
		return inyect;
	}

	public static void setInyect(Inyectador inyect) {
		ControladorBaseDatos.inyect = inyect;
	}

	public static FuncionesInput getInput() {
		return input;
	}

	public static void setInput(FuncionesInput input) {
		ControladorBaseDatos.input = input;
	}

	public static FuncionesOutput getOutput() {
		return output;
	}

	public static void setOutput(FuncionesOutput output) {
		ControladorBaseDatos.output = output;
	}

	public static FuncionesAplicacion getAplicacion() {
		return aplicacion;
	}

	public static void setAplicacion(FuncionesAplicacion aplicacion) {
		ControladorBaseDatos.aplicacion = aplicacion;
	}
	
}
