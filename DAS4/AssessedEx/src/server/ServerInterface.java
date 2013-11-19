package server;

public interface ServerInterface extends java.rmi.Remote{
	
	public int createAuction(String name, int minValue)
		throws java.rmi.RemoteException;
	
	public boolean bidOnItem(int itemID, int maxBid)
		throws java.rmi.RemoteException;
	
	public AuctionItem[] listAuctions(boolean active)
		throws java.rmi.RemoteException;
	
}
