package tareaAux;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class Servidor extends Thread{
	// Iniciamos
	Scanner lee = new Scanner(System.in);
	
	// Declaramos variables de tipo Socket que vamos a usar
	DatagramSocket socketUDP;
	DatagramPacket paqueteRecibido;
	DatagramPacket paqueteAEnviar;
	int puerto;

	// Constructor para enviar el puerto
	public Servidor() {
		puerto = 9990;
	}
	@Override
	// Funcion para iniciar el Servidor
	public void run() {
		try {
			// Creamos un socket bajo UDP
			socketUDP = new DatagramSocket(puerto);
			while (true) {

				// -- RECIBIENDO EL PAQUETE
				// Declaramos nuestro paquete el cual recibiremos
				paqueteRecibido = new DatagramPacket(new byte[1024], 1024);
				// Recibe el paquete
				socketUDP.receive(paqueteRecibido);
				//System.out.println("Llego un paquete...");
				// Recibiendo vector e bytes y convirtiendo a cadena
				String cadenaRecibido = new String(paqueteRecibido.getData());
				//System.out.println("Solicitud Recibida: " + cadenaRecibido);

				// -- ENVIANDO EL PAQUETE
				String cad = "";
				int num = Integer.parseInt(cadenaRecibido.substring(0, 1));
				switch (num) {
				case 1:
					System.out.println("Ingrese un numero");
					int n = lee.nextInt();
					int c = 0;
					if(n>1) {
						for (int i = 2; i <= n-1; i++) {
							if(n%i == 0) {
								c++;
							}
						}
						if(c==0) {
							cad = "Es primo";
						}
						else {
							cad = "No es primo";
						}
					}
					else {
						cad = "No es primo";
					}
					break;
				case 2:
					System.out.println("Ingrese la cadena");
					String cad1 = lee.next();
					String aux = "";
					for (int i = cad1.length()-1; i >=0 ; i--) {
						aux = aux+cad1.charAt(i);
					}
					if(cad1.compareToIgnoreCase(aux)==0) {
						cad = "Es palindromo";
					}
					else {
						cad = "No es palindromo";
					}
					break;
				case 3:
					System.out.println("Ingrese el primer numero");
					int a = lee.nextInt();
					System.out.println("Ingrese el segundo numero");
					int b = lee.nextInt();
					System.out.println("Ingrese el tercer numero");
					int c1 = lee.nextInt();
					int m1 = Math.max(a, b);
					int m2 = Math.max(m1, c1);
					cad = "El maximo de los 3 numeros es: "+m2;
					break;
				default:
					break;
				}
				byte mensajeEnviar[] = new byte[1024];
				// Convierte cadena a Vector de Bytes
				mensajeEnviar = cad.getBytes();
				// Se crea el datagrama que contendra el mensaje
				paqueteAEnviar = new DatagramPacket(mensajeEnviar, mensajeEnviar.length, paqueteRecibido.getAddress(),
						paqueteRecibido.getPort());
				// Luego se Envia el paquete al cliente
				socketUDP.send(paqueteAEnviar);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Funcion para finalizar la conexion
	public void finalizar() {
		try {
			socketUDP.close();
			System.out.println("Conexion finalizada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}