package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import client.ClientInterface;

public class AuctionManager {

	private HashMap<Integer, AuctionItem> auctions;
	private int nextUUID = 0;
	
	public AuctionManager(){
		auctions = new HashMap<Integer, AuctionItem>();
	}
	
	public synchronized AuctionItem add(String name, int minimumPrice, ClientInterface c, int clientID, long timeout){
		AuctionItem a = null;
		try {
			a = new AuctionItem(nextUUID, name, minimumPrice, c, clientID, timeout);
			a.start();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		auctions.put(nextUUID, a);
		nextUUID++;
		return a;
	}
	
	public synchronized boolean bidOn(int UUID, int bid, ClientInterface c, int clientID){
		AuctionItem a = auctions.get(UUID);
		if (a == null){
			return false;
		}
		return a.attemptBid(bid, c, clientID);
	}
	
	public synchronized String[] list(boolean active){
		ArrayList<String> sa = new ArrayList<String>(auctions.size());
		for (AuctionItem a : auctions.values()){
			sa.add(a.getUUID() + " " + a.getName() + " " 
					+ a.getCurrentMaxBid() + " " + a.reserveMet());
		}
		sa.trimToSize();
		String[] ai = new String[sa.size()];
		ai = sa.toArray(ai);
		return ai;
	}
	
	public synchronized String[] getHistory(int UUID){
		AuctionItem a = auctions.get(UUID);
		if (a == null) { return new String[0]; }
		return a.getHistory();
	}
	
}
