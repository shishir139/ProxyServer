import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket ss = null;
		int portNum = 11550;
		Socket serverSock = null;
		DataOutputStream outToProxy;
		BufferedReader inFromProxy;
		try{
			ss = new ServerSocket(portNum);
			System.out.println("Server Started - waiting for connection");

			while(true){
				serverSock = ss.accept();
				
				System.out.println("Connected to Proxy server");
				outToProxy = new DataOutputStream(serverSock.getOutputStream());
				inFromProxy = new BufferedReader(new InputStreamReader(serverSock.getInputStream()));
				
				//greetings
				outToProxy.writeBytes("Welcome to server 1\n");
				
				
				//messages
				
				String msg = inFromProxy.readLine();
				
				System.out.println("From Client & proxy: "+msg);
				
				String[] msgArray = msg.split(",");
				
				int legth =LongestCommonSubString.findLengthOfLongestSubString(msgArray[0].toCharArray(), msgArray[1].toCharArray());
				
				String msgToClient = "Length of common SubString:"+legth;
				
				outToProxy.writeBytes(msgToClient+"\n");
				
				
			}

		}catch(IOException e){
			e.printStackTrace();

		}finally {
			try {ss.close();
			serverSock.close();}catch (IOException e){e.printStackTrace();}
		}

	}
}
