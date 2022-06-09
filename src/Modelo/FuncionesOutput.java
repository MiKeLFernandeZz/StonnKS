package Modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.CallableStatement;

public class FuncionesOutput {

	Connection con;
	Statement s;
	
	public FuncionesOutput(Connection con) {
		this.con=con;
	}
	
	public String[] consultar(String query) {
		
		ResultSet rs;
		try {
			s = con.createStatement();
			rs = s.executeQuery (query);
			while (rs.next())
			{
			    System.out.println (rs.getInt (1) + " " + rs.getString (2)+ 
			        " " + rs.getDate(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public boolean verSiEsFinde(String data) {
		ResultSet rs;
		try {
			s = con.createStatement();
			rs = s.executeQuery ("SELECT DAYOFWEEK(\""+data+"\") dia");
			rs.next();
			if(rs.getInt ("dia")==7 || rs.getInt ("dia")==1) return true;
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int sacarIdDelDia(String fecha) {
		ResultSet rs;
		try {
			s = con.createStatement();
			rs = s.executeQuery ("SELECT DiaID FROM Dia WHERE fecha=\""+fecha+"\"");
			rs.next();
			return rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
//-------------------------------------------------------------------------------|	FUNCIONES PARA LA APLICACION
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: NADA
     * - Output: JornadaID
     * 
     * @FUNCION:
     * Mira cual es la ultima JornadaID asignada y le suma 1
     * @OBJETIVO:
     * Buscar cual es el siguiente JornadaID que asignar
     */
	public int sacarSiguienteJornadaID() {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call NextJornadaID}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: NFC-ID del Trabajador
     * - Output: TrabajadorID
     * 
     * @FUNCION:
     * Mira a que trabajador pertenece el NFC-ID y pasa el TrabajadorID
     * @OBJETIVO:
     * Obtener el Primary Key del trabajador al que corresponde el NFC-ID
     */
	public int sacarTrabajadorPorNFCID(String trabajadorNFC) {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call SacarTrabajadorIDdeNFC(\""+trabajadorNFC+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: NFC-ID de la Actividad
     * - Output: ActividadID
     * 
     * @FUNCION:
     * Mira a que actividad pertenece el NFC-ID y pasa el ActividadID
     * @OBJETIVO:
     * Obtener el Primary Key de la actividad a la que corresponde el NFC-ID
     */
	public int sacarActividadPorNFCID(String Act_NFC) {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call SacarActividadIDdeNFC(\""+Act_NFC+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: TrabajadorID
     * - Output: JornadaID
     * 
     * @FUNCION:
     * Mira en base a el TrabajadorID que Jornada tiene activa, es decir, cual de sus jornadas esta sin cerrar (Fecha_Hora_salida 'null')
     * @OBJETIVO:
     * Obtener el JornadaID de la jornada activa del trabajador
     */
	public int buscarJornadaActual(int trabajadorID) {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call BuscarJornadaAbiertaDelTrabajador(\""+trabajadorID+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: NADA
     * - Output: DescansoID
     * 
     * @FUNCION:
     * Mira cual es el ultimo DescansoID asignado y le suma 1
     * @OBJETIVO:
     * Buscar cual es el siguiente DescansoID que asignar
     */
	public int sacarSiguienteDescansoID() {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call NextDescansoID}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: JornadaID
     * - Output: DescansoID
     * 
     * @FUNCION:
     * Usando el JornadaID sacar el DescansoID del descanso no finalizado (HoraFinal 'null')
     * @OBJETIVO:
     * Obtener el DescansoID del descanso activo del trabajador
     */
	public int sacarDescansoAbiertoPorJornadaID(int jornadaID) {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call DescansoAbierto(\""+jornadaID+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			System.out.println("No hay descanso abierto. Return 0");
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: JornadaID
     * - Output: ActividadID
     * 
     * @FUNCION:
     * Mirar mediante el JornadaID la Actividad del ultimo Parte de Actividad abierto
     * @OBJETIVO:
     * Obtener la ActiviadID para el proximo Parte de Actividad en el caso que siga en la misma actividad
     */
	public int sacarUltimaActividadDelParte(int jornadaID) {
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call UltimaActividadDelParteSegunJornada(\""+jornadaID+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			rs.next();
			return rs.getInt(1);
		}catch(Exception e) {
			
		}
		return 0;
	}
	/**
     * Ejecuta uno de los Procesos creados en la Base de Datos:
     * - Input: TrabajadorID
     * - Output: Lista de actividades
     * 
     * @FUNCION:
     * Mirar que actividades corresponden al departamento del  trabajador
     * @OBJETIVO:
     * Obtener una lista de actividades para ofrecer al trabajador a la hora de cambiar de actividad
     */
	public List<String> sacarActividadesPorTrabajadorID(int trabajadorID) {
		int i=1;
		List<String> actividades = new ArrayList<>();
		try {
			CallableStatement sentencia=(CallableStatement) con.prepareCall("{call actividadesDelTrabajador(\""+trabajadorID+"\")}");
			
			ResultSet rs=sentencia.executeQuery();
			
			while(rs.next()) {
				
				actividades.add(rs.getString(i));
				
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return actividades;
	}

	
	
}
