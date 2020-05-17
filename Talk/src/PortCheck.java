//https://goni9071.tistory.com/78

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class PortCheck {
	public static void main(String[] args) throws UnknownHostException, IOException {
	    SocketAddress endpoint =  new InetSocketAddress("211.224.106.165", 2980);
	    new Socket().connect(endpoint, 3000);
	    System.out.println("OK");
	  }


}
