package myprogram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class MyFTPClient {

	static FTPClient ftpClient = new FTPClient();

	public MyFTPClient() {
	}

	public static void main(String[] args) {
		try {
			mainProc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end main()

	public static void doConnect() {
		Scanner sc = new Scanner(System.in);
		try {
			// 포트는 여기 메소드에서 밖에 쓰이지 않으므로 지역변수로 선언.
			int port = 21;
			System.out.print("FTP서버 주소 입력: ");
			String server = sc.next();
			// FTPServer에 Connect서버 연결
			ftpClient.connect(server, port);
			// 응답이 정상적인지 확인 하기 위해 응답 받아오기
			int replyCode = ftpClient.getReplyCode();
			// 서버와의 응답이 정상인지 확인
			if (FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println(server + "에 연결되었습니다.");
				System.out.println(ftpClient.getReplyCode() + " SUCCESS CONNECTION.");
			}
		} catch (SocketException e) {
			System.out.println("서버 연결 실패");
			System.exit(1);

		} catch (IOException e) {
			System.out.println("서버 연결 실패");
			System.exit(1);
		}

	}// end doConnect()

	// FTP서버 로그인
	public static boolean doLogin() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.print("사용자: ");
			String id = sc.next();
			System.out.print("암호: ");
			String pw = sc.next();
			boolean success = ftpClient.login(id, pw);
			if (!success) {
				System.out.println(ftpClient.getReplyCode() + " Login incorrect.");
				System.exit(1);
			} else {
				System.out.println(ftpClient.getReplyCode() + " Login successful.");
			}
		} catch (IOException e) {
			System.out.println(ftpClient.getReplyCode() + " Login incorrect.");
			return false;
		} finally {
		}
		return true;
	}// end doLogin()

	// mainProc 메소드
	// Ftp의 명령 메뉴를 출력하여 해당되는 처리를 호출한다.
	public static void mainProc() throws IOException {
		boolean command = true;
		try {
			// 서버접속
			doConnect();
			// 로그인
			doLogin();
			while (command) {
				// 메뉴출력
				showMenu();
				// 명령을 받아서 처리한다.
				command = exeCommand(getCommand());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}// end mainProc()

	// getCommand 메소드
	// 이용자가 지정한 명령 번호를 읽어 처리한다.
	public static String getCommand() {
		String buf = "";
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		// 한글자, 두글자 명령어 입력을 받을 때까지 반복한다.
		while (buf.length() != 1 && buf.length() != 2) {
			try {
				buf = lineRead.readLine();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			} finally {
				if (lineRead != null) {
					try {
						lineRead.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return (buf);
	}// end getCommand()

	// showMenu 메소드
	// FTP의 명령 메뉴를 출력한다.
	public static void showMenu() {
		System.out.println("명령어에 해당하는 번호 입력>");
		System.out.print("1 ls");
		System.out.print("    2 dir");
		System.out.print("    3 cd");
		System.out.print("    4 put");
		System.out.print("    5 get");
		System.out.print("    6 mkdir");
		System.out.print("    7 rmdir");
		System.out.print("    8 delete");
		System.out.print("    9 append");
		System.out.println("    10 binary");
		System.out.print("11 status");
		System.out.print("    12 pwd");
		System.out.print("    13 ascii");
		System.out.println("    14 quit");
	}// end showMenu()

	// execCommand 메소드
	// 명령에 대응하는 각 처리를 호출한다.
	public static boolean exeCommand(String command) {
		boolean com = true;
		switch (Integer.parseInt(command)) {
		case 1:
			doLs();// 목록보기
			break;
		case 2:
			doDir();// 목록보기
			break;
		case 3:
			doCd();// 디렉토리 이동
			break;
		case 4:
			doPut();// 서버에 파일 전송
			break;
		case 5:
			doGet();// 서버로부터 파일 다운로드
			break;
		case 6:
			doMkdir();// 디렉토리 생성
			break;
		case 7:
			doRmdir();// 디렉토리 삭제
			break;
		case 8:
			doDelete();// 파일 지우기
			break;
		case 9:
			doAppend();// 파일 붙이기
			break;
		case 10:
			doBinary();// 바이너리 전송 모드
			break;
		case 11:
			doGetStatus();// 상태보기
			break;
		case 12:
			doPwd();// 현재 작업중인 디렉토리 보기
			break;
		case 13:
			doAscii();// 아스키 전송 모드
			break;
		case 14:
			doQuit();// 접속종료
			com = false;
			break;
		default: // 그 이외의 입력 처리
			System.out.println("잘못된 번호를 선택하셨습니다. 다시 골라주세요.");
		}
		return (com);
	}// end exeCommand()

	public static String[] doLs() {
		String[] files = null;
		try {
			files = ftpClient.listNames();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}
		} catch (IOException e) {
			System.out.println("서버로 부터 파일 리스트를 가져오지 못했습니다.");
		}
		return null;
	}// end doLs()

	public static FTPFile[] doDir() {
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i]);
			}
		} catch (IOException e) {
			System.out.println("서버로 부터 디렉토리를 가져오지 못했습니다.");
		}
		return null;
	}// end doDir()

	public static void doCd() {
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		String path = "";
		System.out.print("어디로 이동하시겠습니까?");
		try {
			path = lineRead.readLine();
			ftpClient.changeWorkingDirectory(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doCd()

	public static void doPut() {
		// 여기에 있는 파일의 내용으로 업로드
		File putFile = new File("C:\\Users\\solulink", "window.txt");
		String fileName = "";
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("서버에 업로드 할 파일 이름을 입력하세요: ");
		InputStream inputStream = null;
		try {
			// PUT할 파일명 입력
			fileName = lineRead.readLine();
			inputStream = new FileInputStream(putFile);
			// 기능A01 ..
			// 기능A02 ...
			// 기능A03 ...
			ftpClient.storeFile(fileName, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();// close를 하지 않는다면, 나중에 시스템부하가 일어날것이야.
					// 이런식으로 조건을 줘서 finally구문으로 마지막 한줄로 close()를 하자.
				} catch (IOException e1) {
				}
			}
		}
	}// end doPut()

	public static void doGet() {
		// 이경로의 파일 이름으로, 저장됨
		File getFile = new File("C:\\Users\\solulink\\centos.txt");
		String fileName = null;
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("서버로 부터 다운로드 할 파일 이름을 입력하세요: ");
		OutputStream outputStream = null;
		try {
			// GET할 파일명 입력
			fileName = lineRead.readLine();
			outputStream = new FileOutputStream(getFile);
			ftpClient.retrieveFile(fileName, outputStream);
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
	}// end doGet()

	public static void doAppend() {
		// append a b -> b에 a내용이 더해짐.
		Scanner sc = new Scanner(System.in);
		String pathName = "";
		System.out.println("append a b -> b에 a내용이 더해짐.");
		System.out.print("a파일입력: ");
		pathName = sc.nextLine();
		// File appendFile = new File("C:\\Users\\solulink\\window.txt");//
		// window.txt파일을 내가 입력한 파일에 append한다.
		File appendFile = new File(pathName);
		String fileName = "";
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("b파일입력: ");
		InputStream inputStream = null;
		try {
			fileName = lineRead.readLine();
			inputStream = new FileInputStream(appendFile);
			ftpClient.appendFile(fileName, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					sc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}// end doAppend()

	public static void doMkdir() {//디렉토리 생성 메소드
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		String directoryName = "";
		try {
			System.out.print("생성할 디렉토리 이름 입력 :");
			directoryName = lineRead.readLine();
			ftpClient.makeDirectory(directoryName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doMkdir()

	public static void doRmdir() {
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		String directoryName = "";
		try {
			System.out.print("삭제할 디렉토리 이름 입력 :");
			directoryName = lineRead.readLine();
			ftpClient.removeDirectory(directoryName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end rmMkdir()

	public static void doDelete() {
		BufferedReader lineRead = new BufferedReader(new InputStreamReader(System.in));
		String fileName = "";
		try {
			System.out.print("삭제할 파일 입력: ");
			fileName = lineRead.readLine();
			ftpClient.deleteFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doDelete()

	public static void doBinary() {
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println(ftpClient.getReplyCode() + " Switching to Binary mode.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doBinary()

	public static void doAscii() {
		try {
			ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
			System.out.println(ftpClient.getReplyCode() + " Switching to ASCII mode.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void doGetStatus() {
		try {
			System.out.println(ftpClient.getStatus());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doGetStatus()

	public static void doQuit() {
		try {
			ftpClient.disconnect();
			System.out.println(ftpClient.getReplyCode() + " Goodbye.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doQuit()

	public static void doPwd() {
		try {
			String pwd = ftpClient.printWorkingDirectory();
			System.out.println(pwd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end doPwd()

}// end class