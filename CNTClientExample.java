/*
 * CNT4504 Socket Client Example
 *
 * This program implements an example of the client side of a client server 
 * application using sockets. The client connects to the server at the IP address 
 * and port provided on the command line, then it sends a simple message cotaining 
 * the string MC:1 to the server (for management command 1) and waits for the response.  
 * When the response is recieved, it prints the response, closes the connection to the 
 * server and exits.  
 *
 * Input: The IP address and the port number of the server are supplied via the
 *        command line.  
 *
 * Output: The program prints the response from the server.
 *
 * Author: Dr. Douglas Leas
 * Date: 2/27/2017
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class CNTClientExample
{
    public static void main(String[] args) throws IOException 
	{
		if(args.length < 2)  // If there are no command line arguments print an error and exit.
		{
			System.out.println("Usage: CNTClient <server IP Address> <port>\n");
			System.exit(0);
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		// Try to connect to port and the IP address given on the command line.
		Socket s=null;
		try
		{
			s = new Socket(args[0], Integer.parseInt(args[1]));
		}
		catch(Exception e)
		{
			System.out.println("Error: Could not open a socket to " + args[0] + "\n");
			System.exit(100);
		}
		
		//Create a buffered reader attached to the socket's input stream.
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
		// Create a print writer attached to the socket's output stream.
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);
		System.out.println("Connecting to server at "+args[0]+":"+args[1]);
		
		System.out.println("Requesting system time");
		long start_time = System.currentTimeMillis();
		out.println("MC:1");

		System.out.println("Response from the server:\n");
			
		// Read lines from the server and print them until "ServerDone" on
		// a line by itself is encountered.
		String answer;
		while((answer = input.readLine()) != null && !answer.equals("ServerDone"))
		{
			System.out.println(answer);
		} 
		long end_time = System.currentTimeMillis();

		System.out.println("Date command took " + (end_time-start_time) + "ms");
		
		
		
		return;
	}
}