import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientClass {

	
	public static void main(String[] args)  {
		Socket s = null;
		// TODO Auto-generated method stub
		
		String ip = "localhost";
		int port = 11363;
		
	System.out.println("Trying to connect to "+ip+" with port number: "+port);
	try{
		s = new Socket(ip,port);
		
		System.out.println("connected with Proxy Server");
		
		//user input
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		
		//writing
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		
		//reading
		BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
		out.writeBytes("Welcome I am client\n");
		
		String msg = in.readLine();
		System.out.println("From Proxy Server:"+msg);
		
		
		
		
		System.out.println("Please enter Username:");

		String username = user.readLine()+"\n";

		out.writeBytes(username);
		
		System.out.println("From Proxy Server: "+in.readLine());

		System.out.println("Please enter Password:");

		String password = user.readLine()+"\n";

		out.writeBytes(password);
		
		System.out.println("From Proxy Server: "+in.readLine());
		
		//authentication
		System.out.println("From Proxy Server: "+in.readLine());

		System.out.println("Please proide name of server to get connected:");
		
		out.writeBytes(user.readLine()+"\n");
		
		//message transmitting and finding length of common sub string
		
		System.out.println("Enter first string");
		
		String msg1 = user.readLine();
		
		System.out.println("Enter second String:");
		
		String msg2 = user.readLine();
		
		out.writeBytes(msg1+","+msg2+"\n");
		
		System.out.println("From Server:"+in.readLine());
		
	}catch(IOException e){
		e.printStackTrace();
		
	}finally {
		try {s.close();}catch (IOException e){e.printStackTrace();}
	}
	
		
	}

}
