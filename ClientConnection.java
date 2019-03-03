import java.net.*;
import java.io.*;


public class ClientConnection {

	Socket s;
	OutputStreamWriter os;
	PrintWriter out;
	BufferedReader br;
	
	public ClientConnection(String ip,int portNumber) throws UnknownHostException, IOException{
		
		s = new Socket(ip,portNumber);
		os = new OutputStreamWriter(s.getOutputStream());
		out = new PrintWriter(os);
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	public void sendMsg(String msg) throws IOException{
		out.write(msg);
		os.flush();
	}
	public String recieveMsg() throws IOException{
		return br.readLine();
	}
	public void closeConnection() throws IOException
	{
		s.close();
	}
	
}
