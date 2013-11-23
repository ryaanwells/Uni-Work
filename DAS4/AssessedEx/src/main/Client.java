package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.ClientImpl;
import client.ClientInterface;

import server.ServerInterface;

public class Client {
	public static void main(String[] args){
		String host = "localhost";
		int port = 1099;
		
		if (args.length == 1){
			host = args[0];
		}
		if (args.length == 2){
			host = args[0];
			port = Integer.parseInt(args[2]);
		}
		
		ServerInterface SI = null;
		
		try{
			SI = (ServerInterface) Naming.lookup("rmi://" + host + ":" + port + "/bidding/");
		}
		catch (MalformedURLException murle){
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
		
		String command;
		Scanner in = new Scanner(System.in);
		ClientInterface CI = null;
		int ID = -1;
		
		try {
			ID = SI.getNextID();
			System.out.println(ID);
			CI = new ClientImpl(ID);
		} catch (RemoteException RE){
			System.out.println(RE);
			System.exit(0);
		}
		
		while (in.hasNext()){
			command = in.nextLine();
			if (command.equals("q")){
				break;
			}
			else if (command.equals("n")){
				System.out.print("Name your item: ");
				String name = in.nextLine();
				System.out.print("Give your item a price: ");
				int price = Integer.parseInt(in.nextLine().trim());
				try {
					SI.createAuction(name, price, CI, ID);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			else if (command.equals("l")){
				String[] auctions = null;
				try {
					auctions = SI.listAuctions(true);
				} catch (RemoteException e){
					e.printStackTrace();
					continue;
				}
				
				for (String AI : auctions){
					System.out.println(AI);
				}
			}
			else if (command.equals("b")){
				System.out.print("Enter ID of item: ");
				int UUID = Integer.parseInt(in.nextLine().trim());
				System.out.print("Enter bid: ");
				int bid = Integer.parseInt(in.nextLine().trim());
				boolean success = false;
				try {
					success = SI.bidOnItem(UUID, bid, CI, ID);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (success){
					System.out.println("Succeeded in bidding on " + UUID);
				}
				else {
					System.out.println("Unsuccesful in bidding on " + UUID);
				}
			}
			else if (command.equals("h")){
				System.out.print("Ender ID of item: ");
				int UUID = Integer.parseInt(in.nextLine().trim());
				String[] history = null;
				try {
					history = SI.getHistory(UUID);
				} catch (RemoteException e){
					e.printStackTrace();
				}
				if (history != null){
					for (String s: history){
						System.out.println(s);
					}
				}
			}
			System.out.print(" _\n|" + ID + "| > ");
		}
		in.close();
		System.exit(0);
		
	}
}
