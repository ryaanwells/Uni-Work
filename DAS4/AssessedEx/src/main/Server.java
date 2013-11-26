package main;

import java.net.MalformedURLException;
import java.rmi.Naming; //Import naming classes to bind to rmiregistry
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import server.ServerImpl;

public class Server {

	public static void main(String args[]) {
		System.out.println("Starting Server.");
		try {
			System.out.print("Getting Registry... ");
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.list();
			System.out.println("Done.");
		} catch (RemoteException e) {
			try {
				System.out.println("Failed - no registry started.");
				System.out.print("Starting registry... ");
				LocateRegistry.createRegistry(1099);
				System.out.println("Done.");
			} catch (RemoteException e1) {
				System.out.println("Failed. Could not start registry.");
				e1.printStackTrace();
			}
		}

		Scanner in = new Scanner(System.in);

		System.out.print("Do you wish to start with test data? [y/N]: ");
		String decision = in.nextLine().trim();

		System.out
				.print("How many minutes after an auction ends until it is deleted? "
						+ "Default [press ENTER] is 1 minute. Time in minutes: ");
		int delay = 1;
		String delayLine = in.nextLine();
		if (!delayLine.equals(null)) {
			try {
				int newDelay = Integer.parseInt(delayLine);
				delay = newDelay;
			} catch (NumberFormatException NFE) {}
		}

		System.out.println("Auction lives for " + delay
				+ (delay == 1 ? " minute" : " minutes") + " before deletion.");
		in.close();

		ServerImpl SI;
		try {
			System.out.println("Creating Server Instance.");
			SI = new ServerImpl(decision.equals("y") || decision.equals("Y"),
					delay * 60000);
			Naming.rebind("rmi://localhost:1099/bidding/", SI);
			System.out.println("Running!");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
