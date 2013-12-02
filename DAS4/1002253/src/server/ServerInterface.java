package server;

import client.ClientInterface;

public interface ServerInterface extends java.rmi.Remote{
	
	public int getNextID()
		throws java.rmi.RemoteException;
	
	public int createAuction(String name, int minValue, ClientInterface c, int clientID, long timeout)
		throws java.rmi.RemoteException;
	
	public boolean bidOnItem(int itemID, int maxBid, ClientInterface c, int clientID)
		throws java.rmi.RemoteException;
	
	public String[][] listAuctions(boolean active)
		throws java.rmi.RemoteException;
	
	public String[] getHistory(int itemID)
		throws java.rmi.RemoteException;
}
