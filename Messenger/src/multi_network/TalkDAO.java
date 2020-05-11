package multi_network;

import java.sql.Connection;
import java.sql.PreparedStatement;
import static db.Jdbc.*;


public class TalkDAO {
	Connection con;
	private static TalkDAO talkDAO;
	
	public static TalkDAO getInstance() {
		// TODO Auto-generated method stub
		if(talkDAO == null) {
			talkDAO = new TalkDAO();
		}
		return talkDAO;
	}

	public void setConnection(Connection con2) {
		// TODO Auto-generated method stub
		this.con = con;
	}

	public int insertTalk(Talk talk) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			pstmt = con.prepareStatement("INSERT INTO eval_talk VALUES(?,?,?)");
			pstmt.setString(1, talk.getUserId());
			pstmt.setString(2, talk.getBody());
			pstmt.setString(3, talk.getDate());
			insertCount = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("insert Err : " + e);
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	
}
