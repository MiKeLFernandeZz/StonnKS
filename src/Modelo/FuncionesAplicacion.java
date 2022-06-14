package Modelo;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Minutes;

public class FuncionesAplicacion {

	Inyectador inyect;
	FuncionesOutput output;
	
	public FuncionesAplicacion(Inyectador inyect, FuncionesOutput output) {
		this.inyect=inyect;
		this.output=output;
	}
	
	public void iniciarJornada(int TrabajadorID, String FechaEntrada, String HoraEntrada, int ActividadID) {

		//Crear nueva Jornada
		int JornadaID;
		
		JornadaID=output.sacarSiguienteJornadaID();
		
		inyect.insertarJornada(JornadaID, FechaEntrada.concat(" ").concat(HoraEntrada), null, TrabajadorID);

		//Crear nuevo Parte de Actividad
		
		inyect.insertarParteActividad(ActividadID, JornadaID, HoraEntrada, null);
		
	}

	public void iniciarDescanso(int TrabajadorID, String HoraComienzo, TipoDescanso tipoDescanso) {
		
		//Cerrar Parte de Actividad
		int JornadaID;

		JornadaID=output.buscarJornadaActual(TrabajadorID);
		
		inyect.cerrarParteDeActividad(JornadaID,HoraComienzo);

		//Meter descanso
		int DescansoID;
		
		DescansoID=output.sacarSiguienteDescansoID();
		inyect.insertarDescanso(DescansoID, HoraComienzo, null, JornadaID, TipoDescanso.getIndex(tipoDescanso));
		
	}

	public void finalizarDescanso(int TrabajadorID, String HoraFinal) {

		//Cerrar Descanso
		int JornadaID, DescansoID;

		JornadaID=output.buscarJornadaActual(TrabajadorID);
		
		DescansoID=output.sacarDescansoAbiertoPorJornadaID(JornadaID);
		inyect.cerrarDescanso(DescansoID, HoraFinal);
		
		//Abrir nuevo parte de actividad
		int ActividadID;
		
		ActividadID=output.sacarUltimaActividadDelParte(JornadaID);
		inyect.insertarParteActividad(ActividadID, JornadaID, HoraFinal, null);
		
	}

	public void cambiarActividad(int TrabajadorID, String HoraInicio, int ActividadID, String Act_NFC) {		

		//Ver si esta en descanso o no
		
		int JornadaID, DescansoID;

		JornadaID=output.buscarJornadaActual(TrabajadorID);
		
		DescansoID=output.sacarDescansoAbiertoPorJornadaID(JornadaID); //si returnea null o algo esk no hay descansos abiertos
		
		if(Act_NFC!=null) ActividadID=output.sacarActividadPorNFCID(Act_NFC);
		
		if(DescansoID!=0) {//significa k hay descanso abierto (Esta Descansando)
			//Finalizar descanso 
			inyect.cerrarDescanso(DescansoID, HoraInicio);
		}
		//Cerrar Ultimo parte de actividad
		inyect.cerrarParteDeActividad(JornadaID,HoraInicio);
		
		//Crear nuevo parte de actividad
		inyect.insertarParteActividad(ActividadID, JornadaID, HoraInicio, null);
		
	}

	public void finalizarJornada(int TrabajadorID, String FechaSalida, String HoraSalida) {
		
		int JornadaID;

		JornadaID=output.buscarJornadaActual(TrabajadorID);
		
		inyect.cerrarParteDeActividad(JornadaID,HoraSalida);
		
		inyect.cerrarJornada(JornadaID, FechaSalida, HoraSalida);
		
	}
	
