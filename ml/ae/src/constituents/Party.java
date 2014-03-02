package constituents;

public enum Party {
	DUP ("DUP"), 
	INDLAB ("Ind Lab"), 
	SDLP ("SDLP"), 
	SNP ("SNP"), 
	CON ("Con"), 
	LAB ("Lab"), 
	IND ("Ind"), 
	LDEM ("LDem"),  
	PC ("PC");
	
	private String name;
	
	Party(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static Party getParty(String name){
		for (Party p: Party.values()){
			if (p.name.equals(name)){
				return p;
			}
		}
		return null;
	}
	
}
