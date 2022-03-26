/*
 * @author Mohammed Ahmed
 * Student Number: 214396428
 * Class: EECS 3214
 * Instructor: Suprakash Datta
 *
 *  Your documentation should include:
 *
 *		1) Situations (if any) when your program may not work correctly
 *			- The situations when my program may not work correctly is:
 *				1. when you run the coordinator before running the node (because my coordinator is in a client mode looking for a server)
 *
 *		2) Possible improvements and extensions to your program that you would do if you had more time
 *			- if I had more time I would improve the log file so that it would include: Trace, debug, information and error instead of just the time when the client connected and when the connection closed.
 *
 *		3) Instructions to compile and run your program. Definitely include this if you have used helper classes
 *			- To run this program you will first:
 *				1. Run the server program with 1 argument, a port number (in the command line in terminal (only 1 argument))
 *				2. Run the node program with 3 argument, first the hostName, second the port of the coordinator and three the port of the server (in the command line)
 *				3. Run the coordinator node with 1 argument, the port number of the node
 *
 *		Note: the node program will continue to run for additional 15 sec after the coordinator sent an input to node to allow overlap between connections
 *
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class server {
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {   //if the port number is not said when running the program
			System.err.println("Usage: java server <port number>");
			System.exit(1);
		}
		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		int portNumber = Integer.parseInt(args[0]);

		try {
			serverSocket = new ServerSocket(Integer.parseInt(args[0])); //new server socket
			int count = 0;
			String i;
			while (true) {

				clientSocket = serverSocket.accept(); //accept client
				
				SimpleDateFormat formatte = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				i = formatte.format(System.currentTimeMillis()); //current time stored in i (the time when the client was accepted)
			
				count++;
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("server.log"), true)); //creating a new file named 'server.log' to log our clients
				
				task t = new task(clientSocket, i, writer, count); // creating a new thread for every client
				new Thread(t).start();
			}

		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}

	}

}

class task implements Runnable {

	private final Socket clientSocket;
	private final String i;
	private final BufferedWriter write;
	private final int count;

	public task(Socket socket, String ii, BufferedWriter writer, int count) { //constructor
		this.clientSocket = socket;
		this.i = ii;
		this.write = writer;
		this.count = count;
	}

	public static void processingDelay(int m) throws InterruptedException { // A method to create a delay so we can allow an overlap between connections
		Thread.sleep(m);
	}
	String k;

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public void run() { //overriding the run method
		PrintWriter out = null;

		try {

			out = new PrintWriter(clientSocket.getOutputStream(), true); //output stream

			out.println(i); // respond to each client by sending its current time

			processingDelay(15000); // creating a 15 sec delay

			clientSocket.close(); // closing socket
			k = formatter.format(System.currentTimeMillis()); // time of when the connection closed
			
// below were are writing the log information into the log file
			
			write.write("Client ");
			write.write(String.valueOf(count));
			write.write(":");
			write.newLine();
			write.write("Time client connected was: ");
			write.write(i);
			write.newLine();
			write.write("Time client connection was closed is: ");
			write.write(k);
			write.newLine();
			write.flush();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}
