import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		final int SERVER_PORT = 5000;
		ServerSocket serverSocket = null;
		try {
//		1. ���� ���� ��ü ����
			serverSocket = new ServerSocket();

//		2. ������ ȣ��Ʈ�� ��Ʈ�� binding
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding! \n address: " + localHostAddress);

//		3. Ŭ���̾�Ʈ�� ���� ��û�� �ö����� ���, ��û������ ������ block ����
			Socket socket = serverSocket.accept();

//		4. ���� ��û�� ���� �޼��� ���
			InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteSocketAddress.getPort();
			System.out.println(
					"[server] connected! \n connected socket address: " + remoteHostName + ", port: " + remoteHostPort);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
