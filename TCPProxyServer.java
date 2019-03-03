import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPProxyServer {
	public static void main (String args[]) {
		ServerSocket listenSocket = null;
		try{
			int serverPort = 11363;
			listenSocket = new ServerSocket(serverPort);
			System.out.println("Proxy Server started !! -- Waiting for connection");
			
			while(true) {
				
					
				Socket clientSocket = listenSocket.accept();
				System.out.println("connection started with client");
				ServerConnection c = new ServerConnection(clientSocket);
						
			}
		} catch(IOException e) {System.out.println("Listen :"+e.getMessage());}
		
		finally {
			try {listenSocket.close();}
			catch (IOException e){e.printStackTrace();}
	}
	}
}

