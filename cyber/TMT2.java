import FormatIO.FileIn;
import FormatIO.EofX;

import java.util.LinkedList;

public class TMT2 {

    private FileIn plaintextFile;
    private FileIn tableFile;
    private FileIn ciphertextFile;
    private Table table;

    private int plaintextBlock;
    private Integer[] ciphertext;

    public TMT2(String plaintextFilename, String tableDataFilename, String ciphertextFilename){
	this.plaintextFile = new FileIn(plaintextFilename);
	this.tableFile = new FileIn(tableDataFilename);
	this.ciphertextFile = new FileIn(ciphertextFilename);
    }
    
    public boolean initialize(){
	table = new Table();
	LinkedList<Integer> allCipherBlocks = new LinkedList<Integer>();
	
	try {
	    plaintextBlock = Hex16.convert(plaintextFile.readWord());
	}
	catch (EofX x){
	    return false;
	}
	
	for (;;){
	    try {
		String[] line = tableFile.readLine().split(" ");
		table.add(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
	    }
	    catch (EofX x){
		break;
	    }
	}

	for (;;){
	    try {
		String hex = ciphertextFile.readWord();
		allCipherBlocks.add(Hex16.convert(hex));
	    }
	    catch (EofX x){
		ciphertext = new Integer[allCipherBlocks.size()];
		ciphertext = allCipherBlocks.toArray(ciphertext);
		break;
	    }
	}
	return true;
    }
    
    public int getKey(int start, int plaintext, int ciphertext){
	int key = -1;
	while (start != ciphertext){
	    key = start;
	    start = Coder.encrypt(start, plaintext);
	}
	return key;
    }

    public String getMessage(int key){
	String message = "";
	int decrypt, c0, c1;
	for (int i = 0; i < ciphertext.length; i++){
	    decrypt = Coder.decrypt(key, ciphertext[i]);
	    c0 = decrypt / 256;
	    c1 = decrypt % 256;
	    message += (char) c0;
	    if (c1 != 0) 
		message += (char) c1;
	}
	return message;
    }

    public int tableSearch(){
	if (table.find(ciphertext[0]) != -1){
	    System.out.println("In table");
	    return getKey(table.find(ciphertext[0]), plaintextBlock, ciphertext[0]);
	}
	int seed = ciphertext[0];
	for (int i = 0; i < 256; i++){
	    seed = Coder.encrypt(seed, plaintextBlock);
	    if (table.find(seed) != -1){
		return getKey(table.find(seed), plaintextBlock, ciphertext[0]);
	    }
	}
	return -1;
    }

    public static void main(String[] args){
	TMT2 tmt2 = new TMT2(args[0], args[1], args[2]);
	tmt2.initialize();
	int key = tmt2.tableSearch();
	if (key >= 0){
	    String message = tmt2.getMessage(key);
	    System.out.println("KEY: " + key);
	    System.out.println(message);
	}
	else {
	    System.out.println("Could not find the key.");
	}
    }

}
