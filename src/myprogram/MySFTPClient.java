package myprogram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MySFTPClient {
	public MySFTPClient() {
	}

	public static void main(String[] args) {
		MySFTPClient sftpClient = new MySFTPClient();
		sftpClient.doConnect();
		sftpClient.doLogin();
		sftpClient.doCommand();
	}

	public void doConnect() {

	}

	public void doLogin() {

	}

	public String doCommand() {
		String command = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("명령어입력: ");
			command = br.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return command;
	}

	public void doCd(String command) {
		if (command.equals("cd")) {

		}
	}
}
