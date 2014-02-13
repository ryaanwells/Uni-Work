import FormatIO.FileIn;
import FormatIO.FileOut;
import FormatIO.EofX;
import java.util.LinkedList;

public class CTO{

    private FileIn plaintext;
    private FileIn ciphertext;
    private int firstBlockPlain;
    private int firstBlockCipher;
    private Integer[] cipherBlocks;
    private int key;

    public CTO(String plaintextFilename, String ciphertextFilename){
	this.plaintext = new FileIn(plaintextFilename);
	this.ciphertext = new FileIn(ciphertextFilename);
    }

    public void initialize(){
	try {
	    String hexPlain = plaintext.readWord();
	    firstBlockPlain = Hex16.convert(hexPlain);
	}
	catch (EofX x){}
	
	LinkedList<Integer> allCipherBlocks = new LinkedList<Integer>();
	try {
	    for (;;){
		String hexCipher = ciphertext.readWord();
		allCipherBlocks.add(Hex16.convert(hexCipher));
	    }
	}
	catch (EofX x){
	    cipherBlocks = new Integer[allCipherBlocks.size()];
	    cipherBlocks = allCipherBlocks.toArray(cipherBlocks);
	    firstBlockCipher = cipherBlocks[0];
	}
    }

    public boolean bruteForce(){
	for (int i = 0; i < Math.pow(2,16); i++){
	    int decrypt = Coder.decrypt(i, firstBlockCipher);
	    if (decrypt == firstBlockPlain){
		// System.out.println("KEY: " + i);
		key = i;
		return true;
	    }
	}
	return false;
    }

    public String getMessage(){
	if (bruteForce()){
	    String message = "";
	    int decrypt, c0, c1;
	    for (int i = 0; i < cipherBlocks.length; i++){
		decrypt = Coder.decrypt(key, cipherBlocks[i]);
		c0 = decrypt / 256;
		c1 = decrypt % 256;
		message += (char) c0;
		if (c1 != 0) 
		    message += (char) c1;
	    }
	    return message;
	}
	return "No key could be found;";
    }

    public static void main(String[] args){
	CTO cto = new CTO("1_plaintext.txt", "1_ciphertext.txt");
	cto.initialize();
	String message = cto.getMessage();
	System.out.println(message);
    }
}