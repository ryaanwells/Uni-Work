import FormatIO.FileIn;
import FormatIO.FileOut;
import FormatIO.EofX;

import java.util.Random;

public class TMT1 {
    
    private FileIn plaintext;
    private FileOut output;
    
    private int firstBlock;
    private Random r;
    private Table table;

    private final int MAX_KEY_VALUE = 65536;
    private final int L = 256;
    private final int N = 256;

    public TMT1(String plaintextFilename, String outputFilename){
	this.plaintext = new FileIn(plaintextFilename);
	this.output = new FileOut(outputFilename);
    }

    public boolean initialize(){
	table = new Table();
	r = new Random();
	try {
	    firstBlock = Hex16.convert(plaintext.readWord());
	}
	catch (EofX x){
	    return false;
	}
	return true;
    }

    public void process(){
	int seed, x;
	for (int i = 0; i < N; i++){
	    seed = r.nextInt(MAX_KEY_VALUE);
	    
	    while (table.find(seed) != -1){
		seed = r.nextInt(MAX_KEY_VALUE);
	    }
	    
	    x = Coder.encrypt(seed, firstBlock);
	    for (int j = 0; j < L; j++){
		x = Coder.encrypt(x, firstBlock);
	    }

	    output.println(x + " " + seed);
	    System.out.println(x + " " + seed);
	    table.add(seed, x);
	}
    }
    
    public static void main(String[] args){
	TMT1 tmt1 = new TMT1(args[0], args[1]);
	if (!tmt1.initialize()){
	    System.err.println("Could not initialize. Exiting.");
	    System.exit(1);
	}
	tmt1.process();
    }
}
