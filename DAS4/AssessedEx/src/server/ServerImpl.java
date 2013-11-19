package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements ServerInterface{

	private AuctionManager AM;
	
	public ServerImpl() throws RemoteException {
		super();
		AM = new AuctionManager();
	}

	@Override
	public int createAuction(String name, int minValue) throws RemoteException {
		// TODO Auto-generated method stub
		AuctionItem a = AM.add(name, minValue);
		return a.getUUID();
	}

	@Override
	public boolean bidOnItem(int itemID, int maxBid) throws RemoteException {
		return AM.bidOn(itemID, maxBid);
	}

	@Override
	public AuctionItem[] listAuctions(boolean active) throws RemoteException {
		return AM.list(active);
	}

}
