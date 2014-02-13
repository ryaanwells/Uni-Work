import FormatIO.FileIn;
import FormatIO.FileOut;
import FormatIO.EofX;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;

public class CTO{

    private FileIn plaintext;
    private FileIn ciphertext;
    private int firstBlockPlain;
    private int firstBlockCipher;
    private Integer[] cipherBlocks;
    private int key;

    private static int[][] bigrams = { 
	{101, 32}, {32, 109}, {104, 32}, {32, 116}, {97, 116},
	{109, 101}, {104, 101}, {111, 110}, {32, 112}, {116, 104},
	{32, 98}, {110, 116}, {100, 32}, {104, 105}, {101, 97},
	{32, 97}, {101, 110}, {97, 108}, {116, 32}, {116, 111},
	{32, 108}, {115, 32}, {110, 103}, {108, 32}, {32, 104},
	{32, 99}, {97, 32}, {32, 115}, {105, 115}, {108, 108},
	{105, 110}, {105, 116}, {110, 101}, {110, 32}, {32, 102},
	{32, 110}, {97, 110}, {111, 114}, {116, 105}, {32, 119},
	{102, 32}, {100, 101}, {101, 114}, {97, 115}, {110, 111},
	{32, 105}, {103, 32}, {98, 101}, {114, 32}, {116, 101},
	{114, 111}, {114, 101}, {101, 115}, {32, 114}, {32, 111},
	{32, 100}, {119, 97}, {121, 32}, {97, 114}, {119, 104},
	{110, 100}, {115, 116}, {109, 32}, {111, 32}, {108, 101},
	{104, 111}, {111, 117}, {115, 101}, {32, 121}, {104, 97},
	{111, 102}, {101, 108}, {101, 100}, {118, 101},	{97, 100}
    };

    private static double[] bigramWeight = {
	3.81, 0.85, 0.53, 2.93, 0.85, 0.52, 2.46, 0.84, 0.52, 2.41, 
	0.82, 0.52, 2.36, 0.8, 0.51, 2.14, 0.79, 0.49, 2.04, 0.76, 
	0.49, 1.98, 0.75, 0.49, 1.61, 0.73, 0.48, 1.52, 0.73, 0.48, 
	1.47, 0.73, 0.46, 1.4, 0.68, 0.44, 1.39, 0.67, 0.43, 1.35, 
	0.66, 0.42, 1.31, 0.64, 0.42, 1.22, 0.63, 0.4, 1.11, 0.63, 
	0.4, 1.08, 0.62, 0.4, 1.08, 0.61, 0.39, 1.08, 0.59, 0.39, 
	1.01, 0.59, 0.39, 0.95, 0.58, 0.39, 0.93, 0.57, 0.38, 0.91, 
	0.55, 0.38, 0.88, 0.55, 0.37};


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
	CTO cto = new CTO("test.txt", "1_ciphertext.txt");
	System.out.println(bigrams.length == bigramWeight.length);
	LinkedList<int[]> chars = new LinkedList<int[]>();
	LinkedList<Double> weights = new LinkedList<Double>();
	try {
	    BufferedReader fr = new BufferedReader(new FileReader("test.txt"));
	    String s;
	    while ((s= fr.readLine()) != null){
		String[] arr = s.split("-");
		char[] charr = arr[0].toCharArray();
		int[] ints = new int[2];
		ints[0] = (int)Character.toLowerCase(charr[0]);
		ints[1] = (int)Character.toLowerCase(charr[1]);
		chars.add(ints);
		weights.add(Double.parseDouble(arr[1]));
		System.out.println((int)Character.toLowerCase(charr[0]) + ":" + (int)charr[1] + ":" + arr[1]);
	    }
	} 
	catch (Exception e){
	    e.printStackTrace();
	}

	System.out.println("{ ");
	for (int[] ints: chars){
	    System.out.println("{" + ints[0] + ", " + ints[1] + "},");
	}
	System.out.println("}");
	
	System.out.print("{");
	for (double d: weights){
	    System.out.print(d + ", ");
	}
	System.out.println("}");

	// String s = "E ";
	// char[] ca = s.toCharArray();
	// System.out.println((int)Character.toLowerCase(ca[0]));
	// System.out.println((int)Character.toLowerCase(ca[1]));
	// cto.initialize();
	// String message = cto.getMessage();
	// System.out.println(message);
    }
}