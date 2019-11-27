package program;

//구현
public class AdapterProtocolByObject implements Connectable {
	Protocol protocol = new Protocol();
	@Override
	public void myFTPClient() {
		protocol.myFTPClient();
	}

	@Override
	public void mySFTPClient() {
		protocol.mySFTPClient();
	}
}


