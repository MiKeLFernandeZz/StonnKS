package Modelo;

public class FuncionesInput {

	Inyectador inyect;
	FuncionesOutput output;
	
	public FuncionesInput(Inyectador inyect, FuncionesOutput output) {
		this.inyect=inyect;
		this.output=output;
	}
	
	public void insertarDatosIniciales() {		
		
		//EMPRESA
		inyect.insertarEmpresa("A01114792", "Atusa", "Fabricación de accesorios de hierro maleable", "Agurain-Álava(Spain)", "info.spain@atusagroup.com", "+34 945180000", "Activo");
		//System.out.println("Empresa metida");
		//Calendario
		inyect.insertarCalendario(1, "Calendario General", "2021-2022");
		inyect.insertarCalendario(2, "Calendario Control Final", "2021-2022");
		inyect.insertarCalendario(3, "Calendario Control Final", "2018-2019");
		//System.out.println("Calendarios inicializados");
		
		//Departamento
		inyect.insertarDepartamento(0, "Marketing", "Departamento de Marketing", "A01114792", 1);
		inyect.insertarDepartamento(1, "Control Final", "Departamento de Ctrl. Final", "A01114792", 2);
		inyect.insertarDepartamento(2, "Fundicion", "Departamento de Fundicion", "A01114792", 1);
		inyect.insertarDepartamento(3, "Almacen", "Departamento de Gestion y Almacenamiento", "A01114792", 1);
		//System.out.println("Departamentos metidos");
		
		//Proyecto
		inyect.insertarProyecto(1, "Lote 500.000 piezas Z-34 Hulma", "Activo", "A01114792");
		inyect.insertarProyecto(2, "6x700 cajas: Juntas Pequeñas", "Inactivo", "A01114792");
		inyect.insertarProyecto(3, "Control y Gestion Hornos Bulgaria", "Activo", "A01114792");
		//System.out.println("Proyectos metidos");
		
		//Participa
		inyect.insertarParticipa(0, 1);
		inyect.insertarParticipa(1, 1);
		inyect.insertarParticipa(1, 2);
		inyect.insertarParticipa(2, 1);
		inyect.insertarParticipa(2, 2);
		inyect.insertarParticipa(2, 3);
		inyect.insertarParticipa(3, 1);
		inyect.insertarParticipa(3, 2);
		//System.out.println("Asignacion de Proyectos a Departamentos hecha");
		
		//Actividad
		inyect.insertarActividad(11, "QWERTYUIOPASDFG", "Fundir Metal", "Activa", 3);
		inyect.insertarActividad(12, "MNBVCXZÑLKJH", "Moldear Pieza", "Activa", 3);
		inyect.insertarActividad(13, "SDFGHJK", "Montar Pieza", "Activa", 2);
		inyect.insertarActividad(14, "WREFGDFGHDFG", "Revisar Pieza", "Activa", 2);
		inyect.insertarActividad(15, "DJWILEFBSDX", "Empaquetar Piezas", "Activa", 1);
		inyect.insertarActividad(16, "ASDFBHUDFF", "Enviar Piezas", "Activa", 1);
		inyect.insertarActividad(17, "AS3WUIUFNVNC", "Recibir Material", "Activa", 2);
		inyect.insertarActividad(18, "UUROTNDJFVVF", "Transportar Material", "Activa", 1);
		//System.out.println("Actividades metidas");
		
		//Trabajador
		inyect.insertarTrabajador(1, "1234567891", "Encargado de Fundicion", "Ruben", "Cacho Ruiz de Infante", 3600, 1000, "71007183R", "1977-04-08", null, null, null, "2000-11-27", null, 0, 2);
		inyect.insertarTrabajador(2, "9876543211", "Jefe Control Final", "Juan Jose", "Alvaro Saez de Asteasu", 4000, 1000, "76001245J", "1967-08-29", null, null, null, "1990-09-18", "2022-03-25", 0, 1);
		inyect.insertarTrabajador(3, "1324576891", "Coordinador Control Final", "Susana", "Perez de Villareal", 2500, 450, "65756881S", "1970-01-20", null, null, null, "2010-09-10", null, 2, 1);
		inyect.insertarTrabajador(4, "9786534211", "Montador de Piezas", "Joseba", "Alvaro Hernandez", 1500, 150, "58005182J", "2002-03-26", null, null, null, "2021-10-18", null, 3, 1);
		inyect.insertarTrabajador(5, "0011992251", "Revisor de piezas", "Beatriz", "Alvaro Hernandez", 1500, 200, "58005281B", "1998-08-13", null, null, null, "2019-08-05", "2019-08-30", 3, 1);
		//System.out.println("Trabajadores metidos");
		
		//Jornada
																						//RUBEN: Horario de noche
		inyect.insertarJornada(11, "2022-05-18 22:31:11", "2022-03-19 06:01:02", 1);
		inyect.insertarJornada(12, "2022-05-19 22:29:45", "2022-03-20 06:00:57", 1);
		inyect.insertarJornada(13, "2022-05-20 22:30:23", "2022-03-21 05:59:10", 1);
		inyect.insertarJornada(14, "2022-05-23 22:30:01", "2022-03-24 06:03:23", 1);
		inyect.insertarJornada(15, "2022-05-24 22:30:50", "2022-05-25 06:02:50", 1);
																						//JUAN JOSE: Jubilado
		inyect.insertarJornada(20, "2022-03-26 06:31:50", "2022-03-26 14:30:05", 2);
		inyect.insertarJornada(21, "2022-03-26 06:31:50", "2022-03-26 14:30:05", 2);
		inyect.insertarJornada(22, "2022-03-26 06:31:50", "2022-03-26 14:30:05", 2);
		inyect.insertarJornada(23, "2022-03-26 06:31:50", "2022-03-26 14:30:05", 2);
		inyect.insertarJornada(24, "2022-03-25 06:31:50", "2022-03-25 14:30:05", 2);
																						//SUSANA: Jornada completa
		inyect.insertarJornada(30, "2022-05-19 06:06:11", "2022-03-19 14:01:02", 3);
		inyect.insertarJornada(31, "2022-05-20 06:01:45", "2022-03-20 14:00:57", 3);
		inyect.insertarJornada(32, "2022-05-23 06:10:23", "2022-03-23 13:59:10", 3);
		inyect.insertarJornada(33, "2022-05-24 06:17:01", "2022-03-24 14:03:23", 3);
		inyect.insertarJornada(34, "2022-05-25 06:02:50", null, 3);				//simulamos que no ha terminado, k estamos a dia 25 antes de las 12
																						//JOSEBA: Media Jornada
		inyect.insertarJornada(40, "2022-05-19 08:06:11", "2022-03-19 12:01:02", 4);
		inyect.insertarJornada(41, "2022-05-20 08:01:45", "2022-03-20 12:00:57", 4);
		inyect.insertarJornada(42, "2022-05-23 08:10:23", "2022-03-23 11:59:10", 4);
		inyect.insertarJornada(43, "2022-05-24 08:17:01", "2022-03-24 12:03:23", 4);
		inyect.insertarJornada(44, "2022-05-25 08:02:50", null, 4);				//simulamos que no ha terminado, k estamos a dia 25 antes de las 12
																						//BEATRIZ: 27 no asistió. Retirada
		inyect.insertarJornada(50, "2019-08-23 06:30:12", "2019-08-23 16:29:05", 5);
		inyect.insertarJornada(51, "2019-08-26 06:36:34", "2019-08-26 14:40:17", 5);
		inyect.insertarJornada(52, "2019-08-28 06:25:53", "2019-08-28 14:30:21", 5);
		inyect.insertarJornada(53, "2019-08-29 06:28:50", "2019-08-29 14:19:05", 5);
		inyect.insertarJornada(54, "2019-08-30 06:31:50", "2019-08-30 14:30:05", 5);
		//System.out.println("Jornadas metidas");
		
		//ParteActividad
		inyect.insertarParteActividad(13, 42, "08:06:11", "10:30:05");
		inyect.insertarParteActividad(13, 42, "10:45:05", "11:59:10");
		inyect.insertarParteActividad(14, 52, "06:25:53", "9:58:55");
		inyect.insertarParteActividad(14, 52, "10:03:05", "14:30:21");
		inyect.insertarParteActividad(17, 32, "06:10:23", "14:30:05");
		inyect.insertarParteActividad(18, 32, "14:55:05", "13:59:10");
		inyect.insertarParteActividad(13, 44, "08:02:50", "11:06:05");
		inyect.insertarParteActividad(13, 44, "11:23:05", null);
		inyect.insertarParteActividad(11, 15, "22:30:50", "04:15:12");
		inyect.insertarParteActividad(11, 15, "04:32:46", "06:02:50");
		//System.out.println("Partes de Actividades metidos");
		
		//Justificante
		inyect.insertarJustificante(1, "Cita medica", "2019-08-27", "2019-08-27", 5);
		//System.out.println("Justificante metido");
		
		//Tipo
		inyect.insertarTipo(1, "Almuerzo", 10);
		inyect.insertarTipo(2, "Comida", 20);
		inyect.insertarTipo(3, "Tomar el aire", 5);
		inyect.insertarTipo(4, "Urgencia", 30);
		//System.out.println("Tipos de descanso metidos");
		
		//Descanso
		inyect.insertarDescanso(1, "14:30:05", "14:55:05", 32, 2);
		inyect.insertarDescanso(2, "11:06:05", "11:23:05", 44, 3);
		inyect.insertarDescanso(3, "9:58:55", "10:03:05", 52, 1);
		inyect.insertarDescanso(4, "10:30:05", "10:45:05", 42, 1);
		inyect.insertarDescanso(5, "04:15:12", "04:32:46", 15, 4);
		//System.out.println("Descansos insertados");
		
		//Dia y Corresponde
		gestionarDiasParaCalendario("2021-09-01","2022-07-29", 1, "6:30:00", "14:30:00"); //CALENDARIO 1
		gestionarDiasParaCalendario("2022-05-02","2022-08-31", 2, "22:00:00", "6:00:00"); //CALENDARIO 2
		gestionarDiasParaCalendario("2019-04-01","2019-08-30", 3, "22:30:00", "6:30:00"); //CALENDARIO 3
		//System.out.println("Dias metidos en los Calendarios");
		// COLOCAR DIAS FESTIVOS
		//Meter por rango
		festivosPorRango("2021-12-22","2022-1-2", "Vacaciones de Navidad");
		festivosPorRango("2022-1-6","2022-1-7", "Reyes Magos");
		festivosPorRango("2022-4-14","2022-4-24", "Semana Santa");
				//Meter individual
		inyect.actualizarDia(output.sacarIdDelDia("2022-1-1"),null, null, "Año Nuevo");
		inyect.actualizarDia(output.sacarIdDelDia("2022-1-6"),null, null, "Dia de los Reyes Magos");
		inyect.actualizarDia(output.sacarIdDelDia("2022-4-15"),null, null, "Viernes Santo");
		inyect.actualizarDia(output.sacarIdDelDia("2021-10-12"),null, null, "Fiesta Nacional de España");
		inyect.actualizarDia(output.sacarIdDelDia("2021-11-1"),null, null, "Todos los Santos");
		inyect.actualizarDia(output.sacarIdDelDia("2021-12-6"),null, null, "Día de la Constitución Española");
		inyect.actualizarDia(output.sacarIdDelDia("2021-12-8"),null, null, "Inmaculada Concepción");
		inyect.actualizarDia(output.sacarIdDelDia("2021-12-25"),null, null, "Navidad");
		inyect.actualizarDia(output.sacarIdDelDia("2019-04-1"),null, null, "Asunción de la virgen");
		inyect.actualizarDia(output.sacarIdDelDia("2019-8-15"),null, null, "Navidad");
		//System.out.println("Dias Festivos Colocados");
	}

