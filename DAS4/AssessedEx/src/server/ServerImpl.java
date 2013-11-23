package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientInterface;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements ServerInterface{

	private AuctionManager AM;
	private int nextID;
	
	public ServerImpl() throws RemoteException {
		super();
		this.AM = new AuctionManager();
		this.nextID = -1;
	}

	@Override
	public int getNextID(){
		nextID++;
		return nextID;
	}
	
	@Override
	public int createAuction(String name, int minValue, ClientInterface c, int clientID) throws RemoteException {
		AuctionItem a = this.AM.add(name, minValue, c, clientID);
		System.out.println("Auction created: " + name + ", " + minValue);
		return a.getUUID();
	}

	@Override
	public boolean bidOnItem(int itemID, int maxBid, ClientInterface c, int clientID) throws RemoteException {
		return this.AM.bidOn(itemID, maxBid, c, clientID);
	}

	@Override
	public String[] listAuctions(boolean active) throws RemoteException {
		return this.AM.list(active);
	}
	
	@Override
	public String[] getHistory(int itemID){
		return this.AM.getHistory(itemID);
	}

}