	 public int sacarHorasDeUnaJornada(int TrabajadorID, String fecha, String hora) {
        int JornadaID, t_jornada=0, t_descanso=0;
        DateTime aux = new DateTime();
        DateTime fin;
        DateTime inicio;
        String horaInicio[];
        String horaFin[];
        
        //SACAR TIEMPO TRABAJADO
        JornadaID=output.buscarJornadaPorTrabajadorIDyFecha(TrabajadorID, fecha);
        
        if(JornadaID==0) return 0;
        
        String cierreJ=output.sacarCierreJornada(JornadaID);
        String comienzoJ=output.sacarComienzoJornada(JornadaID);
        
        horaInicio = comienzoJ.substring(11).split(":");
        inicio = new DateTime(aux.getYearOfEra(), aux.getMonthOfYear(),
			aux.getDayOfMonth(), Integer.valueOf(horaInicio[0]),
			Integer.valueOf(horaInicio[1]), Integer.valueOf(horaInicio[2]));
        
        if(cierreJ==null) {//La jornada sigue activa xd
        	fin = new DateTime();
        }
        else { // La jornada ha chapado
	        horaFin = cierreJ.substring(11).split(":");
	    	fin = new DateTime(aux.getYearOfEra(), aux.getMonthOfYear(),
				aux.getDayOfMonth(), Integer.valueOf(horaFin[0]),
				Integer.valueOf(horaFin[1]), Integer.valueOf(horaFin[2]));
        }
    	t_jornada+=Minutes.minutesBetween(inicio, fin).getMinutes();
    	
    	
    	//-------------------------------------------------------//
    	
    	
        //SACAR TIEMPO DESCANSADO
        List<Integer> descansos=new ArrayList<>();
        String cierreD;
        String comienzoD;
        
        descansos=output.sacarDescansosDeUnaJornada(JornadaID);
        for (Integer descansoID : descansos) {
            cierreD=output.sacarCierreDescanso(descansoID);
            comienzoD=output.sacarComienzoDescanso(descansoID);
            horaInicio = comienzoD.split(":");
            inicio = new DateTime(aux.getYearOfEra(), aux.getMonthOfYear(),
    			aux.getDayOfMonth(), Integer.valueOf(horaInicio[0]),
    			Integer.valueOf(horaInicio[1]), Integer.valueOf(horaInicio[2]));
            
            if(cierreD==null){ 
            	fin = new DateTime();
            }else { 		   
            	horaFin = cierreD.split(":");
                fin = new DateTime(aux.getYearOfEra(), aux.getMonthOfYear(),
    				aux.getDayOfMonth(), Integer.valueOf(horaFin[0]),
    				Integer.valueOf(horaFin[1]), Integer.valueOf(horaFin[2]));
            }
            t_descanso += Minutes.minutesBetween(inicio, fin).getMinutes();
        }
        System.out.println("Tiempo jornada: " + t_jornada + "\tTiempo descansos: " + t_descanso);
        return t_jornada-t_descanso;
    }
	 
	 public int sacarHorasDeLaSemana(int TrabajadorID, String fecha, String hora) {
	        int inicio_semana;
	        int horas_semanales=0;
	        DateTime fechaActual;
	        DateTime fechaInicio;
	        
	        fechaActual = new DateTime();
	        //inicio_semana=fecha-(output.verDiaDeLaSemana(fecha)-1);//restar un numero de dias a una fecha para encontrar el inicio de la semana
	        inicio_semana = output.verDiaDeLaSemana(fecha)-1;
	        fechaInicio = fechaActual.minusDays(inicio_semana);
	        horas_semanales+=sacarHorasDeUnaJornada(TrabajadorID, FechaYHora.toFechaBase(fechaInicio), hora);
	        
	        do {
	        	fechaInicio = fechaInicio.plusDays(1);
	            horas_semanales+=sacarHorasDeUnaJornada(TrabajadorID, FechaYHora.toFechaBase(fechaInicio), hora);
	            inicio_semana--;
	        }while(inicio_semana > 0);
	        
	        return horas_semanales;
	    }
	
	public enum TipoDescanso{
		ALMUERZO,
		COMIDA,
		TOMARAIRE,
		URGENCIA;
		
		public static int getIndex(TipoDescanso tipo) {
			switch(tipo) {
			case ALMUERZO: return 1;
			case COMIDA: return 2;
			case TOMARAIRE: return 3;
			case URGENCIA: return 4;
			}
			return 0;
		}
		
		public static TipoDescanso getTipo(String s) {
			switch(s.toLowerCase()) {
			case "almuerzo": return ALMUERZO;
			case "comida": return COMIDA;
			case "tomar aire": return TOMARAIRE;
			case "urgencia": return URGENCIA;
			}
			return null;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			switch(super.toString()) {
			case "ALMUERZO": return "Almuerzo";
			case "COMIDA": return "Comida";
			case "TOMARAIRE": return "Tomar Aire";
			case "URGENCIA": return "Urgencia";
			}
			return null;
		}
		
	}
	
}
