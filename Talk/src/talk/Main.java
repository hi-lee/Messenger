package talk;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 

public class Main extends Application  {
    /*
     * 여러 개의 Thread를 효율적으로 관리하는 대표적인 라이브러리 -> ThreadPool로 처리하게 되면 Thread숫자를 제한하기 떄문에
     * 서버폭증에 대비할 수 있다.
     */
    public static ExecutorService threadPool;
//    public static final String IP = "211.224.106.165";
//    public static final int PORT = 2980;
 
    /* 접속한 클라이언트들을 관리한다 -> 일종의 배열 */
    public static Vector<client> clients = new Vector<client>();
    ServerSocket serverSocket;
 
    /* 서버를 구동시켜서 클라이언트의 연결을 기다리는 메소드 */
    public void startServer(String IP, int PORT) {
    	IP = "211.224.106.165";
    	PORT = 2980;
    	
        try {
            serverSocket = new ServerSocket();
            /* 특정 클라이언트의 접속을 기다린다 */
            serverSocket.bind(new InetSocketAddress("211.224.106.165", 2980));
            
        } catch (Exception e) {// 오류 발생
//        	System.out.println("[ 서버 연결 오류 ] : " + e);
        	e.printStackTrace();
            if (!serverSocket.isClosed()) {// serverSocket이 닫혀 있는 상황이 아니라면
                stopServer();
            }
            return;
        }
        // 클라이언트가 접속할때 까지 기다린다.
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                    	System.out.println("[클라이언트 연결 대기중]");
                        Socket socket = serverSocket.accept();// 클라이언트의 접속을 기다린다.
                        clients.add(new client(socket));
                        System.out.println("[클라이언트 접속]" + socket.getRemoteSocketAddress() + ":"
                                + Thread.currentThread().getName());
                    } catch (Exception e) {
                        if (!serverSocket.isClosed()) {
                            stopServer();
                        }
                        break;
                    }
                }
 
            }
        };
        /* threadpool을 초기화 한 다음, 첫 번째 thread를 넣어준다. */
        threadPool = Executors.newCachedThreadPool();
        threadPool.submit(thread);
    }// startServer()
    
    /* 서버의 동작을 중지시키는 메소드 */
    public void stopServer() {
        try {
            // 현재 작동 중인 모든 소켓 닫기
            /* Iterator통해서 모든 클라이언트에 개별적으로 접근할 수 있도록 할 수 있다 */
            Iterator<client> iterator = clients.iterator();
            while (iterator.hasNext()) {
                client client = iterator.next();
                client.socket.close();
                iterator.remove();
            }
            // serversocket 객체 닫기
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            // threadPool 종료하기
            if (threadPool != null && threadPool.isShutdown()) {
                threadPool.isShutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// stopServer()
    /* 화면설정 */
    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
        
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("나눔고딕",15));
        root.setCenter(textArea);
        
        Button toggleButton = new Button("시작하기");
        toggleButton.setMaxWidth(Double.MAX_VALUE);
        BorderPane.setMargin(toggleButton, new Insets(1,0,0,0));
        root.setBottom(toggleButton);
        
        String IP ="211.224.106.165";
        int PORT = 2980;
        
        toggleButton.setOnAction(event->{
            if(toggleButton.getText().equals("시작하기")) {
                startServer(IP, PORT);
//            	startServer();
                Platform.runLater(()->{
                    String message = String.format("[서버 시작]\n",IP,PORT);
                    textArea.appendText(message);
                    toggleButton.setText("종료하기");
                });
            }else {
                stopServer();
                Platform.runLater(()->{
                    String message = String.format("[서버 종료]\n",IP,PORT);
                    textArea.appendText(message);
                    toggleButton.setText("시작하기");
                });
                
            }
        });
        
        Scene scene = new Scene(root,400,400);
        primaryStage.setTitle("Chat Server");
        primaryStage.setOnCloseRequest(event->stopServer());
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }// start()
 
    public static void main(String[] args) {
        launch(args);
    }// main()
}