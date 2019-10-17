import java.io.*;
import java.net.*;

/**
 * Un cliente de demo de TCP que se conecta al servidor de demo.
 * El puerto está fijado al 6789, y la IP del servidor se puede especificar
 * como el primer parámetro de la línea de comandos.
 * 
 * El cliente tan solo se conecta al servidor, pide lineas por el teclado,
 * las envia al servidor y muestra la respuesta.
 * 
 * Si se escribe "fin" por teclado, se envía al servidor para que desconecte al cliente
 * y el cliente finaliza
 * 
 * @version 19/Oct/2015
 */

class TcpClient {
 public static void main(String argv[]) throws Exception {

  String cadena;
  String respuesta;
  BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
  // Conectar con servidor.
  // Se espera el nombre o IP de 
  Socket conexion = new Socket(argv.length == 1 ? argv[0] : "localhost", 6789);
  // Un par de streams para representar entrada y salida
  PrintWriter haciaServidor = new PrintWriter(conexion.getOutputStream(), true);
  // con auto-flush: vacía buffers tras print, println y format automáticamente
  BufferedReader desdeServidor = new BufferedReader(new InputStreamReader(conexion.getInputStream()));

  // con "fin" se termina el cliente y se cerrará el transporte por parte del cliente
  // Con "shutdown" el servidor cerrará el transporte, y en este caso, el cliente terminará también
  do {
   // Pedir una cadena por teclado y mandarla al servidor
   System.out.print("Escibe algo para mandar al servidor: ");
   cadena = teclado.readLine();
   haciaServidor.println(cadena);

   // Esperar respuesta
   respuesta = desdeServidor.readLine();
   System.out.println("DESDE EL SERVIDOR: " + respuesta);
  } while (!(cadena.equals("fin") || cadena.equals("shutdown")));
  // cerrar transporte
  conexion.close();
  System.out.println("Cliente finalizado");
 }
}
