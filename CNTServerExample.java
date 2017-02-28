/*
 * CNT4504 Server Example
 *
 * This program implements the Server side of a client server application.  The client 
 * connects to the server, then it requests the date and time from the server.  When 
 * the server receives a request over an established connection, it runs the appropriate 
 * Linux Bash command to generate the requested information, captures that information, 
 * and sends it back to the client that requested the information with the line 
 * "ServerDone" appended to the end.  The connection stays up until the client terminates 
 * it by sending a quit request.
 *
 * The request from the server is simply a String MC:1 
 *
 * Author: Dr. Douglas Leas
 * Date: 2/27/2017
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * A TCP server thread that runs on port 1234.  When a client connects, it
 * reads a request number and responds to that request
 */
public class CNTServerExample {
    public static void main(String[] args) throws IOException {
		System.out.println("CNT4504 Server Socket Example\n");
		System.out.println("Server starting on socket 1234");
		
		// Create the socket and bind to port number 1234
        ServerSocket listener = new ServerSocket(1234);
        
		// Listen for client connections, this will block waiting for a connection
		Socket socket = listener.accept();
		System.out.println("Accepted Client connection");

		// Attach a buffered reader to the socket's input stream
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// Attach a print writer to the socket's output stream
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
		// Read the request from the client
		String answer = input.readLine();
		
		Process cmdProc;
		cmdProc=null;
		// Execute the appropriate command.
		if(answer.equals("MC:1"))
		{
			System.out.println("Responding to date request from the client ");
			String[] cmd = {"bash", "-c", "date"};
			cmdProc = Runtime.getRuntime().exec(cmd);
		}
		else
		{
			System.out.println("Unknown request ");
			socket.close();
			return;
		}
		
		// Read the result of the commands and send the result to the client one line at a time
		// followed by the line "ServerDone"
		BufferedReader cmdin = new BufferedReader(new InputStreamReader(cmdProc.getInputStream()));
		String cmdans;
		while((cmdans = cmdin.readLine()) != null)
		{
			out.println(cmdans);
		}
		out.println("ServerDone");


		socket.close();
		return;

	}
 
}