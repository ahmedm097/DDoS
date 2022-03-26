
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

import java.io.*;
import java.net.*;

public class node {

	public static void processingDelay(int m) throws InterruptedException {
		Thread.sleep(m);
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		if (args.length != 3) {
			System.err.println("Usage: java node <host name> <port of coordinator> <port of server>");
			System.exit(1);
		}

		String hostName = args[0];
		int portCoordinator = Integer.parseInt(args[1]);
		int portServer = Integer.parseInt(args[2]);

		try (ServerSocket serverSocket = new ServerSocket(portCoordinator);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			// ^^above is the code for the node to be in the server mode

			String s = in.readLine(); // what the coordinator sent
			if (s != null) { // if the coordinator sent anything to the node,
								// then the node switch from server mode to
								// client mode
				try (Socket echoSocket = new Socket(hostName, portServer);
						PrintWriter ou = new PrintWriter(echoSocket.getOutputStream(), true);
						BufferedReader i = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
						BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

				) {

					processingDelay(15000); // there's a delay of 15 sec to
											// allow overlap of between
											// connections

					echoSocket.close(); // closed the socket
				} catch (UnknownHostException e) {
					System.err.println("Don't know about host " + hostName);
					System.exit(1);
				} catch (IOException e) {
					System.err.println("Couldn't get I/O for the connection to " + hostName);
					System.exit(1);
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}

}
