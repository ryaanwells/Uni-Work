package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientInterface;

@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements ServerInterface{

	private AuctionManager AM;
	private int nextID;
	
	public ServerImpl(boolean startData, long delay) throws RemoteException {
		super();
		System.out.print("Creating Auction Manager... ");
		this.AM = new AuctionManager(delay);
		System.out.println("Done.");
		this.nextID = -1;
		if (startData){
			BufferedReader BR = null;
			System.out.print("Loading in start data... ");
			try {
				BR = new BufferedReader(new FileReader("startingAuctions.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Could not find starting file, starting empty.");
				e.printStackTrace();
				return;
			}
			
			String line;
			String[] auctionInfo;
			try {
				line = BR.readLine();
				while(line != null){
					auctionInfo = line.split(":");
					this.createAuction(auctionInfo[0], Integer.parseInt(auctionInfo[1]), 
							null, -1, Integer.parseInt(auctionInfo[2]) * 60000);
					line = BR.readLine();
				}	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				BR.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Done.");
		}
	}

	@Override
	public synchronized int getNextID(){
		nextID++;
		return nextID;
	}
	
	@Override
	public int createAuction(String name, int minValue, ClientInterface c, int clientID, long timeout) throws RemoteException {
		Auction a = this.AM.add(name, minValue, c, clientID, timeout);
		return a.getUUID();
	}

	@Override
	public boolean bidOnItem(int itemID, int maxBid, ClientInterface c, int clientID) throws RemoteException {
		return this.AM.bidOn(itemID, maxBid, c, clientID);
	}

	@Override
	public String[][] listAuctions(boolean active) throws RemoteException {
		return this.AM.list(active);
	}
	
	@Override
	public String[] getHistory(int itemID){
		return this.AM.getHistory(itemID);
	}

}
