import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public static void main(String[] args) {
		final int SERVER_PORT = 5000;
		ServerSocket serverSocket = null;
		try {
//		1. 서버 소켓 객체 생성
			serverSocket = new ServerSocket();

//		2. 소켓을 호스트의 포트와 binding
			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(localHostAddress, SERVER_PORT));
			System.out.println("[server] binding! \n address: " + localHostAddress);

//		3. 클라이언트의 연결 요청이 올때까지 대기, 요청전까지 서버는 block 상태
			Socket socket = serverSocket.accept();

//		4. 연결 요청이 오면 메세지 출력
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
