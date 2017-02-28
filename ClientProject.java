/*
To Do:
Looping:
1: Display menu
2: Prompt user for command
3: Prompt user for number of times to execute command
4: Check user input for command validity. If user command or count is invalid, inform the
user and redisplay the menu.
5: Send that command request to the server the requested number of times. For each time
the command is run (for option 6 prompt the user for a string to send.)
  a. Get response back from server
  b. Print “Request X is complete” (replace X with a number 1, 2, 3, etc.)
  c. On the last response also, display the final response from the server
6: Display the total amount of time that it took to run the command the specified number
  of times in milliseconds.

User commands:
1. Server current Date and Time
2. Server Number of running processes 
3. Server number of active socket connections
4. Server time of last system boot
5. Server current users
6. Server echo back what is sent from client
7. Quit
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
