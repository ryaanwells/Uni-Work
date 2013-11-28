package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import client.ClientImpl;
import client.ClientInterface;

import server.ServerInterface;

public class ThroughputTest {

	static ServerInterface SI;
	static long[] totalTimes;

	public static void main(String[] args) {

		int users = 50;
		String host = "localhost";
		int port = 1099;

		if (args.length == 1) {
			users = Integer.parseInt(args[0]);
		} else if (args.length == 2) {
			users = Integer.parseInt(args[0]);
			host = args[1];
		} else if (args.length == 3) {
			users = Integer.parseInt(args[0]);
			host = args[1];
			port = Integer.parseInt(args[2]);
		}

		totalTimes = new long[users];
		Thread[] T = new Thread[users];

		SI = null;
		String ServerString = "rmi://" + host + ":" + port + "/bidding/";

		System.out.print("Connecting to: " + ServerString + " ...");
		try {
			SI = (ServerInterface) Naming.lookup("rmi://" + host + ":" + port
					+ "/bidding/");
		} catch (MalformedURLException murle) {
			System.err.println(murle);
			murle.printStackTrace();
			System.exit(0);
		} catch (RemoteException re) {
			System.err.println(re);
			re.printStackTrace();
			System.exit(0);
		} catch (NotBoundException nbe) {
			System.err.println(nbe);
			nbe.printStackTrace();
			System.exit(0);
		}

		System.out.println(" connected.");

		for (int i = 0; i < users; i++){
			T[i] = new Thread(new ThroughputThread(i));
			T[i].start();
		}
		
		for (int i = 0; i < users; i++){
			try {
				T[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		long totalTime = 0;
		
		for (int i = 0; i < totalTimes.length; i++){
			totalTime += totalTimes[i]/100;
		}
		
		long average = totalTime/users;
		
		long stdevTemp = 0;
		
		for (int i = 0; i < totalTimes.length; i++){
			stdevTemp += (average - totalTimes[i]/100) * (average - totalTimes[i]/100);
		}
		
		long stdDev = (long) Math.sqrt(stdevTemp/users);
		
		System.out.println("Average RTT:  " + average + "ms");
		System.out.println("Standard Dev: " + stdDev + "ms");
		System.exit(0);
	}

	private static class ThroughputThread implements Runnable {

		private int testID;

		protected ThroughputThread(int testID) {
			this.testID = testID;
		}

		@Override
		public void run() {
			int ID = -1;

			try {
				ID = SI.getNextID();
			} catch (RemoteException RE) {
				RE.printStackTrace();
				return;
			}

			ClientImpl C = null;
			ClientInterface CI = null;

			try {
				C = new ClientImpl(ID);
				CI = C;
			} catch (RemoteException RE) {
				RE.printStackTrace();
				System.exit(0);
			}

			int command;
			long startTime;
			for (int i = 0; i < 100; i++) {
				command = i + ID % 3;
				try {
					if (command == 0) {
						startTime = System.currentTimeMillis();
						SI.createAuction("Test", ID, CI, ID, 1);
						totalTimes[testID] += System.currentTimeMillis()
								- startTime;
					} else if (command == 1) {
						startTime = System.currentTimeMillis();
						SI.bidOnItem(ID, i, CI, ID);
						totalTimes[testID] += System.currentTimeMillis()
								- startTime;
					} else {
						startTime = System.currentTimeMillis();
						SI.listAuctions(true);
						totalTimes[testID] += System.currentTimeMillis()
								- startTime;
					}
				} catch (RemoteException RE) {
				}
			}
		}
	}
}
