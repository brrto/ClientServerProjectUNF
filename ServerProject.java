/*
To do:
Multithreading: Spawn child thread for each new client that connects
Print diagnostic commands for what it's doing
Run linux commands for:
1. Server current Date and Time
2. Server Number of running processes
3. Server number of active socket connections
4. Server time of last system boot
5. Server current users
6. Server echo back what is sent from client
7. Quit -this should kill the child thread
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
