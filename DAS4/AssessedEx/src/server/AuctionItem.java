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
	private int currentMaxBid;
	private int minimumPrice;

	private ClientInterface owner;
	private ClientInterface maxBidder;
	private HashMap<ClientInterface, Integer> bidders;
	private ArrayList<String> history;

	public AuctionItem(int uuid, String name, int minimumPrice, ClientInterface c) throws RemoteException {
		super();
		this.uuid = uuid;
		this.name = name;
		this.minimumPrice = minimumPrice;
		this.currentMaxBid = 0;
		this.owner = c;
		this.bidders = new HashMap<ClientInterface, Integer>();
		this.history = new ArrayList<String>();
	}

	public String getName() {
		return this.name;
	}

	public int getUUID() {
		return this.uuid;
	}

	public int getCurrentMaxBid() {
		return this.currentMaxBid;
	}

	public boolean attemptBid(int bid, ClientInterface ci) {
		if (bid > currentMaxBid && !ci.equals(owner)) {
			boolean leaderChanged = false;
			ClientInterface oldLeader = null;
			bidders.put(ci, bid);
			if (maxBidder == null || bid > bidders.get(maxBidder)){
				leaderChanged = true;
				oldLeader = this.maxBidder;
				this.maxBidder = ci;
				System.out.println(oldLeader);
				System.out.println(this.maxBidder);
				this.currentMaxBid = bid;
				history.add("Bid: " + maxBidder + "bids " + this.currentMaxBid);
			}
			else if (bidders.get(maxBidder) > bid){
				this.currentMaxBid = bid + 1;
				history.add("Bid: " + ci + " bids " + bid);
				history.add("Bid: " + maxBidder + "bids " + this.currentMaxBid);
			}
			else{
				history.add("Bid: " + ci + " matches leading bid of " + this.currentMaxBid);
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
			return leaderChanged;
		}
		return false;
	}

	public boolean reserveMet() {
		return minimumPrice <= currentMaxBid;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
