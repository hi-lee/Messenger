package Chat_copy;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	public static void main(String[] args) {

		String url = "jdbc:sqlserver://211.224.106.165,3678; database=UWAY;integratedSecurity=true";
		String user = "sa";
		String password = "stadmin";

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(url, user, password);

			if (conn != null) {
				System.out.println("DB Connected");
			} else {
				System.out.println("DB DisConnected");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
