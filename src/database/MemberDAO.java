package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	//싱글톤패턴 선언
	//static : 프로그램이 메모리에 적재될때, dao를 만들어라
	private static MemberDAO dao = new MemberDAO();

	private MemberDAO() {
	}

	/*
	 * 싱글톤 패턴 : 하나의 객체만을 생성해서 사용할 수 있도록 설계한 구조이다.
	 * 1 생성자의 접근제어자는 private
	 * 2 객체자신을 생성을	 한다.
	 * 3 생성된 객체를 넘겨줄 수 있는 메소드를 정의한다.
	 */
	//이 클래스의 인스턴스를 사용하는 유일한 방법
	public static MemberDAO getInstance() {
		return dao;
	}

	private Connection init() throws SQLException {
		// 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// 서버 연결
		String url = "jdbc:oracle:thin:@192.168.0.126:1521:orcl";

		// RAC 방법
		// https://www.ilhoko.com/entry/JDBC-URL-for-Oracle-RAC
		/*
		 * String racUrl = "jdbc:oracle:thin:@(DESCRIPTION=" +
		 * "(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.126)(PORT=1521))" +
		 * "(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.126)(PORT=1521))" +
		 * "(FAILOVER=on)(LOAD_BALANCE=off))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=orcl)))";
		 */

		String user = "ism";
		String password = "123123";

		return DriverManager.getConnection(url, user, password);
		// return DriverManager.getConnection(racUrl, user, password);

	}// end init()

	private void exit() throws SQLException {
		if (rs != null)
			rs.close();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}// end exit()

	public List<MemberDTO> listMethod() {
		List<MemberDTO> aList = new ArrayList<MemberDTO>();

		try {
			conn = init();
			// 쿼리문 실행을 위한 Statement타입의 객체를 리턴
			stmt = conn.createStatement();
			String sql = "SELECT * FROM MEMBER";
			// 쿼리문 실행
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserId(rs.getString("user_id"));
				dto.setUserName(rs.getString("user_name"));
				dto.setUserBirth(rs.getString("user_birth"));
				dto.setUserEmail(rs.getString("user_email"));
				aList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원반납
				exit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return aList;
	}// listMethod()

}// end class
