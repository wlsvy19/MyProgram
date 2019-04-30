package myprogram;

import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class MySFTPClient {
	public MySFTPClient() {
	}

	public static void main(String[] args) {
		MySFTPClient sftpClient = new MySFTPClient();
		sftpClient.Connect();
		// sftpClient.Command();
	}

	public void Connect() {
		// https://lahuman.jabsiri.co.kr/152
		//DH알고리즘을 쓰기위한 코드
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		try {
			java.security.KeyPairGenerator.getInstance("DH");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		try {
			javax.crypto.KeyAgreement.getInstance("DH");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();

		Scanner scanner = new Scanner(System.in);
		System.out.println("username, host, password 순으로 입력");
		String username = scanner.nextLine();
		String host = scanner.nextLine();
		String password = scanner.nextLine();
		// int port = scanner.nextInt();

		try {
			// 세션 객체 생성
			session = jsch.getSession(username, host, 22);
			// 비밀번호설정
			session.setPassword(password);
			// 호스트 정보를 검사하지 않음
			session.setConfig("StrictHostKeyChecking", "no");
			// 세션접속
			session.connect();
			// sftp채널열기
			channel = session.openChannel("sftp");
			// 채널접속
			channel.connect();
			System.out.println("Connected to user@" + host);
		} catch (JSchException e) {
			e.printStackTrace();
		} finally {

		}

		ChannelSftp channelSftp = (ChannelSftp) channel;

		while (true) {
			System.out.print("sftp> ");
			String str = "";
			str = scanner.nextLine();
			String command = str.split("\\s")[0];

			if (command.equals("cd")) {
			}
			if (command.equals("pwd")) {
				try {
					System.out.println("Remote working directory: " + channelSftp.pwd());
				} catch (SftpException e) {
					e.printStackTrace();
				}
				continue;
			}
			if (command.equals("quit")) {
				channelSftp.quit();
				break;
			}
			if (command.equals("get")) {

			}
			if (command.equals("put")) {

			}
			if (command.equals("ls")) {

			}
		}
		channelSftp.disconnect();
		scanner.close();
		System.exit(0);
	}

}
