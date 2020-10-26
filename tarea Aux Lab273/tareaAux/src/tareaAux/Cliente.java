package tareaAux;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente extends Thread{
	// Iniciamos el Main

	// Declaramos las variables de nuestro Socket UDP
	int puerto;
	InetAddress servidorDestino;
	DatagramSocket socketUDP;
	DatagramPacket paqueteRecibido;
	DatagramPacket paqueteAEnviar;

	// Constructor para ingresar nuestra direccion y puerto
	public Cliente() throws UnknownHostException {
		servidorDestino = InetAddress.getByName("127.0.0.1");
		puerto = 9990;
	}
	@Override
	public void run() {
		try {
			Cliente c = new Cliente();
			c.iniciar();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Funcion para iniciar la consulta al servidor
	public void iniciar() throws IOException {
		Scanner lectura = new Scanner(System.in);

		// Creamos un socket bajo UDP
		socketUDP = new DatagramSocket();
		while(true) {
			// -- ENVIANDO EL PAQUETE
			// Leemos el mensaje que vamos a enviar al servidor
			System.out.println("MENU");
			System.out.println("1. Es primo");
			System.out.println("2. Es palindromo");
			System.out.println("3. Maximo de tres numeros");
			System.out.println("4. Salir");
			String cadenaEnviar = lectura.nextLine();
			if(cadenaEnviar.compareToIgnoreCase("4")!=0) {
				// Convertimos el mensajes a Bytes
				byte[] mensajeEnviar = cadenaEnviar.getBytes();
				// Declaramos nuestro paquete el cual enviaremos
				paqueteAEnviar = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, servidorDestino, puerto);
				// Enviamos el Datagrama
				socketUDP.send(paqueteAEnviar);

				// -- RECIBIENDO PAQUETE
				// Se recibe el paquete
				paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
				// Esperamos a que nos llegue respuesta desde el servidor
				socketUDP.receive(paqueteRecibido);
				// Ha llegado un datagrama, para ver los datos se utiliza getData()
				String mensaje = new String(paqueteRecibido.getData());
				System.out.println(mensaje);
				System.out.println();
			}
			else {
				finalizar();
				break;
			}

		}
	}

	// Funcion para finalizar la conexion
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conexion Finalizada con el servidor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}