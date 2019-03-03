import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection extends Thread {
	BufferedReader inFromClient,user,inFromServer;
	DataOutputStream outToClient,outToServer;
	Socket clientSocket,serverSocket;
	
	
	public ServerConnection (Socket aClientSocket) {
		try {


			clientSocket = aClientSocket;
			

			//client
			outToClient = new DataOutputStream(clientSocket.getOutputStream());
			inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			user = new BufferedReader(new InputStreamReader(System.in));
	

			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
		try { 
			
			//greetings
			String msg = inFromClient.readLine();

			System.out.println("From Client: "+msg);

			outToClient.writeBytes("Welcome to proxy server!\n");
						
			//credentials match
			
			String username,password;
			
			username = inFromClient.readLine();
			
			outToClient.writeBytes("Username Recieved\n");
			
			password = inFromClient.readLine();
			
			outToClient.writeBytes("Password Recieved\n");
			
			//method for searching username & password
			if(credentialsSearch(username, password) == true){
				outToClient.writeBytes("Login Successful.\n");
			}
			
			else outToClient.writeBytes("Login Failed. Invalid Credentials Provided.\n");

			String servername = inFromClient.readLine();
			System.out.println("From Client: "+servername);
			
			
			
			//method for address resolution
			
			int portNumer = Integer.parseInt(addressResolution(servername));
			
			System.out.println("Trying to connect to Server:"+servername+" Using port Number:"+portNumer);
			
			Socket serverSock = new Socket("localhost", portNumer);
			System.out.println("Connected to server");
			
			//server reading & writing properties
			outToServer = new DataOutputStream(serverSock.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
			
			System.out.println("From Server: "+inFromServer.readLine());
			
			
			//messages to and fro - client and receiver
			
			String toServer = inFromClient.readLine();
			System.out.println("Message From Client: "+toServer);
			outToServer.writeBytes(toServer+"\n");
			
			
			String toClinet = inFromServer.readLine();
			System.out.println("Message From Server: "+toClinet);
			outToClient.writeBytes(toClinet+"\n");
			
			
			serverSock.close();
		} catch(EOFException e) {System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("IO:"+e.getMessage());
		} finally { try {clientSocket.close(); 
		}catch (IOException e){/*close failed*/}}
		
		
		
	}
	
	private String addressResolution(String servername) {
		String portNum = null;
		try{
			Scanner inFromFile = new Scanner(new File("C:/Users/Jindal's/workspace/Assignment1/src/receiverList.txt"));

			while(inFromFile.hasNextLine()){

				String s = inFromFile.nextLine();  
				String[] sArray = s.split(",");

								
				if ((servername.compareTo(sArray[0])== 0 ))
				{
					portNum = sArray[1];
					break;
				}
				else
				{
					
					portNum = "11560";
				}



			}

			inFromFile.close();
		}  catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return portNum;
	}
	
	
	public boolean credentialsSearch(String username,String password){
		
		
		boolean result = false;
		try{
			Scanner inFromFile = new Scanner(new File("C:/Users/Jindal's/workspace/Assignment1/src/username.txt"));

			while(inFromFile.hasNextLine()){

				String s = inFromFile.nextLine();  
				String[] sArray = s.split(",");

								
				if ((username.compareTo(sArray[0])== 0 && password.compareTo(sArray[1]) == 0))
				{
					
					result = true;
					break;
				}
				else
				{
					
					result = false;
				}



			}

			inFromFile.close();
		}  catch (FileNotFoundException e) {
			System.out.println(e);
		}




		return result;	
	}

	
}
