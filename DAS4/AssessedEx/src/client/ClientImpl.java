package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import core.MessageType;

@SuppressWarnings("serial")
public class ClientImpl extends UnicastRemoteObject implements ClientInterface {

	private int id;
	private ArrayList<String> messages;
	
	public ClientImpl(int id) throws RemoteException{
		super();
		this.id = id;
		this.messages = new ArrayList<String>(20);
	}
	
	@Override
	public synchronized void update(MessageType mt, String message) throws RemoteException {
		this.messages.add(message);
	}
	
	public synchronized String[] getMessages(){
		String[] out = new String[this.messages.size()];
		out = this.messages.toArray(out);
		this.messages.clear();
		return out;
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
