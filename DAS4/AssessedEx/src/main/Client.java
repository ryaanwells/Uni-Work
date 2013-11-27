package main;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import client.ClientImpl;
import client.ClientInterface;
import client.UserInterface;

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
		String ServerString = "rmi://" + host + ":" + port + "/bidding/";
		
		System.out.print("Connecting to: " + ServerString + " ...");
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
		
		System.out.println(" connected.");
		
		int ID = -1;
		
		System.out.print("Requesting unique ID... ");
		
		try {
			ID = SI.getNextID();
		} catch (RemoteException RE){
			System.out.println(RE);
			System.exit(0);
		}
		
		System.out.println("Done.");
		
		ClientImpl C = null;
		ClientInterface CI = null;
		
		System.out.print("Creating user... ");
		
		try {
			C = new ClientImpl(ID); 
			CI = C;
		} catch (RemoteException RE){
			RE.printStackTrace();
			System.exit(0);
		}
		
		System.out.println("Done.");
		
		Scanner in = new Scanner(System.in);
		UserInterface UI = new UserInterface(in, ID, C, CI, SI);
		UI.start();

		in.close();
		System.exit(0);
	}
}
