public class NoSuchBandException extends Exception {
    public NoSuchBandException(String band) {
	super(band + " not found in the database.");
    }

    public NoSuchBandException(int bid) {
	super("band with id number " + bid + " not found in database.");
    }
}
