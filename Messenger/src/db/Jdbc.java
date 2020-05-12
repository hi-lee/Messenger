package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Jdbc {

	public static Connection getConnection() {
//		211.224.106.165,3678
		String url = "jdbc:sqlserver://211.224.106.165:3678;DatabaseName=UWAY";
		String user = "sa";
		String password = "stadmin";
		Connection conn = null;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, password);

			if (conn != null) {
				System.out.println("DB Connected");
			} else {
				System.out.println("DB DisConnected");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error : " + e);
		}
		return conn;
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commit(Connection con) {
		try {
			con.commit();
			System.out.println("commit success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection con) {
		try {
			con.rollback();
			System.out.println("rollback success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
