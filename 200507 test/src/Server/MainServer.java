package Server;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		try {
//			���� ��Ʈ �ѹ� 8888
			ServerSocket s_socket = new ServerSocket(8888);
//			accept: Ŭ���̾�Ʈ ������ �� ���
//			Ŭ���̾�Ʈ�� 8888��Ʈ�� ���� �õ� > accept ��⿡�� Socket Ŭ���� ���� �� ��ȯ
//			c_socket: Ŭ���̾�Ʈ�� ��� �����ϰ� ��
			Socket c_socket = s_socket.accept();
			
//			OutputStream ��ü�� �������� ������ �޽��� �ۼ��Ͽ� ����
			OutputStream output_data = c_socket.getOutputStream();
			
			String sendDataString = "Welcome to My Server";
			output_data.write(sendDataString.getBytes());
			
			s_socket.close();
			c_socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
