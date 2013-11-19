package server;

public class AuctionItem {
	private int uuid;
	private String name;
	private int currentMaxBid;
	private int minimumPrice;
	
	public AuctionItem(int uuid, String name, int minimumPrice){
		this.uuid = uuid;
		this.name = name;
		this.minimumPrice = minimumPrice;
		this.currentMaxBid = 0;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getUUID(){
		return this.uuid;
	}
	
	public int getCurrentMaxBid(){
		return this.currentMaxBid;
	}
	
	public boolean attemptBid(int bid){
		if (bid > currentMaxBid){
			currentMaxBid = bid;
			// NOTIFY HERE
			return true;
		}
		return false;
	}
	
	public boolean reserveMet(){
		return minimumPrice <= currentMaxBid;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
