package multi_network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import talk.svc.TalkSvc;
import talk.vo.Talk;

public class MultiClientThread extends Thread {
	private MultiClient mc;

	public MultiClientThread(MultiClient mc) {
		this.mc = mc;
	}

	public void run() {
		String message = null;
		String[] receivedMsg = null;
		String ip = null;
		Talk talk = null;

		boolean isStop = false;
		while (!isStop) {
			try {
//				서버에서 전달된 데이터 처리
				message = (String) mc.getOis().readObject();
//				id#메세지 형태로 전달, #기준으로 분리
				receivedMsg = message.split("#");

			} catch (Exception e) {
				e.printStackTrace();
				isStop = true;
			}

//			exit 메세지 인경우 DB insert X
			if (!receivedMsg[1].equals("exit")) {

				talk = new Talk();
//			#기준으로 분리된 메세지를 talk객체에 담음
				talk.setUserId(receivedMsg[0]);
				talk.setBody(receivedMsg[1]);

//			DB연결을 위한 서비스
				TalkSvc talkSvc = new TalkSvc();
				boolean insertResult = talkSvc.insertTalk(talk);

				if (insertResult == false) {
					System.out.println("insert FAIL");
				} else {
					System.out.println("insert SUCCESS");
				}
			}

			System.out.println("ID: " + receivedMsg[0] + ", body: " + receivedMsg[1]);
//            0: id, 1: message

			if (receivedMsg[1].equals("exit")) {
//				전송된 아이디가 사용자 아이디와 동일 할 경우 종료
				if (receivedMsg[0].equals(mc.getId())) {
					mc.exit();
				} else {
//					아이디가 다를 경우 사용자 종료가 아닌 타이용자
					mc.getJta().append(receivedMsg[0] + "님이 종료하셨습니다." + System.getProperty("line.separator"));
					mc.getJta().setCaretPosition(mc.getJta().getDocument().getLength());
				}
			}

			else {
				mc.getJta().append(receivedMsg[0] + " : " + receivedMsg[1] + System.getProperty("line.separator"));
				mc.getJta().setCaretPosition(mc.getJta().getDocument().getLength());
			}
		}
	}
}