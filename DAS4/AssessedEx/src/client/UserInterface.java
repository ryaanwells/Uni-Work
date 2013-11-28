package client;

import java.rmi.RemoteException;
import java.util.Scanner;

import server.ServerInterface;

public class UserInterface {

	private Scanner in;
	private int ID;
	private ClientImpl C;
	private ClientInterface CI;
	private ServerInterface SI;
	
	public UserInterface(Scanner in, int ID, ClientImpl C, ClientInterface CI, ServerInterface SI){
		this.in = in;
		this.C = C;
		this.CI = CI;
		this.SI = SI;
	}
	
	public void start(){
		this.initial();
		this.help();
		String command;
		while(true){
			System.out.print("bidRMI> ");
			command = in.nextLine();
			if (command.equals("h")){
				this.help();
				continue;
			}
			else if (command.equals("q")){
				break;
			}
			else if (command.equals("n")){
				this.newItem();
			}
			else if (command.equals("l")){
				this.list();
			}
			else if (command.equals("b")){
				this.bid();
			}
			else if (command.equals("i")){
				this.history();
			}
			else if (command.equals("m")){
				this.messages();
			}
			else {
				this.unknownCommand();
			}
		}
	}
		
	private void newItem(){
		System.out.print("\tName your item: ");
		String name = in.nextLine();
		
		System.out.print("\tGive your item a price: ");
		int price = Integer.parseInt(in.nextLine().trim());
		
		while (price < 0){
			System.out.print("\tThe price cannot be negative!: ");
			price = Integer.parseInt(in.nextLine().trim());
		}
		
		System.out.print("\tHow many minutes from now should it end?: ");		
		long timeout = Long.parseLong(in.nextLine().trim());
		
		while (timeout < 0){
			System.out.println("\tYou cannot finish an auction before it begins!");
			System.out.print("\tEnter a value in minutes greater than 0:");
			timeout = Long.parseLong(in.nextLine().trim());
		}
		
		timeout = 60000 * timeout; //minutes to milliseconds conversion
		
		try {
			SI.createAuction(name, price, this.CI, this.ID, timeout);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void list(){
		String[][] auctions = null;
		boolean active = true;
		
		System.out.print("Do you wish to see open [Y] or closed auctions [n]? :");
		String command = in.nextLine().trim();
		
		if (command.equals("n") || command.equals("N")){
			active = false;
		}
		
		try {
			auctions = SI.listAuctions(active);
		} catch (RemoteException e){
			e.printStackTrace();
			return;
		}
		
		System.out.println("\t| Item ID |   Item Name  | Current Bid | Reserve Met |");
		System.out.println("\t|----------------------------------------------------|");
		for (String[] A : auctions){
			System.out.println("\t\t" + A[0] + "\t" + A[1] + "\t" + A[2] + "\t" + A[3]);
			System.out.println("\t|----------------------------------------------------|");
		}
	}
	
	private void bid(){
		System.out.print("\tEnter ID of item: ");
		int UUID = Integer.parseInt(in.nextLine().trim());
		
		System.out.print("\tEnter bid: ");
		int bid = Integer.parseInt(in.nextLine().trim());
		
		boolean success = false;
		
		try {
			success = SI.bidOnItem(UUID, bid, CI, ID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		if (success){
			System.out.println("\tSucceeded in bidding on " + UUID);
		}
		else {
			System.out.println("\tUnsuccesful in bidding on " + UUID);
		}
	}
	
	private void history(){
		System.out.print("\tEnder ID of item: ");
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
	
	private void messages(){
		System.out.println("\tGetting messages...");
		String[] messages = C.getMessages();
		if (messages.length == 0){ System.out.println("\tNo new messages"); return;}
		for (String s: messages){
			System.out.println("\t" + s);
		}
	}
	
	private void initial(){
		System.out.println();
		System.out.println("\t|-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-|\n" +
						   "\t|                                                 |\n" +
				           "\t|                   Welcome to                    |\n" +
						   "\t|                                                 |\n" +
						   "\t|       #               # #####  #     #  #####   |\n" +
						   "\t|      #     #         #  #    # # # # #   ##     |\n" +
						   "\t|     #     ##        #   #    # #  #  #    #     |\n" +
						   "\t|    #####       #####    #####  #     #    #     |\n" +
						   "\t|   #    #  #   #   #     #  #   #     #    #     |\n" +
						   "\t|  #    #   #  #   #      #   #  #     #    ##    |\n" +
						   "\t|  #####   ##  ####       #    # #     #  #####   |\n" +
						   "\t|                                                 |\n" +
						   "\t|-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-+=+-|\n"
				);
		System.out.println();
	}
	
	private void help(){
		String help = "\th\t-\tDisplays this help message.\n"
				+"\tn\t-\tCreates a new auction.\n"
				+ "\tl\t-\tLists all auctions that are open.\n"
				+ "\tb\t-\tBid on an auction.\n"
				+ "\ti\t-\tInformation on an items bidding history\n"
				+ "\tm\t-\tView unread messages\n"
				+ "\tq\t-\tExit the application.";
		System.out.println(help);
	}
	
	private void unknownCommand(){
		String unknownCommand = "\tUnrecognised command. The available commands are: ";
		System.out.println(unknownCommand);
		this.help();
	}
}
