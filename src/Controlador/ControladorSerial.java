package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ControladorSerial implements SerialPortDataListener, ActionListener {
	static PropertyChangeSupport conectorMain;
	Scanner teclado;
	SerialPort comPort;
	SerialPortDataListener conector;
	int datosBuenos[];
	Object arg0new;

	public ControladorSerial() {
		inicializar();
	}
	
	public void inicializar() {
		conectorMain = new PropertyChangeSupport(this);
		if(SerialPort.getCommPorts().length > 0){
			comPort = SerialPort.getCommPorts()[0];
			System.out.println(comPort.getDescriptivePortName());
			comPort.openPort();
			comPort.addDataListener(this);
			comPort.setBaudRate(9600);	
		}else {
			System.out.println("Sin conecxion a Basys");
		}
	}

	public static void addPropertyChangeListener(PropertyChangeListener listener) {
		conectorMain.addPropertyChangeListener(listener);
	}

	public static void removePropertyChangeListener(PropertyChangeListener listener) {
		conectorMain.removePropertyChangeListener(listener);
	}

	public void enviarInfo() {
		byte[] cadena = {'h','o','l','a'};
		comPort.writeBytes(cadena, 4, 0);
	}

	@Override
	public int getListeningEvents() {

		return 1;
	}

	public String leerSerial(int size) {
		while (comPort.bytesAvailable() < size) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		byte[] lectruraBytes = new byte[comPort.bytesAvailable()];
		comPort.readBytes(lectruraBytes, size);
		String devolver = new String(lectruraBytes, StandardCharsets.UTF_8);
		return devolver;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		String lectura = leerSerial(16);
		System.out.println(lectura);
		conectorMain.firePropertyChange("NFC_ID", null, lectura);
		//arg0new = arg0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int in : datosBuenos) {
			//System.out.print(in + " ");
		}
		//System.out.println();
	}

	public int[] getDatosBuenos() {
		return datosBuenos;
	}
}
