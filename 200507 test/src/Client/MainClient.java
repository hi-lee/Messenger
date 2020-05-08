package Client;

import java.net.Socket;

import org.omg.CORBA.portable.InputStream;

public class MainClient {
	public static void main(String[] args) {
		try {
			Socket c_socket = new Socket("192.168.10.20", 8888);
			
			InputStream input_data = c_socket.getInputStream();
			
			byte[] receiveBuffer = new byte[100];
			input_data.read(receiveBuffer);
			
			System.out.println(new String(receiveBuffer));
			
			c_socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
