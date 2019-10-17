/**
 * Un servidor TCP Sencillo. Solo una demo.
 * Se coloca a la espera en el puerto 6789.
 * Cuando un cliente se conecta, espera recibir de él una cadena, y 
 * la pasa a mayúsculas y la devuelve al cliente, el proceso se repite
 * hasta que el cliente envía fin, y se le desconecta y se espera a otro cliente.
 * 
 * Si un cliente le envía la palabra "shutdown", el servidor termina.
 * 
 * @version 19/Oct/2015
 */
import java.io.*;
import java.net.*;
 
class TcpServer {
 
    public static void main(String argv[]) throws Exception {
        String fraseDelCliente;
        String fraseDelClienteMays;
        ServerSocket socketServidor = new ServerSocket(6789);
        Socket conexion;
 
        do {
            System.out.println("Esperando cliente...");
            //esperar a un cliente
            conexion = socketServidor.accept();
            System.out.println("Aceptado transporte desde " + conexion.getInetAddress() + ":"
                + conexion.getPort());
            //Un stream para leer
            BufferedReader desdeCliente =
                new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            //otro para escribir. Con auto-flush: vacía buffers tras print, println y format automáticamente
            PrintWriter haciaCliente = new PrintWriter(conexion.getOutputStream(), true);
 
            //Si se recibe "fin", se cierra la conexión con el cliente. Si se recibe "shutdown" se termina el servidor
            do {
                //leer cadena entera hasta cambio de línea
                fraseDelCliente = desdeCliente.readLine();
                System.out.println("Recibido: " + fraseDelCliente);            
                //La pasamos a mayúsculas y la reenviamos al cliente
                fraseDelClienteMays = fraseDelCliente.toUpperCase();
                haciaCliente.println(fraseDelClienteMays);
            } while (!(fraseDelCliente.equals("fin") || fraseDelCliente.equals("shutdown")));
            conexion.close();
        } while (!fraseDelCliente.equals("shutdown"));
 
    socketServidor.close();
    System.out.println("Servidor terminado");
    }
}
