package server;

import client.ClientInterface;

public interface ServerInterface extends java.rmi.Remote{
	
	public int getNextID()
		throws java.rmi.RemoteException;
	
	public int createAuction(String name, int minValue, ClientInterface c)
		throws java.rmi.RemoteException;
	
	public boolean bidOnItem(int itemID, int maxBid, ClientInterface c)
		throws java.rmi.RemoteException;
	
	public String[] listAuctions(boolean active)
		throws java.rmi.RemoteException;
	
}
