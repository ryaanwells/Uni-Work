package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import client.ClientInterface;
import core.MessageType;

@SuppressWarnings("serial")
public class Auction extends UnicastRemoteObject {

	private int uuid;
	private String name;

	private boolean isActive;

	private int currentMaxBid;
	private int minimumPrice;

	private ClientInterface owner;
	private int ownerID;
	private ClientInterface maxBidder;
	private int maxBidderID;
	private HashMap<ClientInterface, Integer> bidders;
	private ArrayList<String> history;

	public Auction(int uuid, String name, int minimumPrice, ClientInterface c,
			int ID) throws RemoteException {
		super();
		this.uuid = uuid;
		this.name = name;
		this.minimumPrice = minimumPrice;
		this.currentMaxBid = 0;
		this.owner = c;
		this.ownerID = ID;
		this.bidders = new HashMap<ClientInterface, Integer>();
		this.history = new ArrayList<String>();

		this.history.add("Client " + this.ownerID + " starts auction for \""
				+ this.name + "\" with reserve of £" + this.minimumPrice);

		this.isActive = true;
	}

	public String getName() {
		return this.name;
	}

	public int getUUID() {
		return this.uuid;
	}

	public synchronized int getCurrentMaxBid() {
		return this.currentMaxBid;
	}
	
	public synchronized boolean isActive(){
		return this.isActive;
	}

	public synchronized boolean attemptBid(int bid, ClientInterface ci,
			int clientID) {
		if (this.isActive && bid > this.currentMaxBid
				&& (this.owner == null || (this.owner != null && !this.owner.equals(ci)))) {
			boolean leaderChanged = false;
			ClientInterface oldLeader = null;
			if (this.maxBidder != null && this.maxBidder.equals(ci)) {
				leaderChanged = false;
				if (bid > this.currentMaxBid){
					this.bidders.put(ci, bid);
					if (!this.reserveMet()){
						if (bid >= this.minimumPrice){
							this.currentMaxBid = this.minimumPrice;
						}
						else {
							this.currentMaxBid = bid;
						}
					}
					history.add("Increased max bid of leader " + clientID + " to £"
							+ bid);
					return true;
				}
				else {
					return false;
				}
			} else if (maxBidder == null || bid > bidders.get(maxBidder)) {
				this.bidders.put(ci, bid);
				leaderChanged = true;
				oldLeader = this.maxBidder;
				if (!this.reserveMet()){
					if (bid >= this.minimumPrice){
						this.currentMaxBid = this.minimumPrice;
					}
					else {
						this.currentMaxBid = bid;
					}
				}
				else {
					this.currentMaxBid = bidders.get(oldLeader) + 1;
				}
				this.maxBidder = ci;
				this.maxBidderID = clientID;
				this.currentMaxBid = bid;
				history.add("Bid: " + maxBidderID + " bids £"
						+ this.currentMaxBid);
			} else if (bidders.get(maxBidder) > bid) {
				this.bidders.put(ci, bid);
				this.currentMaxBid = bid + 1;
				history.add("Bid: " + clientID + " bids £" + bid);
				history.add("Bid: " + maxBidderID + "bids £"
						+ this.currentMaxBid);
			}

			for (ClientInterface c : bidders.keySet()) {
				try {
					if (c.equals(maxBidder)) {
						c.update(MessageType.MAX_BIDDER, "Item: \"" + this.name
								+ "\" (" + this.uuid + "). "
								+ "You are leading with a bid of £"
								+ this.currentMaxBid);
					} else if (leaderChanged
							&& (c != null && c.equals(oldLeader))) {
						c.update(MessageType.OUTBID,
								"You have been outbit on: \"" + this.name + "\" ("
										+ this.uuid + "). Max bid is: £"
										+ this.currentMaxBid);
					} else {
						c.update(MessageType.BID, "Item: \"" + this.name + "\" ("
								+ this.uuid + "). Max bid is: £"
								+ this.currentMaxBid);
					}
				} catch (java.rmi.RemoteException RE) {
					System.err.println("AUCTION " + this.uuid
							+ ": Remote Error for Client: " + ci + "\n" + RE 
							+ "\nCould not reach client.");
				}
			}
			
			try {
				if (owner != null) {
					this.owner.update(MessageType.BID, "Item: \"" + this.name
							+ "\" (" + this.uuid + "). Max bid is: £"
							+ this.currentMaxBid);
				}
			} catch (java.rmi.RemoteException RE) {
				System.err.println("AUCTION " + this.uuid
						+ ": Remote Error for Client: " + this.ownerID
						+ "\n" + RE + "\nCould not reach client.");
				
			}
			
			return leaderChanged;
		}
		this.history.add("Attempted bid by " + clientID
				+ " of £" + bid + " was less than current bid of £" + this.currentMaxBid);
		return false;
	}

	public synchronized boolean reserveMet() {
		return minimumPrice <= currentMaxBid;
	}

	public synchronized String[] getHistory() {
		String[] array = new String[this.history.size()];
		return this.history.toArray(array);
	}

	public synchronized void close() {
		this.isActive = false;

		try {
			if (this.owner != null) {
				if (this.reserveMet()) {
					this.owner.update(MessageType.SOLD, "Your item \""
							+ this.name + "\" has been sold for £"
							+ this.currentMaxBid + " to " + this.maxBidderID);
				} else {
					this.owner.update(MessageType.NOT_SOLD,
							"Reserve was not met on \"" + this.name
									+ "\" when auction ended at £"
									+ this.currentMaxBid + ". Reserve was £"
									+ this.minimumPrice);
				}
			}
		} catch (java.rmi.RemoteException RE) {
			RE.printStackTrace();
		}

		for (ClientInterface CI : this.bidders.keySet()) {
			try {
				if (CI.equals(this.maxBidder)) {
					if (this.reserveMet()) {
						CI.update(MessageType.AUCTION_WIN, "You Won! Item \""
								+ this.name + "\" for £" + this.currentMaxBid);
					} else {
						CI.update(MessageType.NOT_SOLD,
								"Reserve was not met on \"" + this.name
										+ "\" when auction ended at £"
										+ this.currentMaxBid);
					}
				} else {
					if (this.reserveMet()) {
						CI.update(MessageType.AUCTION_END, "Auction ended for \""
								+ this.name + "\" at £" + this.currentMaxBid
								+ ". Item was sold to " + this.maxBidderID);
					} else {
						CI.update(MessageType.AUCTION_END, "Auction ended for \""
								+ this.name + "\" at £" + this.currentMaxBid
								+ ". Item was not sold");
					}
				}
			} catch (java.rmi.RemoteException RE) {
				RE.printStackTrace();
			}
		}
	}

	@Override
	public String toString() {
		return this.name;
	}
}
