/**
 * class showing how to translate from bash pattern to RegEx pattern
 * and how to then use the resulting Regex pattern to match input from System.in
 */

public class Regex {

	// converts bash pattern to Regex string
	// due to vagaries of parameter expansion on Windows, this code will
	// strip off leading and trailing apostrophes (') from `str'
	//
	// the RegEx string is generated as follows
	// '^' is put at the beginning of the string
	// '*' is converted to ".*"
	// '.' is converted to "\."
	// '?' is converted to "."
	// '$' is put at the end of the string
	
	public static String cvtPattern(String str) {
		StringBuilder pat = new StringBuilder();
		int start, length;

		pat.append('^');
		if (str.charAt(0) == '\'') {	// double quoting on Windows
			start = 1;
			length = str.length() - 1;
		} else {
			start = 0;
			length = str.length();
		}
		for (int i = start; i < length; i++) {
			switch(str.charAt(i)) {
			case '*': pat.append('.'); pat.append('*'); break;
			case '.': pat.append('\\'); pat.append('.'); break;
			case '?': pat.append('.'); break;
			default:  pat.append(str.charAt(i)); break;
			}
		}
		pat.append('$');
		return new String(pat);
	}	
}
