package programbackup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgramStart {
	public static void main(String[] args) {// 메인 메소드
		// 문자열 입력해서 문자열 배열에 넣고, 그 배열안에 특정 문자열이 있으면 특정 메소드로 이동?????
		String command = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		MyFTPClient myFTPClient = new MyFTPClient();
		MySFTPClient mySFTPClinet = new MySFTPClient();
		System.out.print("프로토콜 유형: ");
		try {
			command = br.readLine();
			if (command.equals("ftp") || command.equals("FTP")) {
				myFTPClient.start();
			} else if (command.equals("sftp") || command.equals("SFTP")) {
				mySFTPClinet.start();
			} else {
				System.out.println("어떤 프로토콜을 쓸건지 다시 입력해주세요.");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
