package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import client.ClientInterface;

public class AuctionManager {

	private HashMap<Integer, Auction> auctions;
	private int nextUUID = 0;
	private long archiveTime;
	
	
	public AuctionManager(long archiveTime){
		this.auctions = new HashMap<Integer, Auction>();
		this.archiveTime = archiveTime;
	}
	
	public synchronized Auction add(String name, int minimumPrice, ClientInterface c, int clientID, long timeout){
		Auction a = null;
		try {
			a = new Auction(nextUUID, name, minimumPrice, c, clientID);
			AuctionEndingThread AET = new AuctionEndingThread(a.getUUID(), timeout);
			new Thread(AET).start();
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		auctions.put(nextUUID, a);
		nextUUID++;
		return a;
	}
	
	public boolean bidOn(int UUID, int bid, ClientInterface c, int clientID){
		Auction a = auctions.get(UUID);
		if (a == null){
			return false;
		}
		return a.attemptBid(bid, c, clientID);
	}
	
	public synchronized String[][] list(boolean active){
		ArrayList<String[]> sa = new ArrayList<String[]>(auctions.size());
		String[] data;
		for (Auction a : auctions.values()){
			if (a.isActive() == active){
				data = new String[4];
				data[0] = String.valueOf(a.getUUID());
				data[1] = a.getName();
				data[2] = String.valueOf(a.getCurrentMaxBid());
				data[3] = String.valueOf(a.reserveMet());
				
				sa.add(data);
			}
		}
		sa.trimToSize();
		String[][] ai = new String[sa.size()][4];
		ai = sa.toArray(ai);
		return ai;
	}
	
	public String[] getHistory(int UUID){
		Auction a = auctions.get(UUID);
		if (a == null) { return new String[0]; }
		return a.getHistory();
	}
	
	private synchronized void archive(int UUID){
		this.auctions.remove(UUID);
	}
	
	private class AuctionEndingThread implements Runnable{

		private long timeout;
		private int auction;
		
		public AuctionEndingThread(int auction, long timeout){
			this.auction = auction;
			this.timeout = timeout;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(timeout);
				auctions.get(auction).close();
				AuctionArchiveThread AAT = new AuctionArchiveThread(auction);
				new Thread(AAT).start();
			} catch (InterruptedException e) {}
		}
	}
	
	private class AuctionArchiveThread implements Runnable{
		
		private int auction;
		
		public AuctionArchiveThread(int auction){
			this.auction = auction;
		}
		
		@Override
		public void run(){
			try {
				Thread.sleep(archiveTime);
				archive(auction);
			} catch (InterruptedException e) {}
			
		}
	}
	
}
