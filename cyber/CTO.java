import FormatIO.FileIn;
import FormatIO.EofX;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;

public class CTO{

    private FileIn plaintext;
    private FileIn ciphertext;
    private Integer[] cipherBlocks;
    private int key;
    
    // http://www.cse.chalmers.se/edu/year/2010/course/TDA351/ass1/en_stat.html
    // http://norvig.com/mayzner.html
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
	{111, 102}, {101, 108}, {101, 100}, {118, 101},	{97, 100},
	
	// English characters that cannot occur.
	{106, 113}, {113, 103}, {113, 107}, {113, 121}, {113, 122},
	{119, 113}, {119, 122},
	// Characters that cannot occur after an apostrophe
	{39, 97}, {39, 98}, {39, 99}, {39, 101}, {39, 102}, 
	{39, 103}, {39, 104}, {39, 105}, {39, 106}, {39, 107},
	{39, 108}, {39, 111}, {39, 112}, {39, 113}, {39, 114},
	{39, 117}, {39, 118}, {39, 119}, {39, 120}, {39, 121},
	{39, 122}

    };

    private static double[] bigramWeight = {
	3.81, 0.85, 0.53, 2.93, 0.85, 0.52, 2.46, 0.84, 0.52, 2.41, 
	0.82, 0.52, 2.36, 0.8, 0.51, 2.14, 0.79, 0.49, 2.04, 0.76, 
	0.49, 1.98, 0.75, 0.49, 1.61, 0.73, 0.48, 1.52, 0.73, 0.48, 
	1.47, 0.73, 0.46, 1.4, 0.68, 0.44, 1.39, 0.67, 0.43, 1.35, 
	0.66, 0.42, 1.31, 0.64, 0.42, 1.22, 0.63, 0.4, 1.11, 0.63, 
	0.4, 1.08, 0.62, 0.4, 1.08, 0.61, 0.39, 1.08, 0.59, 0.39, 
	1.01, 0.59, 0.39, 0.95, 0.58, 0.39, 0.93, 0.57, 0.38, 0.91, 
	0.55, 0.38, 0.88, 0.55, 0.37,
	
	-100, -100, -100, -100, -100, -100, -100,

	-100, -100, -100, -100, -100, -100, -100, -100, -100, -100,
	-100, -100, -100, -100, -100, -100, -100, -100, -100, -100,
	-100,
	
    };


    public CTO(String ciphertextFilename){
	this.ciphertext = new FileIn(ciphertextFilename);
    }

    public void initialize(){
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
	}
    }

    public boolean validCharacters(int a, int b){
	return ( (a >= 'a' && a <= 'z') || a == '.' || a == ',' || a == ':' || a == ' ' || a == '\'') &&
	    ( (b >= 'a' && b <= 'z') || b == '.' || b == ',' || b == ':' || b == ' ' || b == '\'');
    }

    public double getWeightOfBigrams(int[] text){
	double weight = 0;
	for (int start = 0; start < text.length - 1; start++){
	    int a = text[start];
	    int b = text[start + 1];
	    if (!validCharacters(a, b)){
		return 0;
	    }
	    for (int i = 0; i < bigrams.length - 1; i++){
		if (text[start    ] == bigrams[i][0] &&
		    text[start + 1] == bigrams[i][1]){
		    weight += bigramWeight[i];
		    break;
		}
	    }
	}
	return weight;
    }

    public boolean bruteForce(){
	double bestKeyWeight = -1;
	int bestKey = -1;
	int keysWithSameWeight = 0;
	for (int numBlocks = 3; numBlocks < cipherBlocks.length; numBlocks++){
	    keysWithSameWeight = 0;
	    bestKey = -1;
	    bestKeyWeight = -1;
	    for (int i = 0; i < Math.pow(2,16); i++){
		int[] subCipher = new int [numBlocks * 2];
		for (int j = 0, k = 0; j < numBlocks; j++, k++){
		    int decrypt = Coder.decrypt(i, cipherBlocks[j]);
		    int c0 = decrypt / 256;
		    int c1 = decrypt % 256;
		    subCipher[k] = (int) Character.toLowerCase(c0);
		    subCipher[++k] = (int) Character.toLowerCase(c1);
		}
		double weight = getWeightOfBigrams(subCipher);
		// if (i == 31415){
		//     System.out.println("Real Key Weight: " + weight);
		// }
		if (weight == 0)
		    continue;
		// We have an equal weight to our current best, note this;
		if (weight == bestKeyWeight){
		    keysWithSameWeight++;
		    System.out.print("Matched Key Weight " + weight + " with  Message: ");
		    for (int c = 0; c < subCipher.length; c++){
			System.out.print((char) subCipher[c]);
		    }
		    System.out.print("\n");
		    System.out.println("Number of matches: " + keysWithSameWeight + "\n");
		    System.out.println("Key held: " + bestKey + ", this Key: " + i);
		}
		else if (weight > bestKeyWeight){
		    System.out.print("Best Key Message: ");
		    for (int c = 0; c < subCipher.length; c++){
			System.out.print((char) subCipher[c]);
		    }
		    System.out.print("\n");
		    System.out.println("Best Key Weight: " + weight + "\n");
		    keysWithSameWeight = 0;
		    bestKey = i;
		    bestKeyWeight = weight;
		}
	    }
	    System.out.println("Iteration: " + numBlocks);
	    System.out.println("Same weighted Keys: " + keysWithSameWeight);
	    System.out.println("Top Weight: " + bestKeyWeight + "\n --------- \n");
	    // If we have found a key that is unambiguously the best then stop searching.
	    if (bestKey >= 0 && keysWithSameWeight == 0 && bestKeyWeight > 3){
		key = bestKey;
		System.out.println("KEY: " + key);
		System.out.println("Number of Blocks: " + numBlocks);
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
	System.out.println("{" + (int) '\'' + ", " + (int) 'a' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'b' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'c' + "}");

	System.out.println("{" + (int) '\'' + ", " + (int) 'e' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'f' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'g' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'h' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'i' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'j' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'k' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'l' + "}");


	System.out.println("{" + (int) '\'' + ", " + (int) 'o' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'p' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'q' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'r' + "}");


	System.out.println("{" + (int) '\'' + ", " + (int) 'u' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'v' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'w' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'x' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'y' + "}");
	System.out.println("{" + (int) '\'' + ", " + (int) 'z' + "}");


	CTO cto = new CTO(args[0]);
	// System.out.println(cto.validCharacters(32, 32));
	cto.initialize();
	String message = cto.getMessage();
	System.out.println(message);
    }
}
