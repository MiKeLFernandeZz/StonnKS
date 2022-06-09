package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;
/**
 * Es la clase encargada ejecutar funciones INSERT y UPDATE en la Base de Datos
 */
public class Inyectador {

    private Conector conectar;
    private Connection con;
	
	public Inyectador(Conector conectar) {
		this.conectar=conectar;
	}

	private PreparedStatement prepararEstado(String sql, List<Object> lista) {
		PreparedStatement ps=null;

		try {
			ps = con.prepareStatement(sql);
			for(int cont=0; cont<lista.size();cont++) {
				if(lista.get(cont) instanceof Integer)
					ps.setInt(cont+1, (int) lista.get(cont));
				else if(lista.get(cont) instanceof String)
					ps.setString(cont+1, (String) lista.get(cont));
				else if(lista.get(cont) instanceof Float)
					ps.setFloat(cont+1, (Float) lista.get(cont));
				else
					ps.setString(cont+1, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
//---------------------------------------------------------| INSERTAR DATOS |
	public void insertarEmpresa(String var_NIF, String var_nombre, String var_descripcion, String var_direccion, 
								String var_correo, String var_telefono, String var_estado) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_NIF,var_nombre,var_descripcion,var_direccion,var_correo,var_telefono,var_estado);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Empresa values(?,?,?,?,?,?,?)", argumentos);
            System.out.println("insert into Empresa values(\""+var_NIF+"\",\""+var_nombre+"\",\""+var_descripcion+"\",\""+var_direccion+"\",\""+var_correo+"\",\""+var_telefono+"\",\""+var_estado+"\");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Empresa "+var_nombre+" metida");
        }catch(SQLException e){
        	JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarCalendario(int var_CalendarioID, String var_descripcion, String var_temporada) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_CalendarioID,var_descripcion,var_temporada);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Calendario values(?,?,?)", argumentos);
            System.out.println("insert into Calendario values("+var_CalendarioID+",\""+var_descripcion+"\",\""+var_temporada+"\");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Calendario "+var_descripcion+" metido");
        }catch(SQLException e){
        	JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarDepartamento(int var_DepartamentoID, String var_nombre, String var_descripcion, String var_EmpresaNIF, int var_CalendarioID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_DepartamentoID,var_nombre,var_descripcion,var_EmpresaNIF,var_CalendarioID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Departamento values(?,?,?,?,?)", argumentos);
            System.out.println("insert into Departamento values("+var_DepartamentoID+",\""+var_nombre+"\",\""+var_descripcion+"\",\""+var_EmpresaNIF+"\","+var_CalendarioID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Departamento "+var_nombre+" metido");
        }catch(SQLException e){
        	JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
//
	public void insertarProyecto(int var_ProyectoID, String var_descripcion, String var_estado, String var_EmpresaNIF) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_ProyectoID,var_descripcion,var_estado,var_EmpresaNIF);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Proyecto values(?,?,?,?)", argumentos);
            System.out.println("insert into Proyecto values("+var_ProyectoID+",\""+var_descripcion+"\",\""+var_estado+"\",\""+var_EmpresaNIF+"\");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Proyecto "+var_descripcion+"("+var_ProyectoID+") metido");
        }catch(SQLException e){
        	JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarParticipa(int var_DepartamentoID, int var_ProyectoID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_DepartamentoID,var_ProyectoID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Participa values(?,?)", argumentos);
            System.out.println("insert into Participa values("+var_DepartamentoID+","+var_ProyectoID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarActividad(int var_ActividadID, String var_ACT_NFC, String var_descripcion, String var_estado, int var_ProyectoID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_ActividadID,var_ACT_NFC,var_descripcion,var_estado,var_ProyectoID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Actividad values(?,?,?,?,?)", argumentos);
            System.out.println("insert into Actividad values("+var_ActividadID+",\""+var_ACT_NFC+"\",\""+var_descripcion+"\",\""+var_estado+"\","+var_ProyectoID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Actividad "+var_descripcion+" metida");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}

	public void insertarTrabajador(int var_TrabajadorID, String var_NFC_ID, String var_puesto, String var_nombre, String var_apellidos,
								   float var_salario, float var_comision, String var_DNI, String var_fecha_nacimiento, String var_telefono,
								   String var_direccion, String var_correo, String var_fecha_entrada, String var_fecha_salida, int var_jefe,
								   int var_DepartamentoID) {
		List<Object> argumentos = new ArrayList<Object>();
		if(var_jefe==0) 
			argumentos=Arrays.asList(var_TrabajadorID,var_NFC_ID,var_puesto,var_nombre,var_apellidos,var_salario,var_comision,var_DNI,var_fecha_nacimiento,
				 var_telefono,var_direccion,var_correo,var_fecha_entrada,var_fecha_salida,null,var_DepartamentoID);
		else 
			argumentos=Arrays.asList(var_TrabajadorID,var_NFC_ID,var_puesto,var_nombre,var_apellidos,var_salario,var_comision,var_DNI,var_fecha_nacimiento,
				var_telefono,var_direccion,var_correo,var_fecha_entrada,var_fecha_salida,var_jefe,var_DepartamentoID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Trabajador values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", argumentos);
            System.out.println("insert into Trabajador values("+var_TrabajadorID+",\""+var_NFC_ID+"\",\""+var_puesto+"\",\""+var_nombre+"\",\""+var_apellidos+"\","+var_salario+","+var_comision+
            		",\""+var_DNI+"\",\""+var_fecha_nacimiento+"\",\""+var_telefono+"\",\""+var_direccion+"\",\""+var_correo+"\",\""+var_fecha_entrada+"\",\""+var_fecha_salida+"\","+var_jefe+","+var_DepartamentoID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Trabajador "+var_nombre+" "+var_apellidos+" metido");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarJornada(int var_JornadaID, String var_Fecha_Hora_entrada, String Fecha_Hora_salida, int var_TrabajadorID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_JornadaID,var_Fecha_Hora_entrada,Fecha_Hora_salida,var_TrabajadorID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Jornada values(?,?,?,?)", argumentos);
            System.out.println("insert into Jornada values("+var_JornadaID+",\""+var_Fecha_Hora_entrada+"\",\""+Fecha_Hora_salida+"\","+var_TrabajadorID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Jornada "+var_JornadaID+" del trabajador "+var_TrabajadorID+" metida");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarParteActividad(int var_ActividadID, int var_JornadaID, String var_Hora_Inicio, String Hora_Fin) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_ActividadID,var_JornadaID,var_Hora_Inicio,Hora_Fin);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into ParteActividad values(?,?,?,?)", argumentos);
            System.out.println("insert into ParteActividad values("+var_ActividadID+","+var_JornadaID+",\""+var_Hora_Inicio+"\",\""+Hora_Fin+"\");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarJustificante(int var_JustificanteID, String var_motivo, String var_fecha, String var_validez, int var_TrabajadorID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_JustificanteID,var_motivo,var_fecha,var_validez,var_TrabajadorID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Justificante values(?,?,?,?,?)", argumentos);
            System.out.println("insert into Justificante values("+var_JustificanteID+",\""+var_motivo+"\",\""+var_fecha+"\",\""+var_validez+"\","+var_TrabajadorID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarTipo(int var_TipoID, String var_descripcion, int var_tiempo) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_TipoID,var_descripcion,var_tiempo);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Tipo values(?,?,?)", argumentos);
            System.out.println("insert into Tipo values("+var_TipoID+",\""+var_descripcion+"\","+var_tiempo+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarDescanso(int var_DescansoID, String var_HoraComienzo, String var_HoraFinal, int var_JornadaID, int var_TipoID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_DescansoID,var_HoraComienzo,var_HoraFinal,var_JornadaID,var_TipoID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Descanso values(?,?,?,?,?)", argumentos);
            System.out.println("insert into Descanso values("+var_DescansoID+",\""+var_HoraComienzo+"\",\""+var_HoraFinal+"\","+var_JornadaID+","+var_TipoID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarDia(int var_DiaID,String var_fecha, String var_Hora_Entrada, String var_Hora_Salida, String var_motivo) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_DiaID,var_fecha,var_Hora_Entrada,var_Hora_Salida,var_motivo);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Dia values(?,?,?,?,?)", argumentos);
            System.out.println("insert into Dia values("+var_DiaID+",\""+var_fecha+"\",\""+var_Hora_Entrada+"\",\""+var_Hora_Salida+"\",\""+var_motivo+"\");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado Dia: "+var_fecha);
            //System.out.println("Se han insertado Dia: "+var_fecha);
        }catch(SQLException e){
            //JOptionPane.showMessageDialog(null, "No se ha metido Dia "+var_fecha+"| Error de conexion:" + e.getMessage());
        	System.out.println("No se ha metido Dia "+var_fecha+"| Error de conexion:" + e.getMessage());
        }
	}
	
	public void insertarCorresponde(int var_CalendarioID, int var_DiaID) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(var_CalendarioID,var_DiaID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("insert into Corresponde values(?,?)", argumentos);
            System.out.println("insert into Corresponde values("+var_CalendarioID+","+var_DiaID+");");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	
//---------------------------------------------------------| ACTUALIZAR REGISTROS |
	/**
     * Ejecuta en la Base de Datos un comando que realiza lo siguiente:
     * - Tabla: Dia
     * - Funcion: cambiar atributos de uno de los registros indicado con el DiaID
     * 			- Marcar como festivo: horas de entrada y salida 'null' y motivo de la festividad
     * 			- Marcar como laboral: colocar hora de entrada y hora de salida correspondientes y motivo 'null'
     */
	public void actualizarDia(int id,String hora_entrada,String hora_salida,String motivo) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(hora_entrada,hora_salida,motivo,id);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("UPDATE Dia SET Hora_Entrada=?, Hora_Salida=?, motivo=? WHERE DiaID=?", argumentos);
            System.out.println("UPDATE Dia SET Hora_Entrada="+hora_entrada+", Hora_Salida="+hora_salida+", motivo=\""+motivo+"\" WHERE DiaID="+id+";");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
//-------------------------------------------------------------------------------|	FUNCIONES PARA LA APLICACION
	/**
     * Ejecuta en la Base de Datos un comando que realiza lo siguiente:
     * - Tabla: ParteActividad
     * - Funcion: marcar el cierre de un Parte de Actividad, este parte sera el de la JornadaID pasada y que tenga Hora_Fin 'null'
     */
	public void cerrarParteDeActividad(int jornadaID, String horaComienzo) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(horaComienzo,jornadaID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("UPDATE ParteActividad SET Hora_Fin=? WHERE JornadaID=? and Hora_Fin is null", argumentos);
            System.out.println("UPDATE ParteActividad SET Hora_Fin="+horaComienzo+" WHERE JornadaID="+jornadaID+" AND Hora_Fin is null;");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	/**
     * Ejecuta en la Base de Datos un comando que realiza lo siguiente:
     * - Tabla: Descanso
     * - Funcion: marcar el cierre de un Descanso, este parte sera el del DescansoID pasado y pondra horaFinal como cierre
     */
	public void cerrarDescanso(int descansoID, String horaFinal) {
		List<Object> argumentos = new ArrayList<Object>();
		argumentos=Arrays.asList(horaFinal,descansoID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("UPDATE Descanso SET HoraFinal=? WHERE DescansoID=?", argumentos);
            System.out.println("UPDATE Descanso SET HoraFinal="+horaFinal+" WHERE DescansoID="+descansoID+";");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
	/**
     * Ejecuta en la Base de Datos un comando que realiza lo siguiente:
     * - Tabla: Jornada
     * - Funcion: marcar el cierre de un Jornada, este parte sera el del JornadaID pasado y pondra la fecha junto a la hora como cierre
     */
	public void cerrarJornada(int jornadaID, String fechaSalida, String horaSalida) {
		List<Object> argumentos = new ArrayList<Object>();
		String Fecha_Hora_salida=fechaSalida.concat(" ").concat(horaSalida);
		argumentos=Arrays.asList(Fecha_Hora_salida,jornadaID);
		PreparedStatement ps;
        
        try{
            con = conectar.getConexion();
            
            ps=prepararEstado("UPDATE Jornada SET Fecha_Hora_salida=? WHERE JornadaID=?", argumentos);
            System.out.println("UPDATE Descanso SET Fecha_Hora_salida="+Fecha_Hora_salida+" WHERE JornadaID="+jornadaID+";");
            
            ps.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Se han insertado los datos");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexion:" + e.getMessage());
        }
	}
}
