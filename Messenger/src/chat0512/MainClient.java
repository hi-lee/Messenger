package chat0512;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
 
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
 
public class MainClient extends Application {
    Socket socket;
    TextArea textArea;
 
    // 클라이언트 동작 메소드
    public void startClient(String IP, int port) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, port);
                    receive();
                } catch (Exception e) {
                    // TODO: handle exception
                    if (!socket.isClosed()) {    
                        stopClient();
                        System.out.println("[서버 접속 실패]");
                        Platform.exit();// 프로그램 종료
                    }
                }
            }
        };
        thread.start();
    }
 
    // 클라이언트 종료 메소드
    public void stopClient() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // 서버로부터 메세지를 전달받는 메소드
    public void receive() {
        while (true) {
            try {
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[512];
                int length = in.read(buffer);
                if (length == -1)
                    throw new IOException();
                String message = new String(buffer, 0, length, "UTF-8");
                Platform.runLater(() -> {
                    textArea.appendText(message);
                });
            } catch (Exception e) {
                // TODO: handle exception
                stopClient();
                break;
            }
        }
    }
 
    // 서버로 메세지를 보내는 메소드
    public void send(String message) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = message.getBytes("UTF-8");
                    out.write(buffer);
                    out.flush();
                } catch (Exception e) {
                    // TODO: handle exception
                    stopClient();
                }
            }
        };
        thread.start();
    }
 
    // 동작 메소드
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5));
 
        HBox hBox = new HBox();
        hBox.setSpacing(5);
 
        TextField userName = new TextField();
        userName.setPrefWidth(150);
        userName.setPromptText("닉네임을 입력하세요");
        HBox.setHgrow(userName, Priority.ALWAYS);
 
        TextField IPText = new TextField("192.168.10.135");
        TextField portText = new TextField("2980");
        portText.setPrefWidth(80);
        IPText.setPrefWidth(150);// 픽셀단위
 
        hBox.getChildren().addAll(userName, IPText, portText);
        root.setTop(hBox);
 
        textArea = new TextArea();
        textArea.setDisable(false);
        root.setCenter(textArea);
 
        TextField input = new TextField();
        input.setPrefWidth(Double.MAX_VALUE);
        input.setDisable(true);
 
        /* 텍스트 입력창 동작 */
        input.setOnAction(event -> {
            send(userName.getText() + ":" + input.getText() + "\n");
            input.setText("");
            input.requestFocus();
        });
 
        /* 보내기 버튼 동작 */
        Button sendBtn = new Button("전송");
        sendBtn.setDisable(true);
 
        sendBtn.setOnAction(event -> {
            send(userName.getText() + ":" + input.getText() + "\n");
            input.setText("");
            input.requestFocus();
        });
 
        /* 접속 버튼 동작 */
        Button loginBtn = new Button("접속");
 
        loginBtn.setOnAction(event -> {
            if (loginBtn.getText().equals("접속")) {
                int port = 2980;
                try {
                    port = Integer.parseInt(portText.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startClient(IPText.getText(), port);
                Platform.runLater(() -> {
                    textArea.appendText("[채팅 접속]\n");
                });
                loginBtn.setText("종료");
                input.setDisable(false);
                sendBtn.setDisable(false);
                input.requestFocus();
            }else {
                stopClient();
                Platform.runLater(()->{
                    textArea.appendText("[채팅 종료]\n");
                });
                loginBtn.setText("접속");
                input.setDisable(true);
                sendBtn.setDisable(true);
            }
        });
 
        BorderPane pane = new BorderPane();
        pane.setLeft(loginBtn);
        pane.setCenter(input);
        pane.setRight(sendBtn);
        
        root.setBottom(pane);
        Scene scene = new Scene(root,400,400);
        primaryStage.setTitle("Chat Client");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event-> stopClient()); // 닫기 버튼을 눌렸을때 stopClient메소드 호출
        primaryStage.show();
        
        loginBtn.requestFocus();
        
        
    }
 
    // 실행 메소드
    public static void main(String[] args) {
        launch(args);
    }
}