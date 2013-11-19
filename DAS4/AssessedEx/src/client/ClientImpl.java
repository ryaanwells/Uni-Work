package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import core.MessageType;

@SuppressWarnings("serial")
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private int id;
	
	public ClientImpl(int id) throws RemoteException{
		super();
		this.id = id;
	}
	
	@Override
	public void update(MessageType mt, String message) throws RemoteException {
		switch (mt){
		case BID:
			System.out.println("Bid Notification");
			break;
		case MAX_BIDDER:
			System.out.println("Maximum Bidder");
			break;
		case OUTBID:
			System.out.println("Outbid Notification");
			break;
		case AUCTION_END:
			System.out.println("Auction End");
			break;
		case AUCTION_LOSE:
			System.out.println("Auction Lose");
			break;
		case AUCTION_WIN:
			System.out.println("Auction Win");
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof ClientImpl){
			return ((ClientImpl) o).id == this.id;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return String.valueOf(this.id);
	}
}
