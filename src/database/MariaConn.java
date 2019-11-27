package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaConn {
	String driver = "org.mariadb.jdbc.Driver";
	Connection conn;
//20191127
	public void DBconn() {
		//드라이버 :// ip : 포트번호 / 데이터베이스 이름
		String url = "jdbc:mariadb://192.168.0.126:3306/mysql";
		String user = "ism";
		String password = "123123";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
			if (conn != null) {
				System.out.println("DB접속 성공");
			}
		} catch (SQLException e) {
			System.out.println("DB접속 실패");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MariaConn dbconn = new MariaConn();
		dbconn.DBconn();

		float a=3,b=6;
		System.out.printf("%.0f", b/a);
	}
}
