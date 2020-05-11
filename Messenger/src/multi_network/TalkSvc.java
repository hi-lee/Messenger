package multi_network;

import static db.Jdbc.*;

import java.sql.Connection;

public class TalkSvc {

	public boolean insertTalk(Talk talk) {
		// TODO Auto-generated method stub
		boolean insertResult = false;
		Connection con = getConnection();
		TalkDAO talkDAO = TalkDAO.getInstance();
		talkDAO.setConnection(con);
		
		int insertTalk = talkDAO.insertTalk(talk);
		
		if(insertTalk > 0) {
			commit(con);
			insertResult = true;
		} else {
			rollback(con);
		}
		close(con);
		
		System.out.println("id : " + talk.getUserId());
		
		return insertResult;
	}


}
