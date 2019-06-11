package programbackup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class MyFTPClient {

	public void start() {
		FTPClient ftpClient = new FTPClient();
		// 서버접속
		Scanner scanner = new Scanner(System.in);
		System.out.print("호스트 주소 입력: ");
		String server = scanner.nextLine();
		try {
			ftpClient.connect(server, 21);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				System.out.println(server + "에 연결됐습니다.");
				System.out.println(ftpClient.getReplyCode() + " SUCCESS CONNECTION.");
			}
		} catch (Exception e) {
			System.out.println("서버 연결 실패");
			System.exit(0);
		}
		// 계정입력
		System.out.print("계정 입력: ");
		String user = scanner.nextLine();
		System.out.print("비밀번호 입력: ");
		String password = scanner.nextLine();
		try {
			ftpClient.login(user, password);
			System.out.println(ftpClient.getReplyCode() + " Login successful.");
		} catch (IOException e) {
			System.out.println(ftpClient.getReplyCode() + " Login incorrect.");
			System.exit(0);
		}

		// 명령어시작
		while (true) {
			System.out.print("ftp> ");
			String str = "";
			str = scanner.nextLine();
			String[] params = str.split(" ");
			String command = params[0];
			// 명령어 시작
			if (command.equals("cd")) {
				String path = params[1];
				try {
					ftpClient.changeWorkingDirectory(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end cd
			else if (command.equals("mkdir")) {
				String directory = params[1];
				try {
					ftpClient.makeDirectory(directory);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end mkdir
			else if (command.equals("rmdir")) {
				String directory = params[1];
				try {
					ftpClient.removeDirectory(directory);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end rmdir
			else if (command.equals("binary")) {
				try {
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					System.out.println(ftpClient.getReplyCode() + " Switching to Binary mode.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end binary
			else if (command.equals("ascii")) {
				try {
					ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
					System.out.println(ftpClient.getReplyCode() + "  Switching to ASCII mode.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end ascii
			else if (command.equals("pwd")) {
				try {
					System.out.println(ftpClient.printWorkingDirectory());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end pwd
			else if (command.equals("quit")) {
				try {
					ftpClient.logout();
					System.out.println(ftpClient.getReplyCode() + " Goodbye.");
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end quit
			else if (command.equals("put")) {
				// storeFile(String remoteName, InputStream local) local 입력 스트림으로부터 데이터를 읽어와 FTP
				// 서버에 remoteName 경로로 업로드한다. 성공적으로 파일을 업로드 하면 true를 리턴한다.

				// 문제점>>>2개의 인자가 필요하고, 경로 맨뒤에 '파일명'을 안쓰면 java.io.FileNotFoundException:
				// C:\Users\solulink (액세스가 거부되었습니다) 이런 오류가남
				// 그래서 아래와같이 하면 오류는 안나지만
				// put window.txt C:\Users\solulink\hi.txt -> window.txt로 서버에 저장
				String p1 = str.split(" ")[1];
				String p2 = str.split(" ")[2];

				File putFile = new File(p2);
				InputStream inputStream = null;

				try {
					inputStream = new FileInputStream(putFile);
					boolean result = ftpClient.storeFile(p1, inputStream);
					if (result == true) {
						System.out.println("업로드 성공");
					} else {
						System.out.println("업로드 실패");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} // end put
			else if (command.equals("get")) {
				// retriveFile(String remoteName, OutputStream local) FTP 서버의 remoteName 파일을
				// local에 지정된 출력 스트림에 다운로드 한다. 성공적으로 완료되면 true를 리턴한다.
				// retrieveFileStream(String remoteName) FTP 서버의 remoteName 파일로부터 데이터를 읽어오는 입력
				// 스트림을 리턴한다.

				// 문제점>>>역시 2개의 인자가 필요하고, 경로 맨뒤에 '파일명'을 안쓰면 java.io.FileNotFoundException:
				// C:\Users\solulink (액세스가 거부되었습니다) 이런 오류가남
				// 파일이 없다는 오류지 근데왜!!!
				// get centos.txt C:\Users\solulink\hello.txt 하면 centos.txt파일이 hello.txt로 바뀌면서
				// 다운로드 되냐고.....

				String p1 = str.split(" ")[1];
				String p2 = str.split(" ")[2];

				File getFile = new File(p2);
				OutputStream outputStream = null;

				try {
					outputStream = new FileOutputStream(getFile);
					boolean result = ftpClient.retrieveFile(p1, outputStream);
					if (result == true) {
						System.out.println("다운로드 성공");
					} else {
						System.out.println("다운로드 실패");
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} // end get
			else if (command.equals("delete")) {
				String file = str.split(" ")[1];
				try {
					ftpClient.deleteFile(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end rm
			else if (command.equals("ls")) {
				String[] files = null;
				try {
					files = ftpClient.listNames();
					for (int i = 0; i < files.length; i++) {
						System.out.println(files[i]);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end ls
			else if (command.equals("dir")) {
				FTPFile[] files = null;
				try {
					files = ftpClient.listFiles();
					for (int i = 0; i < files.length; i++) {
						System.out.println(files[i]);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end dir
		} // end while
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
		System.exit(0);
	}
}