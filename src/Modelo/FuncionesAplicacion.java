package Modelo;


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

	public void finalizarDescanso(String TrabajadorNFC, String HoraFinal) {

		//Cerrar Descanso
		int JornadaID, TrabajadorID, DescansoID;

		TrabajadorID=output.sacarTrabajadorPorNFCID(TrabajadorNFC);
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
		else {
			//Cerrar Ultimo parte de actividad
			inyect.cerrarParteDeActividad(JornadaID,HoraInicio);
		}
		//Crear nuevo parte de actividad
		inyect.insertarParteActividad(ActividadID, JornadaID, HoraInicio, null);
		
	}

	public void finalizarJornada(int TrabajadorID, String FechaSalida, String HoraSalida) {
		
		int JornadaID;

		JornadaID=output.buscarJornadaActual(TrabajadorID);
		
		inyect.cerrarParteDeActividad(JornadaID,HoraSalida);
		
		inyect.cerrarJornada(JornadaID, FechaSalida, HoraSalida);
		
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
