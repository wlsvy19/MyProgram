package program;

import java.util.Scanner;
//https://limkydev.tistory.com/78

/*프로그램을 보면 직접 호출하지 않고 왜 저렇게 거치고 거쳐서 호출하는 걸까라는 생각이 들 수 있습니다.
만약 직접 호출이 안되는 경우이거나, 기존에 있는 것을 변환해서 호출해야 하는 경우를 생각하면 기존에 있는
Protocol를 수정하지 않은 상태에서 interface와 Adapter만 수정하여 Main에서 원하는 메소드로 변화 시켜 호출 시켜 주는 것이라 생각하면 이해에 도움이 될것입니다.*/

/*어댑터에는 두종류가 있다.
1. 객체 어댑터
2. 클래스 어댑터
클래스 어댑터 패턴을 쓰려면 다중 상속이 필요한데, 자바는 기본적으로 다중 상속이 불가능.*/

public class Client {
	public static void main(String[] args) {

		//클래스 어댑터 패턴 사용
		//Connectable connect = new AdapterProtocol();

		//객체 어댑터 패턴 사용
		Connectable connect = new AdapterProtocolByObject();
		System.out.print("프로토콜 입력 : ");
		Scanner sc = new Scanner(System.in);
		String protocol = sc.nextLine();

		if (protocol.equals("ftp") || protocol.equals("FTP")) {
			connect.myFTPClient();
		} else if (protocol.equals("sftp") || protocol.equals("SFTP")) {
			connect.mySFTPClient();
		}else {
			System.out.println("유효하지 않습니다. 종료합니다.");
			System.exit(0);
		}
		sc.close();
	}// end main()
}
