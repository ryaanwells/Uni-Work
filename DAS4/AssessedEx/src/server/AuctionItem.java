package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import client.ClientInterface;
import core.MessageType;

@SuppressWarnings("serial")
public class AuctionItem extends UnicastRemoteObject{
	
	private int uuid;
	private String name;
	
	private AuctionThread AT = null;
	private boolean isActive;
	private long timeout;
	private boolean bidding;
	
	private int currentMaxBid;
	private int minimumPrice;

	private ClientInterface owner;
	private int ownerID;
	private ClientInterface maxBidder;
	private int maxBidderID;
	private HashMap<ClientInterface, Integer> bidders;
	private ArrayList<String> history;

	public AuctionItem(int uuid, String name, int minimumPrice, ClientInterface c, int ID, long timeout) throws RemoteException {
		super();
		this.uuid = uuid;
		this.name = name;
		this.minimumPrice = minimumPrice;
		this.currentMaxBid = 0;
		this.owner = c;
		this.ownerID = ID;
		this.bidders = new HashMap<ClientInterface, Integer>();
		this.history = new ArrayList<String>();
		
		this.history.add("Client " + this.ownerID + " starts auction for "+
				this.name + " with reserve of " + this.minimumPrice);
		
		this.isActive = true;
		this.timeout = timeout;
		this.bidding = false;
	}

	public String getName() {
		return this.name;
	}
	
	public void start(){
		if (isActive && this.AT == null){
			this.AT = new AuctionThread();
			new Thread(this.AT).start();
		}
	}

	public int getUUID() {
		return this.uuid;
	}

	public int getCurrentMaxBid() {
		return this.currentMaxBid;
	}

	public synchronized boolean attemptBid(int bid, ClientInterface ci, int clientID) {
		if (isActive && bid > currentMaxBid && !ci.equals(owner)) {
			this.bidding = true;
			boolean leaderChanged = false;
			ClientInterface oldLeader = null;
			bidders.put(ci, bid);
			if (maxBidder == null || bid > bidders.get(maxBidder)){
				leaderChanged = true;
				oldLeader = this.maxBidder;
				this.maxBidder = ci;
				this.maxBidderID = clientID;
				this.currentMaxBid = bid;
				history.add("Bid: " + maxBidderID + " bids " + this.currentMaxBid);
			}
			else if (bidders.get(maxBidder) > bid){
				this.currentMaxBid = bid + 1;
				history.add("Bid: " + clientID + " bids " + bid);
				history.add("Bid: " + maxBidderID + "bids " + this.currentMaxBid);
			}
			else{
				history.add("Bid: " + clientID + " matches leading bid of " + this.currentMaxBid);
			}
			
			for (ClientInterface c : bidders.keySet()) {
				try {
					if (c.equals(maxBidder)){
						c.update(MessageType.MAX_BIDDER, 
								"Item: " + this.name + " ("
										+ this.uuid + ")." 
										+ "You are leading with a bid of"
										+ this.currentMaxBid);
					} else if (leaderChanged && (c != null && c.equals(oldLeader))){
						c.update(MessageType.OUTBID,
								"You have been outbit on: " + this.name + " ("
										+ this.uuid + "). Max bid is: "
										+ this.currentMaxBid);
					} 
					else {
						c.update(MessageType.BID, 
								"Item: " + this.name + " (" 
										+ this.uuid + "). Max bid is: "
										+ this.currentMaxBid);
					}
				} catch (java.rmi.RemoteException RE) {
					System.err.println("AUCTION " + this.uuid
							+ ": Remote Error for Client: " + ci + "\n" + RE);
					RE.printStackTrace();
				}
				try {
					this.owner.update(MessageType.BID, "Item: " + this.name + " (" 
							+ this.uuid + "). Max bid is: "
							+ this.currentMaxBid);
				} catch (java.rmi.RemoteException RE){
					System.err.println("AUCTION " + this.uuid
							+ ": Remote Error for Client: " + this.owner + "\n" + RE);
					RE.printStackTrace();
				}
			}
			this.bidding = false;
			notifyAll();
			return leaderChanged;
		}
		return false;
	}

	public boolean reserveMet() {
		return minimumPrice <= currentMaxBid;
	}
	
	public String[] getHistory(){
		String[] array = new String[this.history.size()];
		return this.history.toArray(array);
	}
	
	private synchronized void close(){
		System.out.println("CLOSING");
		this.isActive = false;
		for (ClientInterface CI: this.bidders.keySet()){
			try {
				if (CI.equals(this.maxBidder)){
					if (this.reserveMet()){
						CI.update(MessageType.AUCTION_WIN, "You Won! Item " + this.name + 
								" for " + this.currentMaxBid);
					}
					else {
						CI.update(MessageType.NOT_SOLD, "Reserve was not met on " + this.name +
								" when auction ended at " + this.currentMaxBid);
					}
				}
				else if (CI.equals(this.owner)){
					if (this.reserveMet()){
						CI.update(MessageType.SOLD, "Your item " + this.name + 
								" has been sold for " + this.currentMaxBid);
					}
					else {
						CI.update(MessageType.NOT_SOLD, "Reserve was not met on " + this.name +
								" when auction ended at " + this.currentMaxBid + 
								". Reserve was " + this.minimumPrice);
					}
				}
				else {
					if (this.reserveMet()){
						CI.update(MessageType.AUCTION_END, "Auction ended for " + this.name +
								" at " + this.currentMaxBid + ". Item was sold");
					}
					else {
						CI.update(MessageType.AUCTION_END, "Auction ended for " + this.name +
								" at " + this.currentMaxBid + ". Item was not sold");
					}
				}
			} catch (java.rmi.RemoteException RE){
				RE.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	private class AuctionThread implements Runnable{
		
		@Override
		public void run() {
			try {
				System.out.println(timeout);
				Thread.sleep(timeout);
				if (bidding){
					try{
						wait();
					}
					catch (InterruptedException IE){}
				}
				close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
