package main;

import java.net.MalformedURLException;
import java.rmi.Naming; //Import naming classes to bind to rmiregistry
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import server.ServerImpl;

public class Server {

	public static void main(String args[]) {
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.list();
		} catch (RemoteException e) {
			try {
				LocateRegistry.createRegistry(1099);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		ServerImpl SI;
		try {
			SI = new ServerImpl();
			Naming.rebind("rmi://localhost:1099/bidding/", SI);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		}

	}
}
