package server;

import java.util.HashMap;

public class AuctionManager {

	private HashMap<Integer, AuctionItem> auctions;
	private int nextUUID = 0;
	
	public AuctionManager(){
		auctions = new HashMap<Integer, AuctionItem>();
	}
	
	public synchronized AuctionItem add(String name, int minimumPrice){
		AuctionItem a = new AuctionItem(nextUUID, name, minimumPrice);
		auctions.put(nextUUID, a);
		nextUUID++;
		return a;
	}
	
	public synchronized boolean bidOn(int UUID, int bid){
		AuctionItem a = auctions.get(UUID);
		return a.attemptBid(bid);
	}
	
	public synchronized AuctionItem[] list(boolean active){
		AuctionItem[] ai = (AuctionItem[]) auctions.values().toArray();
		return ai;
	}
	
}
