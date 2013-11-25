package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import core.MessageType;

@SuppressWarnings("serial")
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private int id;
	private ArrayList<String> messages;
	private ArrayList<String> alerts;
	
	public ClientImpl(int id) throws RemoteException{
		super();
		this.id = id;
		this.messages = new ArrayList<String>(20);
		this.alerts = new ArrayList<String>(20);
	}
	
	@Override
	public void update(MessageType mt, String message) throws RemoteException {
		switch (mt){
		case BID:
			this.messages.add(message);
			break;
		case MAX_BIDDER:
			this.alerts.add(message);
			break;
		case OUTBID:
			this.alerts.add(message);
			break;
		case AUCTION_END:
			this.messages.add(message);
			break;
		case AUCTION_LOSE:
			this.messages.add(message);
			break;
		case AUCTION_WIN:
			this.alerts.add(message);
			break;
		case SOLD:
			this.alerts.add(message);
		case NOT_SOLD:
			this.alerts.add(message);
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
