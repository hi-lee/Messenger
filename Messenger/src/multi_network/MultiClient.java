package multi_network;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.JProgressBar;

public class MultiClient implements ActionListener {
	private Socket socket;
	private ObjectInputStream ois;	//입력
	private ObjectOutputStream oos;	//출력
	private JFrame jframe, login1;	//윈도우창, 로그인창
	private JTextField jtf, idc, pass;
	private JTextArea jta, jlo;
	private JLabel jlb1, jlb2, jID, jPW;
	private JPanel jp1, jp2, jp3, jp4;
	private String id;
	private String ip = "ip content";
	private JButton jbtn, jbtn1, jexit;
	public boolean changepower = false;
	public boolean saypower = false;
	private boolean login = false;

	public MultiClient() {
		
//		ip = argIp; 
//		id = argId;
//		System.out.println("ip : " + ip);
		 
		jframe = new JFrame("Multi Chatting");
		login1 = new JFrame("Login");
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		progressBar.setBounds(32, 303, 195, 14);

		jtf = new JTextField(20);
		idc = new JTextField(20);
		pass = new JTextField(20);

		jta = new JTextArea(43, 43) {
			{
				setOpaque(false);
			}

		};
		jlo = new JTextArea(30, 30);
		jlb1 = new JLabel("TALK") {
			{
				setOpaque(false);
			}
		};
		jlb2 = new JLabel("IP : " + ip) {
			{
				setOpaque(true);
			}
		};
		jID = new JLabel("IP");
		jPW = new JLabel("name");
		jbtn = new JButton("EXIT");
		jbtn1 = new JButton("Login");
		jexit = new JButton("exit");
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jbtn.setFont(new Font("Arial", Font.PLAIN, (int) 20));
		jlb1.setFont(new Font("Arial", Font.PLAIN, (int) 15));
		jlb1.setBackground(Color.lightGray);
		jlb2.setBackground(Color.lightGray);
		jlb2.setFont(new Font("Arial", Font.PLAIN, (int) 15));

		jID.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		jID.setHorizontalAlignment(jID.CENTER);
		jPW.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		jPW.setHorizontalAlignment(jPW.CENTER);

		idc.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		idc.setBackground(Color.WHITE);
		pass.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		pass.setBackground(Color.WHITE);
		jbtn1.setBackground(Color.lightGray);
		jbtn1.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		jexit.setBackground(Color.lightGray);
		jexit.setFont(new Font("Arial", Font.PLAIN, (int) 30));
		jbtn.setBackground(Color.lightGray);
		jlo.setBackground(Color.lightGray);

		jp1.setLayout(new BorderLayout());
		jp2.setLayout(new BorderLayout());
		jp3.setLayout(new GridLayout(3, 2, 10, 10));

		jp1.add(jbtn, BorderLayout.EAST);
		jp1.add(jtf, BorderLayout.CENTER);
		jp2.add(jlb1, BorderLayout.CENTER);
		jp2.add(jlb2, BorderLayout.EAST);

		jp1.setBackground(Color.lightGray);
		jp2.setBackground(Color.lightGray);
		jp3.setBackground(Color.lightGray);
		jp3.add(jID);
		jp3.add(idc);
		jp3.add(jPW);
		jp3.add(pass);
		jp3.add(jbtn1);
		jp3.add(jexit);
		jframe.add(jp1, BorderLayout.SOUTH);
		jframe.add(jp2, BorderLayout.NORTH);
		login1.add(jp3, BorderLayout.EAST);
		login1.add(jp4, BorderLayout.EAST);

		JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jframe.add(jsp, BorderLayout.CENTER);
		JScrollPane jsp1 = new JScrollPane(jlo, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		login1.add(jp3, BorderLayout.CENTER);

		jtf.addActionListener(this);
		jbtn.addActionListener(this);
		jexit.addActionListener(this);

		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					oos.writeObject(id + "#exit");
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				System.exit(0);
			}

			public void windowOpened(WindowEvent e) {
				jtf.requestFocus();
			}
		});

		jbtn1.addActionListener(this);

		jta.setEditable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;

		jframe.pack();
		jframe.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
		jframe.setResizable(false);
		jframe.setVisible(false);

		login1.pack();
		login1.setSize(800, 300);
		login1.setLocation((screenWidth - jframe.getWidth()) / 2, (screenHeight - jframe.getHeight()) / 2);
		login1.setResizable(false);
		login1.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String msg = jtf.getText();

		String str = e.getActionCommand();

		if (str.equals("Login")) {
			jframe.setVisible(true);
			login1.setVisible(false);

			ip = idc.getText();
			id = pass.getText();
		}

		if (str.equals("exit")) {
			System.exit(0);
		}
		
		
/*
		if (obj == jtf && changepower == true) {
			changepower = false;
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "changepower", "warning", JOptionPane.WARNING_MESSAGE);
			} else {
				id = jtf.getText();
				jtf.setText("");
			}
		} else if (obj == jtf && saypower == true) {
			saypower = false;
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "saypower", "warning", JOptionPane.WARNING_MESSAGE);
			} else {
				id = jtf.getText();
				jtf.setText("");
			}
		}
*/
		
		
		if (obj == jtf) { // 엔터 친 경우
//			텍스트필드에 입력하지 않은 경우 경고창
			if (msg == null || msg.length() == 0) {
				JOptionPane.showMessageDialog(jframe, "값을 입력하세요", "warning", JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					oos.writeObject(id + "#" + msg);
				} catch (IOException ee) {
					ee.printStackTrace();
				}
				jtf.setText("");
			}
		} else if (obj == jbtn) { 	// 종료버튼 클릭한 경우
			try {
				oos.writeObject(id + "#exit");
			} catch (IOException f) {
				f.printStackTrace();
			}
			System.exit(0);
		}
	}

	public void exit() {
		System.exit(0);
	}

	
//	서버와 송수신을 위해 소켓 객체와 연결하여 Stream 발생
	public void init() throws IOException {
		socket = new Socket("192.168.10.135", 5000);
		System.out.println("connected...");
//		입출력 스트림 생성
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		MultiClientThread ct = new MultiClientThread(this);
		Thread t = new Thread(ct);
		t.start();
	}

	public static void main(String args[]) throws IOException {
		JFrame.setDefaultLookAndFeelDecorated(true);
		MultiClient cc = new MultiClient();
		cc.init();
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public JTextArea getJta() {
		return jta;
	}

	public String getId() {
		return id;
	}

	public void SetName(String a) {
		id = a;
	}

	public void Clear() {
		jta.setText("");
		jtf.requestFocus();
	}

	public void My() {
		jta.setDisabledTextColor(Color.blue);
	}
}