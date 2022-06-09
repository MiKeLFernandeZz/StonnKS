package Modelo;

import org.joda.time.DateTime;

public class FechaYHora{
	
	DateTime dt;

	public FechaYHora() {
		dt = new DateTime();
	}
	
    public String getFecha() {
    	return ((dt.getDayOfMonth() < 10) ? "0" + dt.getDayOfMonth() : dt.getDayOfMonth())
			+ "/" + ((dt.getMonthOfYear() < 10) ? "0" + dt.getMonthOfYear() : dt.getMonthOfYear())
			+ "/" + dt.getYearOfEra();
    }
    
    public String getFechaBase() {
    	DateTime dt = new DateTime();
    	return (dt.getYearOfEra() + "-" + ((dt.getMonthOfYear() < 10) ? "0" + dt.getMonthOfYear() : dt.getMonthOfYear())
			+ "-" + ((dt.getDayOfMonth() < 10) ? "0" + dt.getDayOfMonth() : dt.getDayOfMonth()));
    }
    
    public String getHora() {
    	return(dt.getHourOfDay() < 10 ? "0" + dt.getHourOfDay() : Integer.toString(dt.getHourOfDay()));
    }
    
    public String getHoraBase() {
    	DateTime dt = new DateTime();
    	return dt.getHourOfDay() + ":" + dt.getMinuteOfHour() + ":" + dt.getSecondOfMinute();
    }
    
    public String getMinuto() {
    	return(dt.getMinuteOfHour() < 10 ? "0" + dt.getMinuteOfHour() : Integer.toString(dt.getMinuteOfHour()));
    }
    
    public int getDiaSemana() {
    	return dt.getDayOfWeek() - 1;
    }
    
    public int getMilisParaMinuto() {
    	return 60000 - dt.getMillisOfSecond() - dt.getSecondOfMinute() * 1000;
    }
}
