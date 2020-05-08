package Server;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		try {
//			서버 포트 넘버 8888
			ServerSocket s_socket = new ServerSocket(8888);
//			accept: 클라이언트 들어오는 것 대기
//			클라이언트가 8888포트로 연결 시도 > accept 대기에서 Socket 클래스 생성 후 반환
//			c_socket: 클라이언트와 통신 가능하게 함
			Socket c_socket = s_socket.accept();
			
//			OutputStream 객체에 서버에서 전달할 메시지 작성하여 전달
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
