package talk.svc;

import static db.Jdbc.close;
import static db.Jdbc.commit;
import static db.Jdbc.getConnection;
import static db.Jdbc.rollback;

import java.sql.Connection;

import talk.dao.TalkDAO;
import talk.vo.Talk;

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
		
		return insertResult;
	}


}
