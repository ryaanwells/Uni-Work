package client;

import core.MessageType;

public interface ClientInterface extends java.rmi.Remote{
	public void update(MessageType mt, String message) 
			throws java.rmi.RemoteException;
}