	private void gestionarDiasParaCalendario(String fecha_in, String fecha_fin, int Calendario_ID, String hora_entrada, String hora_salida) {
		int año_in, mes_in, dia_in, año_fin, mes_fin, dia_fin;
		String inicio[]=fecha_in.split("-");
		String ffinal[]=fecha_fin.split("-");
		año_in=Integer.valueOf(inicio[0]);
		mes_in=Integer.valueOf(inicio[1]);
		dia_in=Integer.valueOf(inicio[2]);
		año_fin=Integer.valueOf(ffinal[0]);
		mes_fin=Integer.valueOf(ffinal[1]);
		dia_fin=Integer.valueOf(ffinal[2]);
		
			// LLENAR CALENDARIO CON FECHAS
		int cont=(365*Calendario_ID)-365;
		while(!((año_in==año_fin) && (mes_in==mes_fin) && (dia_in==dia_fin)))	{
			
			if(output.verSiEsFinde(año_in+"-"+mes_in+"-"+dia_in))
				inyect.insertarDia(++cont,año_in+"-"+mes_in+"-"+dia_in, null, null, "Fin de semana");
			else 
				inyect.insertarDia(++cont,año_in+"-"+mes_in+"-"+dia_in, hora_entrada, hora_salida, null);
			
			inyect.insertarCorresponde(1, cont);
			dia_in++;
			if(dia_in>sacarDiasDelMes(mes_in, año_in)) {
				dia_in=1;
				mes_in++;
				if(mes_in>12) {
					mes_in=1;
					año_in++;
				}
			}
		}
		inyect.insertarDia(++cont,año_in+"-"+mes_in+"-"+dia_in, null, null, null);
		inyect.insertarCorresponde(1, cont);
			
	}

	private void festivosPorRango(String inicio, String fin, String motivo) {
		//Sacamos de k ID a k ID hay k cambiar
		int id_in=output.sacarIdDelDia(inicio), id_fin=output.sacarIdDelDia(fin);
		while(id_in<=id_fin) {
			inyect.actualizarDia(id_in,null, null, motivo);
			id_in++;
		}
	}

	private int sacarDiasDelMes(int mes, int año) {
		switch(mes){
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            return 31;
        case 4:
        case 6:
        case 9:
        case 11:
            return 30;
        case 2:
        	if(año%4==0) return 29;
        	else return 28;
		}
		return 0;
	}
	
}
