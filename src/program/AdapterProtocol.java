package program;

//상속과 구현을 사용하여 다중상속 느낌 나도록
public class AdapterProtocol extends Protocol implements Connectable {
	@Override
	public void myFTPClient() {
		myFTPClient();
	}

	@Override
	public void mySFTPClient() {
		mySFTPClient();
	}
}
