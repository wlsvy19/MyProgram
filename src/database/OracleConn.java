package database;

import java.util.List;

public class OracleConn {
	public static void main(String[] args) {
		MemberDAO dao = MemberDAO.getInstance();
		List<MemberDTO> aList = dao.listMethod();
		for (int i = 0; i < aList.size(); i++) {
			MemberDTO dto = aList.get(i);
			System.out.printf("ID:%s NAME:%s BIRT:%s EMAIL:%s\n", dto.getUserId(), dto.getUserName(), dto.getUserBirth(),
					dto.getUserEmail());
		} // end for
	}// end main()
}// end class
